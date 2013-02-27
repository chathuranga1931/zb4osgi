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

package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Identify;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.identify.IdentifyQueryResponse;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.impl.general.IdentifyCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class IdentifyImpl implements Identify {
	
	private IdentifyCluster identifyCluster;
	private Attribute identifyTime;
	
	
	public IdentifyImpl(ZigBeeDevice zbDevice){
		identifyCluster = new IdentifyCluster(zbDevice);
		
	}

	public int IdentifyQuery() throws ZigBeeHAException{
		try {
			IdentifyQueryResponse response = (IdentifyQueryResponse)identifyCluster.identifyQuery();
			return response.getTimeout();
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public int getIdentifyTime() throws ZigBeeHAException{
		try {
		return (Integer)identifyTime.getValue();
		
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public void identify(int time)  throws ZigBeeHAException {
		try {
			DefaultResponse response = (DefaultResponse) identifyCluster.identify(time);
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public Subscription[] getActiveSubscriptions() {
		return identifyCluster.getActiveSubscriptions();
	}

	public int getId() {
		return identifyCluster.getId();
	}

	public String getName() {
		return identifyCluster.getName();
	}

	public Attribute getAttribute(int id) {		
		Attribute[] attributes = identifyCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public Attribute[] getAttributes() {
		return identifyCluster.getAvailableAttributes();
	}
	
	
}
