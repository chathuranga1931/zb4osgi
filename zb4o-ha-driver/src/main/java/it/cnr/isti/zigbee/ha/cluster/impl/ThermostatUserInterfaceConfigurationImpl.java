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
import it.cnr.isti.zigbee.ha.cluster.glue.general.ThermostatUserInterfaceConfiguration;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.impl.general.ThermostatUserInterfaceConfigurationCluster;
/**
 * 
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision:  $ ($LastChangedDate: $)
 *
 */

public class ThermostatUserInterfaceConfigurationImpl implements ThermostatUserInterfaceConfiguration{
	
	private ThermostatUserInterfaceConfigurationCluster thermostatUserInterfaceConfigurationCluster;


	public ThermostatUserInterfaceConfigurationImpl(ZigBeeDevice zbDevice){
		thermostatUserInterfaceConfigurationCluster = new ThermostatUserInterfaceConfigurationCluster(zbDevice);
		
	}

	public int getId() {
		
		return thermostatUserInterfaceConfigurationCluster.getId();
	}

	public String getName() {
	
		return thermostatUserInterfaceConfigurationCluster.getName();
	}

	public Subscription[] getActiveSubscriptions() {
		return thermostatUserInterfaceConfigurationCluster.getActiveSubscriptions();
	}

	public Attribute[] getAttributes() {
	
		return thermostatUserInterfaceConfigurationCluster.getAvailableAttributes();
	}

	public Attribute getAttribute(int id) {
		Attribute[] attributes = thermostatUserInterfaceConfigurationCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public String getDescription() throws ZigBeeHAException {
		 try {
	            return (String) thermostatUserInterfaceConfigurationCluster.getAttributeDescription().getValue();
	        } catch (ZigBeeClusterException e) {
	            throw new ZigBeeHAException(e);
	        }
	}


}