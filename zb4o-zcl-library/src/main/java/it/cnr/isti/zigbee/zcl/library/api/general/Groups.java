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

package it.cnr.isti.zigbee.zcl.library.api.general;

import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;

/**
 * This class represent the <b>Groups</b> Cluster as defined by the document:<br>
 * <i>ZigBee Cluster Library</i> public release version 075123r01ZB
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version  $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface Groups extends ZCLCluster {
	
	static final short ID = 0x0004;
	static final String NAME = "Groups";
	static final String DESCRIPTION = "Attributes and commands for group configuration and manipulation";

	static final byte ADD_GROUP_ID = 0x00;
	static final byte VIEW_GROUP_ID = 0x01;
	static final byte GET_GROUP_MEMBERSHIP_ID = 0x02;
	static final byte REMOVE_GROUP_ID = 0x03;
	static final byte REMOVE_ALL_GROUP_ID = 0x04;
	static final byte ADD_GROUP_IF_IDENTIFYING_ID = 0x05;
	
	public Attribute getAttributeNameSupport();
	
	public Response addGroup(int groupId, String name) throws ZigBeeClusterException;
	public Response viewGroup(int groupId) throws ZigBeeClusterException;
	public Response getGroupMembership(int[] groupList) throws ZigBeeClusterException;
	public Response removeGroup(int groupId) throws ZigBeeClusterException;
	public Response removeAllGroup() throws ZigBeeClusterException;
	public Response addGroupIfIdentifying(int groupId, String name) throws ZigBeeClusterException;
}
