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

package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Alarms;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.alarms.AlarmListener;
import it.cnr.isti.zigbee.zcl.library.api.general.alarms.GetAlarmResponse;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.impl.general.AlarmsCluster;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.4.0
 *
 */
public class AlarmsImpl implements Alarms{
	
	private final AlarmsCluster alarmsCluster;
	private final Attribute attribute;
	
	public AlarmsImpl(ZigBeeDevice zbDevice){
		alarmsCluster = new AlarmsCluster(zbDevice);
		attribute = alarmsCluster.getAttributeAlarmCount();
	}

	public boolean getOnOff() throws ZigBeeHAException {
		try {
			Boolean value = (Boolean) attribute.getValue();
			return value.booleanValue();
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public Subscription[] getActiveSubscriptions() {
		return alarmsCluster.getActiveSubscriptions();
	}

	public int getId() {
		return alarmsCluster.getId();
	}

	public String getName() {
		return alarmsCluster.getName();
	}


	public Attribute getAttribute(int id) {		
		Attribute[] attributes = alarmsCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public Attribute[] getAttributes() {
		return alarmsCluster.getAvailableAttributes();
	}

	public Attribute getAttributeAlarmCount() {
	    return attribute;
	}

	public boolean addAlarmListerner(AlarmListener listener) {
	    return alarmsCluster.addAlarmListerner(listener);
	}

	public boolean removeAlarmListerner(AlarmListener listener) {
	    return alarmsCluster.removeAlarmListerner(listener);
	}

	public GetAlarmResponse getAlarm() throws ZigBeeHAException {
	    try {
		Response response = alarmsCluster.getAlarm();
		if (response.getZCLHeader().getCommandId() != GetAlarmResponse.ID)
		    throw new ZigBeeHAException( ((DefaultResponse) response).getStatus().toString());
		
		return (GetAlarmResponse) response;
	    } catch (ZigBeeClusterException e) {
		throw new ZigBeeHAException(e);
	    }
	}

	public void resetAlarm(int clusterId, int attributeId) throws ZigBeeHAException {
	    try {
		DefaultResponse response = (DefaultResponse) alarmsCluster.resetAlarm(clusterId, attributeId);
		if (response.getStatus() != Status.SUCCESS)
		    throw new ZigBeeHAException(response.getStatus().toString());
	    } catch (ZigBeeClusterException e) {
		throw new ZigBeeHAException(e);
	    }
	}

	public void resetAlarmLog() throws ZigBeeHAException {
	    try {
		DefaultResponse response = (DefaultResponse) alarmsCluster.resetAlarmLog();
		if (response.getStatus() != Status.SUCCESS)
		    throw new ZigBeeHAException(response.getStatus().toString());
	    } catch (ZigBeeClusterException e) {
		throw new ZigBeeHAException(e);
	    }
	}

	public void resetAllAlarms() throws ZigBeeHAException{
	    try {
		DefaultResponse response = (DefaultResponse) alarmsCluster.resetAllAlarms();
		if (response.getStatus() != Status.SUCCESS)
		    throw new ZigBeeHAException(response.getStatus().toString());
	    } catch (ZigBeeClusterException e) {
		throw new ZigBeeHAException(e);
	    }
	}
}
