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
package it.cnr.isti.zigbee.zcl.cluster.impl.global.reporting;

import static org.junit.Assert.assertNotNull;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeType;
import it.cnr.isti.zigbee.zcl.library.api.global.AttributeReportingConfigurationRecord;
import it.cnr.isti.zigbee.zcl.library.impl.global.reporting.AttributeReportingConfigurationRecordImpl;
import it.cnr.isti.zigbee.zcl.library.impl.global.reporting.ConfigureReportingCommand;

import org.junit.Test;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class ConfigureReportingCommandTest {

	@Test
	public void testGetPayload() {
		
		ConfigureReportingCommand cmd = new ConfigureReportingCommand(
				new AttributeReportingConfigurationRecord[]{new AttributeReportingConfigurationRecordImpl(
						new Attribute(){
							public int getId() {
								return 0;
							}
							public String getName() {
								return "";
							}
							public Subscription getSubscription() {
								return null;
							}
							public Class getType() {
								return null;
							}

							public Object getValue()
									throws ZigBeeClusterException {
								// TODO Auto-generated method stub
								return null;
							}

							public ZigBeeType getZigBeeType() {
								return ZigBeeType.UnsignedInteger32bit;
							}

							public boolean isReportable() {
								return true;
							}

							public boolean isWritable() {
								// TODO Auto-generated method stub
								return false;
							}

							public void setValue(Object o)
									throws ZigBeeClusterException {
								// TODO Auto-generated method stub
								
							}
							
						}, 1, 60, 900, null , 1800)
				});
		assertNotNull("Failed to build payload",cmd.getPayload());
	}

}
