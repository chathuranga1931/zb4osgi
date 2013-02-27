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

package it.cnr.isti.zigbee.zcl.library.impl.general;


import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.Basic;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.EmptyPayloadCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;
import it.cnr.isti.zigbee.zcl.library.impl.global.DefaultResponseImpl;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class BasicCluster extends ZCLClusterBase implements Basic {
	
	private final AttributeImpl zclVersion;  
	private final AttributeImpl applicationVersion;
	private final AttributeImpl stackVersion;
	private final AttributeImpl hwVersion;
	private final AttributeImpl manufacturerName;
	private final AttributeImpl modelIdentifier;
	private final AttributeImpl dateCode;
	private final AttributeImpl powerSource;
	
	private final AttributeImpl locationDescription;
	private final AttributeImpl physicalEnvironment;
	private final AttributeImpl deviceEnabled;
	private final AttributeImpl alarmMask;
	private final AttributeImpl disableLocalConfig;
	
	private final Attribute[] attributes;
	
	public BasicCluster(ZigBeeDevice zbDevice){
		super(zbDevice);
		zclVersion = new AttributeImpl(zbDevice,this,Attributes.ZCL_VERSION);
		applicationVersion = new AttributeImpl(zbDevice,this,Attributes.APPLICATION_VERSION);
		stackVersion = new AttributeImpl(zbDevice,this,Attributes.STACK_VERSION);
		hwVersion = new AttributeImpl(zbDevice,this,Attributes.HW_VERSION);
		manufacturerName = new AttributeImpl(zbDevice,this,Attributes.MANUFACTURER_NAME);
		modelIdentifier = new AttributeImpl(zbDevice,this,Attributes.MODEL_IDENTIFIER);
		dateCode = new AttributeImpl(zbDevice,this,Attributes.DATE_CODE);
		powerSource = new AttributeImpl(zbDevice,this,Attributes.POWER_SOURCE);
		locationDescription = new AttributeImpl(zbDevice,this,Attributes.LOCATION_DESCRIPTION);
		physicalEnvironment = new AttributeImpl(zbDevice,this,Attributes.PHYSICAL_ENVIRONMENT);
		deviceEnabled = new AttributeImpl(zbDevice,this,Attributes.DEVICE_ENABLED);
		alarmMask = new AttributeImpl(zbDevice,this,Attributes.ALARM_MASK);
		disableLocalConfig = new AttributeImpl(zbDevice,this,Attributes.DISABLE_LOCAL_CONFIG);
		attributes = new AttributeImpl[]{zclVersion, applicationVersion, stackVersion,
				hwVersion, manufacturerName, modelIdentifier, dateCode, powerSource,
				locationDescription, physicalEnvironment, deviceEnabled, alarmMask, disableLocalConfig};
	}
	
	private static EmptyPayloadCommand CMD_RESET_TO_FACTORY_DEFAULT = new EmptyPayloadCommand()
	.setId(Basic.RESET_TO_FACTORY_DEFAULT_ID)
	.setClientServerDirection(true)
	.setClusterSpecific(true)
	.setManufacturerExtension(false);

	@Override
	public short getId() {
		return Basic.ID;
	}

	@Override
	public String getName() {
		return Basic.NAME;
	}

	@Override
	public Attribute[] getStandardAttributes() {
		return attributes;
	}

	public Attribute getAttributeAlarmMask() {
		return alarmMask;
	}

	public Attribute getAttributeApplicationVersion() {
		return alarmMask;
	}

	public Attribute getAttributeDateCode() {
		return dateCode;
	}

	public Attribute getAttributeDeviceEnabled() {
		return deviceEnabled;
	}

	public Attribute getAttributeHWVersion() {
		return hwVersion;
	}

	public Attribute getAttributeLocationDescription() {
		return locationDescription;
	}

	public Attribute getAttributeManufacturerName() {
		return manufacturerName;
	}

	public Attribute getAttributeModelIdentifier() {
		return modelIdentifier;
	}
                                 
	public Attribute getAttributePhysicalEnvironment() {
		return physicalEnvironment;
	}

	public Attribute getAttributeStackVersion() {
		return stackVersion;
	}

	public Attribute getAttributeZCLVersion() {
		return zclVersion;
	}

	public Attribute getPowerSource() {
		return powerSource;
	}

	public Attribute getAttributeDisableLocalConfig() {
		return disableLocalConfig;
	}
	
	public DefaultResponse resetToFactoryDefault() throws ZigBeeClusterException{
		enableDefaultResponse();
		Response response = invoke(CMD_RESET_TO_FACTORY_DEFAULT);
		return  new DefaultResponseImpl(response);
	}

}
