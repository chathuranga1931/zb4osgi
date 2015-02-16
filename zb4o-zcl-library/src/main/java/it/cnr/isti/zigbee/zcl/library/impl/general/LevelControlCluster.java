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
import it.cnr.isti.zigbee.zcl.library.api.general.LevelControl;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.EmptyPayloadCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;
import it.cnr.isti.zigbee.zcl.library.impl.general.level_control.MoveCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.level_control.MoveToLevelCommand;
import it.cnr.isti.zigbee.zcl.library.impl.general.level_control.StepCommand;
import it.cnr.isti.zigbee.zcl.library.impl.global.DefaultResponseImpl;
/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class LevelControlCluster extends ZCLClusterBase implements LevelControl {


    private final AttributeImpl currentLevel;
    private final AttributeImpl remainingTime;
    private final AttributeImpl onOffTransactionTime;
    private final AttributeImpl onLevel;

    private final Attribute[] attributes;

    private static EmptyPayloadCommand CMD_STOP = new EmptyPayloadCommand()
        .setId(LevelControl.STOP_ID)
        .setClientServerDirection(true)
        .setClusterSpecific(true)
        .setManufacturerExtension(false);

    private static EmptyPayloadCommand CMD_STOP_WITH_ONOFF = new EmptyPayloadCommand()
        .setId(LevelControl.STOP_WITH_ONOFF_ID)
        .setClientServerDirection(true)
        .setClusterSpecific(true)
        .setManufacturerExtension(false);


    public LevelControlCluster(ZigBeeDevice zbDevice){
        super(zbDevice);
        currentLevel = new AttributeImpl(zbDevice,this,Attributes.CURRENT_LEVEL);
        remainingTime = new AttributeImpl(zbDevice,this,Attributes.REMAINING_TIME);
        onOffTransactionTime = new AttributeImpl(zbDevice,this,Attributes.ON_OFF_TRANSITION_TIME);
        onLevel = new AttributeImpl(zbDevice,this,Attributes.ON_LEVEL);
        attributes = new AttributeImpl[]{currentLevel, remainingTime, onOffTransactionTime, onLevel};
    }

    @Override
    public short getId() {
        return LevelControl.ID;
    }

    @Override
    public String getName() {
        return LevelControl.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }

    public Attribute getAttributeCurrentLevel() {
        return currentLevel;
    }

    public Attribute getAttributeOnLevel() {
        return onLevel;
    }

    public Attribute getAttributeOnOffTransitionTime() {
        return onOffTransactionTime;
    }

    public Attribute getAttributeRemainingTime() {
        return remainingTime;
    }

    public Response move(byte mode, short rate) throws ZigBeeClusterException{
        enableDefaultResponse();
        MoveCommand moveCmd = new MoveCommand(mode,rate);
        Response response = invoke(moveCmd);
        return new DefaultResponseImpl(response);
    }

    public Response moveWithOnOff(byte mode, short rate) throws ZigBeeClusterException {
        enableDefaultResponse();
        MoveCommand moveCmd = new MoveCommand(mode,rate,true);
        Response response = invoke(moveCmd);
        return new DefaultResponseImpl(response);
    }

    public Response moveToLevel(short level, int time) throws ZigBeeClusterException{
        enableDefaultResponse();
        MoveToLevelCommand moveToLevCmd = new MoveToLevelCommand(level,time);
        Response response = invoke(moveToLevCmd);
        return new DefaultResponseImpl(response);
    }

    public Response moveToLevelWithOnOff(short level, int time) throws ZigBeeClusterException {
        enableDefaultResponse();
        MoveToLevelCommand moveToLevCmd = new MoveToLevelCommand(level,time,true);
        Response response = invoke(moveToLevCmd);
        return new DefaultResponseImpl(response);
    }

    public Response step(byte mode, short step, int time) throws ZigBeeClusterException{
        enableDefaultResponse();
        StepCommand stepCmd = new StepCommand(mode,step,time);
        Response response = invoke(stepCmd);
        return new DefaultResponseImpl(response);
    }

    public Response stepWithOnOff(byte mode, short step, int time) throws ZigBeeClusterException {
        enableDefaultResponse();
        StepCommand stepCmd = new StepCommand(mode,step,time,true);
        Response response = invoke(stepCmd);
        return new DefaultResponseImpl(response);
    }

    public Response stop() throws ZigBeeClusterException{
        enableDefaultResponse();
        Response response = invoke(CMD_STOP);
        return new DefaultResponseImpl(response);
    }

}
