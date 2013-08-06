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
import it.cnr.isti.zigbee.ha.cluster.glue.general.Basic;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.impl.general.BasicCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class BasicImpl implements Basic{

	private BasicCluster basicCluster;
	private Attribute zclVersion;
	private Attribute applicationVersion;
	private Attribute stackVersion;
	private Attribute hwVersion;
	private Attribute manufacturerName;
	private Attribute modelIdentifier;
	private Attribute dateCode;
	private Attribute powerSource;
	private Attribute locationDescription;
	private Attribute physicalEnviroment;
	private Attribute deviceEnabled;
	private Attribute alarmMask;
	private Attribute disableLocalConfig;

	public BasicImpl(ZigBeeDevice zbDevice){
		basicCluster = new BasicCluster(zbDevice);
		zclVersion = basicCluster.getAttributeZCLVersion();
		applicationVersion = basicCluster.getAttributeApplicationVersion();
		stackVersion = basicCluster.getAttributeStackVersion();
		hwVersion = basicCluster.getAttributeHWVersion();
		manufacturerName = basicCluster.getAttributeManufacturerName();
		modelIdentifier = basicCluster.getAttributeModelIdentifier();
		dateCode = basicCluster.getAttributeDateCode();
		powerSource = basicCluster.getPowerSource();
		locationDescription = basicCluster.getAttributeLocationDescription();
		physicalEnviroment = basicCluster.getAttributePhysicalEnvironment();
		deviceEnabled = basicCluster.getAttributeDeviceEnabled();
		alarmMask = basicCluster.getAttributeAlarmMask();
		disableLocalConfig = basicCluster.getAttributeDisableLocalConfig();
	}

	public Attribute getDisableLocalConfig() {
		return disableLocalConfig;
	}

	public Attribute getAlarmMask() {
		return alarmMask;
	}

	public Attribute getApplicationVersion() {
		return applicationVersion;
	}

	public Attribute getDateCode() {
		return dateCode;
	}

	public boolean getDeviceEnabled() throws ZigBeeHAException{
		try {
			Boolean value = (Boolean) deviceEnabled.getValue();
			return value.booleanValue();
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public Attribute getHWVersion() {
		return hwVersion;
	}

	public String getLocationDescription() throws ZigBeeHAException{
		try {
			String value = (String) locationDescription.getValue();
			return value;
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public Attribute getManufacturerName() {
		return manufacturerName;
	}

	public Attribute getModelIdentifier() {
		return modelIdentifier;
	}

	public Attribute getPhysicalEnvironment() {
		return physicalEnviroment;
	}

	public Attribute getPowerSource() {
		return powerSource;
	}

	public Attribute getStackVersion() {
		return stackVersion;
	}

	public Attribute getZCLVersion() {
		return zclVersion;
	}

	public void resetToFactoryDefault() throws ZigBeeHAException{
		try {
			DefaultResponse response = basicCluster.resetToFactoryDefault();
			if (! response.getStatus().equals(Status.SUCCESS))
				throw new ZigBeeHAException(response.getStatus().toString());
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public Subscription[] getActiveSubscriptions() {
		return basicCluster.getActiveSubscriptions();
	}

	public int getId() {
		return basicCluster.getId();
	}

	public String getName() {
		return basicCluster.getName();
	}

	public Attribute getAttribute(int id) {
		Attribute[] attributes = basicCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id )
				return attributes[i];
		}
		return null;
	}

	public Attribute[] getAttributes() {
		return basicCluster.getAvailableAttributes();
	}

}
