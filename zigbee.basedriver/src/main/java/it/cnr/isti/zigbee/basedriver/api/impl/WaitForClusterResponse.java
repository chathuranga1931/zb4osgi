/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 


   See the NOTICE file distributed with this work for additional 
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package it.cnr.isti.zigbee.basedriver.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itaca.ztool.api.af.AF_INCOMING_MSG;

/**
 * 
 * This class register itself as {@link AFMessageConsumer} to the given {@link AFMessageProducer}<br>
 * and it wait for a matching {@link AF_INCOMING_MSG}. As soon as the matching {@link AF_INCOMING_MSG}<br> 
 * is received or the timeout expires the the object unregister itself from {@link AFMessageProducer}<br>
 * 
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class WaitForClusterResponse implements AFMessageConsumer{

	private static final Logger logger = LoggerFactory.getLogger(WaitForClusterResponse.class);
	
	private byte transId;
	private short clusterId;
	private long timeout = -1;
	private AF_INCOMING_MSG response = null;
	private AFMessageProducer producer;

	private final Thread waiter;


	/**
	 * 
	 * @param timeout the maximum number of milliseconds to wait for. The value -1 means unlimited waiting time. 
	 * @since  0.4.0
	 */
	public WaitForClusterResponse(final AFMessageProducer producer, final byte transaction, 
			final short id, final long timeout, final Thread thread) {
		
		synchronized (this) {
			this.producer = producer;
			this.producer.addAFMessageConsumer(this);
			this.timeout = timeout;
			this.waiter = thread;
			response = null;
			//BUG the CC2480 always send AF_INCOMING_MSG with transaction 0 so we must not use it 
			transId = transaction;
			clusterId = id;			
		}
	}
	
	/**
	 * 
	 * @param timeout the maximum number of milliseconds to wait for. The value -1 means unlimited waiting time. 
	 */
	public WaitForClusterResponse(final AFMessageProducer producer, final byte transaction, 
			final short id, final long timeout) {
		
		this(producer,transaction, id, timeout, Thread.currentThread());
	}
	
	public boolean consume(AF_INCOMING_MSG msg) {
		//THINK  Is the following matching algorithm correct?!?!?		
		if ( msg.getClusterId() != clusterId ) {
			logger.debug("Unable to consume AF_INCOMING_MSG, because cluster {} != {}", msg.getClusterId(), clusterId);
			return false;
		}
		if ( msg.getTransId() != transId ) {
			logger.debug("Unable to consume AF_INCOMING_MSG, because transaction {} != {}", msg.getTransId(), transId);
			return false;
		}
		logger.debug(
				"Consuming message with ClusterId: {} TransactionId: {} for thread {}/{}", 
				new Object[]{
						msg.getClusterId(), msg.getTransId(), waiter.getName(), waiter.getClass().getName()
				}
		);
		synchronized (this) {
			response = msg;
			notify();		
		}
		//We wait for a cluster at the time
		producer.removeAFMessageConsumer(this);
		return true;
	}
	
	/**
	 * Wait until an {@link AF_INCOMING_MSG} arrives from the <i>ZigBee Network</i>. The message<br>
	 * has to match the specified parameters and has to arrive before a timout otherwise <code>null</code><br>
	 * will be returned.
	 * 
	 * @return {@link AF_INCOMING_MSG} that match the filter used with the constructor<br>
	 * 		{@link #WaitForClusterResponse(byte, short)} 
	 */ 	
	public AF_INCOMING_MSG getResponse(){
		final long wakeUpTime = System.currentTimeMillis() + timeout;
		AF_INCOMING_MSG msg = null;
		
		synchronized (this) {
			while(response == null && (timeout > 0 && wakeUpTime > System.currentTimeMillis())){
				try {
					wait(wakeUpTime - System.currentTimeMillis());
				} catch (InterruptedException ignored) {
				}
			}
			msg = response;
			response = null;
			producer.removeAFMessageConsumer(this);
		}
		return msg;
	}
	
}
