/*

   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
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

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;
import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.Alarms;
import it.cnr.isti.zigbee.zcl.library.api.general.Groups;
import it.cnr.isti.zigbee.zcl.library.api.general.groups.AddGroupResponse;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.impl.RawClusterImpl;

import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 *
 */
public class AlarmsClusterTest {

    private ZigBeeDevice createMockDevice() throws ZigBeeBasedriverException {
        ZigBeeDevice mock = createMock(ZigBeeDevice.class);

        expect(mock.invoke( (Cluster) anyObject()))
            .andReturn( new RawClusterImpl(
                            Alarms.ID,
                            new byte[]{0x28, 0x0A, 0x0B, 0x00, 0x00 }
            ) );
        replay( mock );
        return mock;
    }

    @Test
    public void testResetAlarm() {
        AlarmsCluster cluster = null;
        ZigBeeDevice device = null;
        try {
            device = createMockDevice();
            cluster = new AlarmsCluster(device);
        } catch (ZigBeeBasedriverException ignored) {
        }
        try {
            DefaultResponse response = (DefaultResponse) cluster.resetAlarm(1, 1);
            assertEquals( Status.SUCCESS, response.getStatus() );
        } catch (ZigBeeClusterException ex) {
            fail("Unexpected exception "+ex);
            ex.printStackTrace();
        }
    }

}
