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

package it.cnr.isti.zigbee.zcl.library.impl.core;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLHeader;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.impl.global.DefaultResponseImpl;

import com.itaca.ztool.util.ByteUtils;
/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class ResponseImpl implements Response {

    protected ZCLHeader header;
    private byte[] payload;

    public ResponseImpl(Cluster cluster, short expectedClusterId) throws ZigBeeClusterException{
        if (expectedClusterId != cluster.getId()){
            throw new ZigBeeClusterException("Expected Response for cluster Id: " + expectedClusterId + " but received message for cluster Id: "+cluster.getId());
        }
        ZCLFrame frame = new ZCLFrame(cluster);
        header = frame.getHeader();
        payload = frame.getPayload();
    }

    public ResponseImpl(Response response){
        header = response.getZCLHeader();
        payload = response.getPayload();
    }

    public byte getHeaderCommandId() {
        return header.getCommandId();
    }

    public byte[] getManufacturerId() {
        return header.getManufacturerId();
    }

    public boolean isClientServerDirection() {
        return header.getFramecontrol().isClientServerDirection();
    }

    public boolean isClusterSpecific() {
        return header.getFramecontrol().isClusterSpecificCommand();
    }

    public boolean isManufacturerExtension() {
        return header.getFramecontrol().isManufacturerExtension();
    }

    public boolean isDefaultResponseEnabled() {
        return header.getFramecontrol().isDefaultResponseEnabled();
    }

    public ZCLHeader getZCLHeader() {
        return header;
    }

    public byte[] getPayload() {
        return payload;
    }

    public static String toString(Response r){
        return
        "[ ZCL Header: " + ByteUtils.toBase16( r.getZCLHeader().toByte() )
                + ", ZCL Payload: " + ByteUtils.toBase16(r.getPayload())
        + "]";
    }

    public String toString(){
        return toString(this);
    }

    public static void checkSpecificCommandFrame(Response response, byte expectedCommandId) throws ZigBeeClusterException{
        byte commandId = response.getHeaderCommandId();
        if (commandId != expectedCommandId) {
            if (commandId == DefaultResponse.ID){
                DefaultResponse  defaultResponse = new DefaultResponseImpl(response);
                throw new ZigBeeClusterException(
                        "Expected SpecificCommandFrame ("+ expectedCommandId +") but received a DefaultResponse"
                        + "\nSTATUS:" +defaultResponse.getStatus() +" CMD:"+ defaultResponse.getCommandId()
                        ,response);
            }
            throw new ZigBeeClusterException(
                "Expected SpecificCommandFrame (" + expectedCommandId +") but Received:"
                    + commandId + " ZCLFrame was " + toString(response),
                response
            );
        }

    }

    public static void checkGeneralCommandFrame(Response response, byte expectedCommandId) throws ZigBeeClusterException{
        if (response.getZCLHeader().getFramecontrol().isClusterSpecificCommand()){
            throw new ZigBeeClusterException(
                    "Received response is not a General Command Frame !");
        }

        byte commandId = response.getHeaderCommandId();
        if (commandId != expectedCommandId) {
            if (commandId == DefaultResponse.ID){
                DefaultResponse  defaultResponse = new DefaultResponseImpl(response);
                throw new ZigBeeClusterException(
                        "Expected GeneralCommandFrame ("+ expectedCommandId +") but received a DefaultResponse"
                        + "\nSTATUS:" +defaultResponse.getStatus() +" CMD:"+ defaultResponse.getCommandId()
                        ,response);
            }
            throw new ZigBeeClusterException(
                    "Expected GeneralCommandFrame ("+ expectedCommandId +") but received:" +commandId
                    ,response);
        }

    }

}
