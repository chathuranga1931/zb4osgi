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

package it.cnr.isti.zigbee.ha.cluster.glue.general;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.AddScenePayload;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.AddSceneResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.GetSceneMembershipResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.RemoveAllScenesResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.RemoveSceneResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.StoreSceneResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.ViewSceneResponse;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public interface Scenes extends Cluster{

	public AddSceneResponse addScene(AddScenePayload scenePayload) throws ZigBeeHAException;
	
	public GetSceneMembershipResponse getSceneMembership(int groupId) throws ZigBeeHAException;
	
	public void recallScene(int groupId, short sceneId) throws ZigBeeHAException;
	
	public RemoveAllScenesResponse removeAllScene(int groupId) throws ZigBeeHAException;
	
	public RemoveSceneResponse removeScene(int groupId, short sceneId) throws ZigBeeHAException;

	public StoreSceneResponse storeScene(int groupId, short sceneId) throws ZigBeeHAException;

	public ViewSceneResponse viewScene(int groupId, short sceneId) throws ZigBeeHAException;
	
	public Attribute getSceneCount();
	
	public Attribute getCurrentScene();
	
	public Attribute getCurrentGroup();
	
	public Attribute getSceneValid();
	
	public Attribute getNameSupport();
	
	public Attribute getLastConfiguredBy();
}
