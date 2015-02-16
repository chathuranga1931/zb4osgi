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
package it.cnr.isti.zigbee.zcl.library.impl.general.groups;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.Groups;
import it.cnr.isti.zigbee.zcl.library.impl.RawClusterImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ResponseImpl;

import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 *
 */
public class AddGroupResponseImplTest {

    @Test
    public void testAddGroupResponseImpl() {
        try {
            AddGroupResponseImpl response = new AddGroupResponseImpl(
                new ResponseImpl(
                        new RawClusterImpl(
                                Groups.ID,
                                new byte[]{0x09, 0x18, 0x00, 0x00, 0x00, (byte) 0xf0 }
                        ),
                        Groups.ID
                )
            );
            assertEquals( Status.SUCCESS, response.getStatus() );
            assertEquals( 0xf000, response.getGroupId() );
        } catch (ZigBeeClusterException e) {
            e.printStackTrace();
            fail("Exception thrwon "+e.getMessage());
        }
    }

}
