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

package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.OccupacySensing;
import it.cnr.isti.zigbee.ha.device.api.lighting.OccupancySensor;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;

import org.osgi.framework.BundleContext;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 *         
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.2.0
 *
 */
public class OccupancySensorDevice extends HADeviceBase implements OccupancySensor {
	
	private OccupacySensing occupancySensing;
	
	public  OccupancySensorDevice(BundleContext ctx,ZigBeeDevice zbDevice) throws ZigBeeHAException {
		super(ctx,zbDevice);
		occupancySensing = (OccupacySensing) addCluster(HAProfile.OCCUPANCY_SENSING);
	}

	public OccupacySensing getOccupacySensing() {
		return occupancySensing;
	}
	

	@Override
	public String getName() {
		return OccupancySensor.NAME;
	}

	final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

		public int[] getCustomClusters() {
			return OccupancySensor.CUSTOM;
		}

		public int[] getMandatoryCluster() {
			return OccupancySensor.MANDATORY;
		}

		public int[] getOptionalCluster() {
			return OccupancySensor.OPTIONAL;
		}

		public int[] getStandardClusters() {
			return OccupancySensor.STANDARD;
		}
		
	};

	@Override
	public DeviceDescription getDescription() {
		return DEVICE_DESCRIPTOR;
	}


}
