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
package it.cnr.isti.basedriver.stub.api.impl.EH;

import it.cnr.isti.basedriver.stub.api.impl.StubZigbeeDeviceBase;
import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeNode;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 895 $ ($LastChangedDate: 2013-08-06 18:00:05
 *          +0200 (mar, 06 ago 2013) $)
 * @since 0.1.0
 * 
 */
public class EnergyDummyDevice extends StubZigbeeDeviceBase {

	public EnergyDummyDevice(int endpoint, final ZigBeeNode node) {
		super(0x1081, 0, endpoint, 0x0104, new int[] { 
				0x0000, 
				0x0003, 
				0x0004, 
				0x0005, 
				0x0006, 
				0x000A, 
				0x0402, 
				0x0B00, 
				0x001b, 
				0x0B02, 
				0x0B03, 
				0x001a, 
				0x0b01, 
				0x0700, 
				0x0701, 
				0x0702, 
				0x0703,
				0x0016			
				
		}, new int[] {
				}, node);
		}
	

	@Override
	public Cluster stubInvoke(Cluster cluster) throws ZigBeeBasedriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stubSend(Cluster cluster) throws ZigBeeBasedriverException {
		// TODO Auto-generated method stub

	}

}
