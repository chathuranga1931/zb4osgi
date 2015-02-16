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

package it.cnr.isti.zigbee.zcl.library.impl.general.alarms;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ClusterFilter;
import it.cnr.isti.zigbee.zcl.library.api.general.Alarms;
import it.cnr.isti.zigbee.zcl.library.api.general.alarms.AlarmResponse;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLFrame;

/**
 * This class should filter out all the ZigBee cluster that do not belong to the<br>
 * {@link AlarmResponse} type from the {@link Alarms} cluster, by checking the data itself.<br>
 * <br>
 * <b>NOTE:</b>to use this class the {@link #FILTER} constant should be used
 * 
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.4.0
 *
 */
public class AlarmsClusterFilter implements ClusterFilter {

	/**
	 * Singelton instance of the filter
	 */
	public final static AlarmsClusterFilter FILTER = new AlarmsClusterFilter();
	
	private AlarmsClusterFilter() {
		
	}
	
	public boolean match(Cluster cluster) {
    	if( cluster.getId() != Alarms.ID )  return false;
	    	
		ZCLFrame frame = new ZCLFrame(cluster);
		return frame.getHeader().getCommandId() == AlarmResponse.ID
			&& frame.getHeader().getFramecontrol().isClusterSpecificCommand();
	}

}
