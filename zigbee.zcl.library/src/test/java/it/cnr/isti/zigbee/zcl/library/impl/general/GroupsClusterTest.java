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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.Groups;
import it.cnr.isti.zigbee.zcl.library.api.general.groups.AddGroupResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.groups.GetGroupMembershipResponse;
import it.cnr.isti.zigbee.zcl.library.impl.RawClusterImpl;

import org.junit.Test;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate: 2014-04-01 12:02:50
 *          +0200 (mar, 01 apr 2014) $)
 * @since 0.8.0
 * 
 */
public class GroupsClusterTest {

	private ZigBeeDevice createMockDevice(final byte[] response)
			throws ZigBeeBasedriverException {
		ZigBeeDevice mock = createMock(ZigBeeDevice.class);

		expect(mock.invoke((Cluster) anyObject())).andReturn(
				new RawClusterImpl(Groups.ID, response));
		replay(mock);
		return mock;
	}

	@Test
	public void testAddGroup() {
		GroupsCluster cluster = null;
		ZigBeeDevice device = null;
		try {
			device = createMockDevice(new byte[] { 0x09, 0x18,
					Groups.ADD_GROUP_ID, 0x00, 0x00, (byte) 0xf0 });
			cluster = new GroupsCluster(device);
		} catch (ZigBeeBasedriverException ignored) {
		}
		try {
			AddGroupResponse response = (AddGroupResponse) cluster.addGroup(
					0xFF00, "hello world!");
			assertEquals(Status.SUCCESS, response.getStatus());
			assertEquals(0xf000, response.getGroupId());
		} catch (ZigBeeClusterException ex) {
			fail("Unexpected exception " + ex);
			ex.printStackTrace();
		}
	}

	@Test
	public void testGetGroupMembership() {
		GroupsCluster cluster = null;
		ZigBeeDevice device = null;
		try {
			device = createMockDevice(new byte[] { 0x09, 0x18,
					Groups.GET_GROUP_MEMBERSHIP_ID, 0x10,
					0x04, // Capacity, Group Count
					0x10, 0x00, 0x00, 0x02, (byte) 0xF0, 0x00, 0x00,
					(byte) 0xFF // Group List
			});
			;
			cluster = new GroupsCluster(device);
		} catch (ZigBeeBasedriverException ignored) {
		}
		try {
			GetGroupMembershipResponse response = (GetGroupMembershipResponse) cluster
					.getGroupMembership(new int[] { 0x03, 0x0A });
			assertEquals(0x10, response.getCapacity());
			assertEquals(0x04, response.getGroupList().length);
			assertArrayEquals(new int[] { 0x0010, 0x0200, 0x00F0, 0xFF00 },
					response.getGroupList());
		} catch (ZigBeeClusterException ex) {
			fail("Unexpected exception " + ex);
			ex.printStackTrace();
		}
	}
}
