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

package it.cnr.isti.zigbee.zcl.library.impl.general.level_control;

import it.cnr.isti.zigbee.zcl.library.api.core.ZBSerializer;
import it.cnr.isti.zigbee.zcl.library.api.general.LevelControl;
import it.cnr.isti.zigbee.zcl.library.impl.core.AbstractCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultSerializer;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class MoveToLevelCommand extends AbstractCommand {
	
	private short level;
	private int time;
	
	public MoveToLevelCommand(short level, int time){
		this(level, time, false);
	}
	
	public MoveToLevelCommand(short level, int time, boolean hasOnOff) {
		super(LevelControl.MOVE_TO_LEVEL_ID);
		if ( hasOnOff ) {
			setId(LevelControl.MOVE_TO_LEVEL_WITH_ONOFF_ID);
		}
		this.level = level;
		this.time = time;
	}

	public byte[] getPayload(){	
		if( payload == null){
			payload = new byte[3];
			ZBSerializer serializer = new DefaultSerializer(payload,0);
			serializer.append_byte((byte)level);
			serializer.append_short((short)time);
		}
		return payload;
	}

}
