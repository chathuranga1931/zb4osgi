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

package it.cnr.isti.zigbee.ha.cluster.glue.general;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.general.alarms.AlarmListener;
import it.cnr.isti.zigbee.zcl.library.api.general.alarms.GetAlarmResponse;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public interface Alarms extends Cluster {
    
    public Attribute getAttributeAlarmCount();

    public void resetAlarm(int clusterId, int attributeId) throws ZigBeeHAException;

    public void resetAllAlarms() throws ZigBeeHAException;

    public GetAlarmResponse getAlarm() throws ZigBeeHAException;

    public void resetAlarmLog() throws ZigBeeHAException;

    public boolean addAlarmListerner(AlarmListener listener);

    public boolean removeAlarmListerner(AlarmListener listener);

}
