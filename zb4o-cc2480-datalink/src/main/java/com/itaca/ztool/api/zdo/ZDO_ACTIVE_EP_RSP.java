/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 

   Copyright 2008-2013 ITACA-TSB, http://www.tsb.upv.es/
   Instituto Tecnologico de Aplicaciones de Comunicacion 
   Avanzadas - Grupo Tecnologias para la Salud y el 
   Bienestar (TSB)


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

package com.itaca.ztool.api.zdo;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 */
public class ZDO_ACTIVE_EP_RSP extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_ACTIVE_EP_RSP.ActiveEndpointCount</name>
    /// <summary>Number of active endpoint in the list</summary>
    public int ActiveEndpointCount;
    /// <name>TI.ZPI1.ZDO_ACTIVE_EP_RSP.ActiveEndpointList</name>
    /// <summary>Array of active endpoints on this device</summary>
    public int[] ActiveEndpointList;
    /// <name>TI.ZPI1.ZDO_ACTIVE_EP_RSP.NWKAddrOfInterest</name>
    /// <summary>Device's short address that this response describes.</summary>
    public ZToolAddress16 nwkAddr;
    /// <name>TI.ZPI1.ZDO_ACTIVE_EP_RSP.SrcAddress</name>
    /// <summary>the message's source network address</summary>
    public ZToolAddress16 SrcAddress;
    /// <name>TI.ZPI1.ZDO_ACTIVE_EP_RSP.Status</name>
    /// <summary>this field indicates either SUCCESS or FAILURE</summary>
    public int Status;
	private byte[] list;

    /// <name>TI.ZPI1.ZDO_ACTIVE_EP_RSP</name>
    /// <summary>Constructor</summary>
    public ZDO_ACTIVE_EP_RSP() {
        this.ActiveEndpointList = new int[0xff];
    }

    public ZDO_ACTIVE_EP_RSP(int[] framedata) {
        this.SrcAddress=new ZToolAddress16(framedata[1],framedata[0]);
        this.Status = framedata[2];
        this.nwkAddr=new ZToolAddress16(framedata[4],framedata[3]);
        
        this.ActiveEndpointCount = framedata[5];
        this.ActiveEndpointList=new int[this.ActiveEndpointCount];
        for (int i = 0; i < this.ActiveEndpointList.length; i++) {
            this.ActiveEndpointList[i] = framedata[i+6];
        }
        
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_ACTIVE_EP_RSP), framedata);
    }

    /// <name>TI.ZPI1.ZDO_ACTIVE_EP_RSP.CMD_STATUS</name>
    /// <summary>Status of command</summary>
    public class CMD_STATUS {

        public static final int FAIL = 1;
        public static final int SUCCESS = 0;
        public static final int ZAF_STATUS_FAILED = 0x80;
        public static final int ZAF_STATUS_INVALID_PARAMETER = 130;
        public static final int ZAF_STATUS_MEM_FAIL = 0x81;
        public static final int ZAPS_FAIL = 0xb1;
        public static final int ZAPS_ILLEGAL_REQUEST = 0xb3;
        public static final int ZAPS_INVALID_BINDING = 180;
        public static final int ZAPS_NO_ACK = 0xb7;
        public static final int ZAPS_NOT_SUPPORTED = 0xb6;
        public static final int ZAPS_TABLE_FULL = 0xb2;
        public static final int ZAPS_UNSUPPORTED_ATTRIB = 0xb5;
        public static final int ZBUFFER_FULL = 0x11;
        public static final int ZMAC_BEACON_LOSS = 0xe0;
        public static final int ZMAC_CHANNEL_ACCESS_FAILURE = 0xe1;
        public static final int ZMAC_DENIED = 0xe2;
        public static final int ZMAC_DISABLE_TRX_FAILURE = 0xe3;
        public static final int ZMAC_FAILED_SECURITY_CHECK = 0xe4;
        public static final int ZMAC_FRAME_TOO_LONG = 0xe5;
        public static final int ZMAC_INVALID_GTS = 230;
        public static final int ZMAC_INVALID_HANDLE = 0xe7;
        public static final int ZMAC_INVALID_PARAMETER = 0xe8;
        public static final int ZMAC_MEM_ERROR = 0x13;
        public static final int ZMAC_NO_ACK = 0xe9;
        public static final int ZMAC_NO_BEACON = 0xea;
        public static final int ZMAC_NO_DATA = 0xeb;
        public static final int ZMAC_NO_SHORT_ADDRESS = 0xec;
        public static final int ZMAC_OUT_OF_CAP = 0xed;
        public static final int ZMAC_PANID_CONFLICT = 0xee;
        public static final int ZMAC_REALIGNMENT = 0xef;
        public static final int ZMAC_TRANSACTION_EXPIRED = 240;
        public static final int ZMAC_TRANSACTION_OVERFLOW = 0xf1;
        public static final int ZMAC_TX_ACTIVE = 0xf2;
        public static final int ZMAC_UNAVAILABLE_KEY = 0xf3;
        public static final int ZMAC_UNSUPPORTED_ATTRIBUTE = 0xf4;
        public static final int ZMEM_ERROR = 0x10;
        public static final int ZNWK_ALREADY_PRESENT = 0xc5;
        public static final int ZNWK_INVALID_PARAM = 0xc1;
        public static final int ZNWK_INVALID_REQUEST = 0xc2;
        public static final int ZNWK_LEAVE_UNCONFIRMED = 0xcb;
        public static final int ZNWK_NO_ACK = 0xcc;
        public static final int ZNWK_NO_NETWORKS = 0xca;
        public static final int ZNWK_NO_ROUTE = 0xcd;
        public static final int ZNWK_NOT_PERMITTED = 0xc3;
        public static final int ZNWK_STARTUP_FAILURE = 0xc4;
        public static final int ZNWK_SYNC_FAILURE = 0xc6;
        public static final int ZNWK_TABLE_FULL = 0xc7;
        public static final int ZNWK_UNKNOWN_DEVICE = 200;
        public static final int ZNWK_UNSUPPORTED_ATTRIBUTE = 0xc9;
        public static final int ZSEC_CCM_FAIL = 0xa4;
        public static final int ZSEC_MAX_FRM_COUNT = 0xa3;
        public static final int ZSEC_NO_KEY = 0xa1;
        public static final int ZSEC_OLD_FRM_COUNT = 0xa2;
        public static final int ZUNSUPPORTED_MODE = 0x12;
        }
    
	public byte[] getActiveEndPointList() {
		if( list == null ) {
			list = new byte[super.packet[ZToolPacket.PAYLOAD_START_INDEX + 5]];
			for (int i = 0; i < list.length; i++) {
				list[i] = (byte) super.packet[ZToolPacket.PAYLOAD_START_INDEX + 6 + i];
			}
		}
		return list;
	}
}
