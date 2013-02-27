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
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASWD;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_wd.SquawkPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_wd.StartWarningPayload;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.IASWDCluster;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 *         
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public class IASWDImpl implements IASWD {

	private final IASWDCluster iaswdcluster;
	private final Attribute maxDuration;

	public IASWDImpl(ZigBeeDevice zbDevice){

		iaswdcluster = new IASWDCluster(zbDevice);
		maxDuration = iaswdcluster.getAttributeMaxDuration();
	}

	public int getId() {
		return iaswdcluster.getId();
	}

	public String getName() {
		return iaswdcluster.getName();
	}

	public Subscription[] getActiveSubscriptions() {
		return iaswdcluster.getActiveSubscriptions();
	}

	public Attribute[] getAttributes() {
		return iaswdcluster.getAvailableAttributes();
	}

	public Attribute getAttribute(int id) {
		Attribute[] attributes = iaswdcluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public Attribute getMaxDuration() {
		return maxDuration;
	}

	public void startWarning(StartWarningPayload payload) throws ZigBeeHAException {
		try {
			iaswdcluster.startWarning(payload); 
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public void squawk(SquawkPayload payload) throws ZigBeeHAException {
		try {
			iaswdcluster.squawk(payload);
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}
}