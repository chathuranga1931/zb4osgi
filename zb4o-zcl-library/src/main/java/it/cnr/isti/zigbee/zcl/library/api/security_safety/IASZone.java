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

package it.cnr.isti.zigbee.zcl.library.api.security_safety;

import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneEnrollRequestPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneEnrollResponse;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneStatusChangeNotificationListener;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneStatusChangeNotificationPayload;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 *         
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface IASZone extends ZCLCluster{

	public static final short ID = 0x0500;
	static final String NAME = "IAS Zone";
	static final String DESCRIPTION = "Attributes and commands for IAS security zone device.";
	
	//received
	static final byte ZONE_ENROLL_RESPONSE_ID = 0x00;
	
	//generated
	static final byte ZONE_STATUS_CHANGE_NOTIFICATION_ID = 0x00;
	static final byte ZONE_ENROLL_REQUEST_ID = 0x01;

	// zone information attribute set
	public Attribute getAttributeZoneState();
	public Attribute getAttributeZoneType();
	public Attribute getAttributeZoneStatus();

	// zone settings attribute set
	public Attribute getAttributeIASCIEAddress();

	// commands generated
	public Response zoneStatusChangeNotification(ZoneStatusChangeNotificationPayload payload) throws ZigBeeClusterException;
	
	public ZoneEnrollResponse zoneEnrollRequest(ZoneEnrollRequestPayload payload) throws ZigBeeClusterException;

	/*
	 * @since 0.8.0
	 */
	public boolean addZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener);

	/*
	 * @since 0.8.0
	 */
	public boolean removeZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener);
}
