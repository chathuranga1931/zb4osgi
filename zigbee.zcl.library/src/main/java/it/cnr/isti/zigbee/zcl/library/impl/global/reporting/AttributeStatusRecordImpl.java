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

package it.cnr.isti.zigbee.zcl.library.impl.global.reporting;

import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.ZBDeserializer;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.global.AttributeStatusRecord;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.AttributeDescriptor;


/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version  $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class AttributeStatusRecordImpl implements AttributeStatusRecord {
	
	private byte status;
	private int direction;
	private int attributeId;

	
	
	protected AttributeStatusRecordImpl(byte status, int direction, int attributeId) {
		super();
		this.status = status;
		this.direction = direction;
		this.attributeId = attributeId;
	}

	public AttributeStatusRecordImpl(AttributeDescriptor attrib, ZBDeserializer deserializer) 
	throws ZigBeeClusterException {
		
		status = deserializer.read_byte();
		direction = deserializer.read_byte();
		attributeId = deserializer.read_short();
		if( attributeId != attrib.getId() ) {
			throw new ZigBeeClusterException(
				"attributeId read from the data doesn't match the expected value "+attributeId+" != "+attrib.getId()
			);
		}
		
	}

	public AttributeStatusRecordImpl(Attribute attrib, ZBDeserializer deserializer) 
	throws ZigBeeClusterException {
		
		status = deserializer.read_byte();
		if ( status != 0 ) {
			return;
		}
		direction = deserializer.read_byte();
		attributeId = deserializer.read_short();
		if( attributeId != attrib.getId() ) {
			throw new ZigBeeClusterException(
				"attributeId read from the data doesn't match the expected value "+attributeId+" != "+attrib.getId()
			);
		}
		
	}
	
	
	public byte getStatus() {
		return status;
	}

	public int getDirection() {
		return direction;
	}
	
	public int getAttributeId() {
		return attributeId;
	}
	

}
