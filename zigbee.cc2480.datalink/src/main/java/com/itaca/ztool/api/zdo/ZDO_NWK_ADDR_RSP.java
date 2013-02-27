/*
   Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es/
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
import com.itaca.ztool.api.ZToolAddress64;
import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class ZDO_NWK_ADDR_RSP extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.AssocDevList</name>
    /// <summary>Dynamic array, array of 16 bit short addresses - list of network address for associated devices.  This list can be a partial list if the entire list doesn't fit into a packet.  If it is a partial list, the starting index is StartIndex.</summary>
    public ZToolAddress16[] AssocDevList;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.IEEEAddr</name>
    /// <summary>64 bit IEEE address of source device</summary>
    public ZToolAddress64 IEEEAddr;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.NumAssocDev</name>
    /// <summary>number of associated devices</summary>
    public int NumAssocDev;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.nwkAddr</name>
    /// <summary>short network address of responding device</summary>
    public ZToolAddress16 nwkAddr;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.SrcAddress</name>
    /// <summary>Source address, size is dependent on SrcAddrMode</summary>
    public ZToolAddress64 SrcAddress;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.StartIndex</name>
    /// <summary>Starting index into the list of associated devices for this report.</summary>
    public int StartIndex;
    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.Status</name>
    /// <summary>this field indicates either SUCCESS or FAILURE</summary>
    public int Status;

    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP</name>
    /// <summary>Constructor</summary>
    public ZDO_NWK_ADDR_RSP() {
        this.AssocDevList = new ZToolAddress16[0xff];
    }

    public ZDO_NWK_ADDR_RSP(int[] framedata) {
        ///WARNING: variable length.
        ///resulting SrcAddress is 8 bytes but serialized is either 2 or 8 depending on Mode
        this.Status = framedata[0];
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) framedata[8-i];
        }
        this.IEEEAddr = new ZToolAddress64(bytes);
        this.nwkAddr=new ZToolAddress16(framedata[10],framedata[9]);
        this.StartIndex = framedata[11];
        this.NumAssocDev = framedata[12];
        this.AssocDevList=new ZToolAddress16[this.NumAssocDev];
        for (int i = 0; i < this.AssocDevList.length; i++) {
            this.AssocDevList[i]=new ZToolAddress16(framedata[14+(i*2)], framedata[13+(i*2)]);
        }
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_NWK_ADDR_RSP), framedata);
    }

    /// <name>TI.ZPI1.ZDO_NWK_ADDR_RSP.CMD_STATUS</name>
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
}
