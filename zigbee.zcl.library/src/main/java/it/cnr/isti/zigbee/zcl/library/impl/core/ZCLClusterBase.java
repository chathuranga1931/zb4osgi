/*
   Copyright 2008-2010 CNR-ISTI, http://isti.cnr.it
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

package it.cnr.isti.zigbee.zcl.library.impl.core;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Command;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.impl.ClusterImpl;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 *         
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public abstract class ZCLClusterBase implements ZCLCluster {
	
	final public static Logger logger = LoggerFactory.getLogger(ZCLClusterBase.class);
	
	private ZigBeeDevice zbDevice;
	private boolean isDefaultResponseEnabled;
	private HashMap<Integer, Attribute> attributes;

	public  ZCLClusterBase(ZigBeeDevice zbDevice){
		this.zbDevice = zbDevice;
	}
	
	public abstract short getId();
	public abstract String getName();
	public abstract Attribute[] getStandardAttributes() ;
	
	protected ZigBeeDevice getZigBeeDevice() {
	    return zbDevice;
	}
	
	public void enableDefaultResponse() {
		isDefaultResponseEnabled = true;
	}
	
	public boolean isDefaultResponseEnabled() {
		return isDefaultResponseEnabled;
	}

	public Attribute getAttribute(int id) {
		if ( attributes ==  null ) {
			attributes = new HashMap<Integer, Attribute>();
			Attribute[] list = getAvailableAttributes();
			for (int i = 0; i < list.length; i++) {
				attributes.put(list[i].getId(), list[i]);
			}
		}
		
		return attributes.get(id);
	}
	
	public Attribute[] getAvailableAttributes() {
		//TODO use Discovery Attribute command to find the real attribute
		return getStandardAttributes();
	}

	public int getManufacturerId() {
		return -1;
	}

	public Response invoke(Command cmd) throws ZigBeeClusterException {
		return invoke(cmd, !isDefaultResponseEnabled);
	}

	public Response invoke(Command cmd, boolean suppressResponse) throws ZigBeeClusterException  {
		ZCLFrame inFrame = new ZCLFrame(cmd, isDefaultResponseEnabled);
		Cluster input = new ClusterImpl(getId(),inFrame);
		if (suppressResponse) {
			try {
				logger.debug("Sending ZCLFrame {} without expecting an answer", inFrame);
				zbDevice.send(input);
				return null;
			} catch (ZigBeeBasedriverException e) {
				throw new ZigBeeClusterException(e);
			}
		} else{
			Cluster cluster;
			try {
				logger.debug("Sending ZCLFrame {} and waiting for response", inFrame);
				cluster = zbDevice.invoke(input);
				Response response = new ResponseImpl(cluster,getId());
				logger.debug("Received response {} to request {}", response, inFrame);
				return response;
			} catch (ZigBeeBasedriverException e) {
				throw new ZigBeeClusterException(e);
			}
		}
	}
	
	public Subscription[] getActiveSubscriptions() {
		final ArrayList<Subscription> actives = new ArrayList<Subscription>();
		final Attribute[] attributes = getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			final Subscription subscription = attributes[i].getSubscription(); 
			if( subscription != null ){
				actives.add(subscription);
			}
		}
		return actives.toArray(new Subscription[]{});
	}


}
