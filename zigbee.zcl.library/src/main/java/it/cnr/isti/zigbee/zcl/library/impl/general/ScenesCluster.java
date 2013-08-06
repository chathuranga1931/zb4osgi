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

package it.cnr.isti.zigbee.zcl.library.impl.general;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.Scenes;
import it.cnr.isti.zigbee.zcl.library.api.general.scenes.AddScenePayload;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.AddSceneCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.AddSceneResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.GetSceneMembershipCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.GetSceneMembershipResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.RecallSceneCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.RemoveAllScenesCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.RemoveAllScenesResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.RemoveSceneCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.RemoveSceneResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.StoreSceneCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.StoreSceneResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.ViewSceneCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.scenes.ViewSceneResponseImpl;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class ScenesCluster extends ZCLClusterBase implements Scenes {
	
	private final AttributeImpl sceneCount;
	private final AttributeImpl currentScene;
	//TODO Implement current group
	//private final AttributeImpl currentGroup;
	private final AttributeImpl sceneValid;
	private final AttributeImpl nameSupport;
	private final AttributeImpl lastConfiguredBy;
	
	private final Attribute[] attributes;
		
	public ScenesCluster(ZigBeeDevice zbDevice){
		super(zbDevice);
		 sceneCount  = new AttributeImpl(zbDevice,this,Attributes.SCENE_COUNT);
		 currentScene = new AttributeImpl(zbDevice,this,Attributes.CURRENT_SCENE);
		 //TODO currentGroup = new AttributeImpl(zbDevice,this,AttributeDescriptor.CURRENT_GROUP)
		 sceneValid = new AttributeImpl(zbDevice,this,Attributes.SCENE_VALID);
		 nameSupport = new AttributeImpl(zbDevice,this,Attributes.NAME_SUPPORT_SCENES);
		 lastConfiguredBy = new AttributeImpl(zbDevice,this,Attributes.LAST_CONFIGURED_BY);
		 
		attributes = new AttributeImpl[]{sceneCount, currentScene, //TODO currentGroup, 
				sceneValid, nameSupport, lastConfiguredBy};
	}

	@Override
	public short getId() {
		return Scenes.ID;
	}

	@Override
	public String getName() {
		return Scenes.NAME;
	}

	@Override
	public Attribute[] getStandardAttributes() {
		return attributes;
	}

	public Response addScene(AddScenePayload scenepayload) throws ZigBeeClusterException{
		AddSceneCommand addSceneCmd = new AddSceneCommand(scenepayload);
		Response response = invoke(addSceneCmd);
		return new AddSceneResponseImpl(response);
	}

	public Attribute getAttributeCurrentGroup() {
		return null; //TODO return currentGroup;
	}

	public Attribute getAttributeCurrentScene() {
		return currentScene;
	}

	public Attribute getAttributeLastConfiguredBy() {
		return lastConfiguredBy;
	}

	public Attribute getAttributeNameSupport() {
		return nameSupport;
	}

	public Attribute getAttributeSceneCount() {
		return sceneCount;
	}

	public Attribute getAttributeSceneValid() {
		return sceneValid;
	}

	public Response getSceneMembership(int groupId) throws ZigBeeClusterException{
		GetSceneMembershipCommand getSceneMemCmd = new GetSceneMembershipCommand(groupId);
		Response response = invoke(getSceneMemCmd);
		return new GetSceneMembershipResponseImpl(response);
	}

	public void recallScene(int groupId, short sceneId) throws ZigBeeClusterException{
		RecallSceneCommand recallSceneCmd = new RecallSceneCommand(groupId, sceneId);
		invoke(recallSceneCmd);
	}

	public Response removeAllScenes(int groupId) throws ZigBeeClusterException{
		RemoveAllScenesCommand removeAllScenes = new RemoveAllScenesCommand(groupId);
		Response response = invoke(removeAllScenes);
		return new RemoveAllScenesResponseImpl(response);
		
	}

	public Response removeScene(int groupId, short sceneId) throws ZigBeeClusterException{
		RemoveSceneCommand removeSceneCmd = new RemoveSceneCommand(groupId, sceneId);
		Response response = invoke(removeSceneCmd);
		return new RemoveSceneResponseImpl(response);
	}

	public Response storeScene(int groupId, short sceneId) throws ZigBeeClusterException{
		StoreSceneCommand storeSceneCmd = new StoreSceneCommand(groupId, sceneId);
		Response response = invoke(storeSceneCmd);
		return new StoreSceneResponseImpl(response);
	}

	public Response viewScene(int groupId, short sceneId) throws ZigBeeClusterException{
		ViewSceneCommand viewSceneCmd = new ViewSceneCommand(groupId,sceneId);
		Response response = invoke(viewSceneCmd);
		return new ViewSceneResponseImpl(response);
	}

}
