/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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
package it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_zone;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ClusterFilter;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASZone;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLFrame;

public class IAS_ZoneZoneStatusChangeNotificationFilter implements ClusterFilter {

    public final static IAS_ZoneZoneStatusChangeNotificationFilter FILTER = new IAS_ZoneZoneStatusChangeNotificationFilter();

    private IAS_ZoneZoneStatusChangeNotificationFilter() {

    }

    public boolean match(Cluster cluster) {
        if( cluster.getId() != IASZone.ID )  return false;

        ZCLFrame frame = new ZCLFrame(cluster);
        return frame.getHeader().getCommandId() == IASZone.ZONE_STATUS_CHANGE_NOTIFICATION_ID
                && frame.getHeader().getFramecontrol().isClusterSpecificCommand();
    }
}