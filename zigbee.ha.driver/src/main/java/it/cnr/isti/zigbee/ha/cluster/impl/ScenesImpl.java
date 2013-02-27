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

package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.AddScenePayload;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.AddSceneResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.GetSceneMembershipResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.RemoveAllScenesResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.RemoveSceneResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.StoreSceneResponse;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.ViewSceneResponse;
import it.cnr.isti.zigbee.zcl.library.impl.general.ScenesCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class ScenesImpl implements Scenes {
	
	private ScenesCluster scenesCluster;
	private Attribute sceneCount;
	private Attribute currentScene;
	private Attribute currentGroup;
	private Attribute sceneValid;
	private Attribute nameSupport;
	private Attribute lastConfiguredBy;
	public ScenesImpl(ZigBeeDevice zbDevice){
		scenesCluster = new ScenesCluster(zbDevice);
		sceneCount = scenesCluster.getAttributeSceneCount();
		currentScene = scenesCluster.getAttributeCurrentScene();
		currentGroup = scenesCluster.getAttributeCurrentGroup();
		sceneValid = scenesCluster.getAttributeSceneValid();
		nameSupport = scenesCluster.getAttributeNameSupport();
		lastConfiguredBy = scenesCluster.getAttributeLastConfiguredBy();
		
	}

	public AddSceneResponse addScene(AddScenePayload scenepayload) throws ZigBeeHAException{
		try {
			AddSceneResponse response = (AddSceneResponse)scenesCluster.addScene(scenepayload);
			return response;
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public Attribute getCurrentGroup() {
		return currentGroup;
	}

	public Attribute getCurrentScene() {
		return currentScene;
	}

	public Attribute getLastConfiguredBy() {
		return lastConfiguredBy;
	}

	public Attribute getNameSupport() {
		return nameSupport;
	}

	public Attribute getSceneCount() {
		return sceneCount;
	}

	public GetSceneMembershipResponse getSceneMembership(int groupId) throws ZigBeeHAException{
		try {
			GetSceneMembershipResponse response = (GetSceneMembershipResponse)scenesCluster.getSceneMembership(groupId);
			return response;
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public Attribute getSceneValid() {
		return sceneValid;
	}

	public void recallScene(int groupId, short sceneId) throws ZigBeeHAException{
		try {
			scenesCluster.recallScene(groupId, sceneId);
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}

	}

	public RemoveAllScenesResponse removeAllScene(int groupId) throws ZigBeeHAException{
		try {
			RemoveAllScenesResponse response = (RemoveAllScenesResponse)scenesCluster.removeAllScenes(groupId);
			return response;
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public RemoveSceneResponse removeScene(int groupId, short sceneId) throws ZigBeeHAException{
		try {
			RemoveSceneResponse response = (RemoveSceneResponse)scenesCluster.removeScene(groupId, sceneId);
			return response;
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public StoreSceneResponse storeScene(int groupId, short sceneId) throws ZigBeeHAException{
		try {
			StoreSceneResponse response = (StoreSceneResponse)scenesCluster.storeScene(groupId, sceneId);
			return response;
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public ViewSceneResponse viewScene(int groupId, short sceneId) throws ZigBeeHAException{
		try {
			ViewSceneResponse response = (ViewSceneResponse)scenesCluster.viewScene(groupId, sceneId);
			return response;
		} catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public Subscription[] getActiveSubscriptions() {
		return scenesCluster.getActiveSubscriptions();
	}

	public int getId() {
		return scenesCluster.getId();
	}

	public String getName() {
		return scenesCluster.getName();
	}

	public Attribute getAttribute(int id) {		
		Attribute[] attributes = scenesCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public Attribute[] getAttributes() {
		return scenesCluster.getAvailableAttributes();
	}
	
}
