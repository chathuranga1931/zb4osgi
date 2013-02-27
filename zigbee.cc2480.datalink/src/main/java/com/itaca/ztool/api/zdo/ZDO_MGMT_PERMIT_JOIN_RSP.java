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
import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class ZDO_MGMT_PERMIT_JOIN_RSP extends ZToolPacket /*implements IRESPONSE_CALLBACK,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_MGMT_PERMIT_JOIN_RSP.SrcAddress</name>
    /// <summary>Source address of the message</summary>
    public ZToolAddress16 SrcAddress;
    /// <name>TI.ZPI1.ZDO_MGMT_PERMIT_JOIN_RSP.Status</name>
    /// <summary>this field indicates either SUCCESS (0) or FAILURE (1).</summary>
    public int Status;

    /// <name>TI.ZPI1.ZDO_MGMT_PERMIT_JOIN_RSP</name>
    /// <summary>Constructor</summary>
    public ZDO_MGMT_PERMIT_JOIN_RSP() {
    }

    public ZDO_MGMT_PERMIT_JOIN_RSP(int[] framedata) {
        this.SrcAddress=new ZToolAddress16(framedata[1],framedata[0]);
        this.Status = framedata[2];
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_MGMT_PERMIT_JOIN_RSP), framedata);
    }

   /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS</name>
        /// <summary>ZDO status</summary>
        public class ZDO_STATUS 
        {
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_DEVICE_NOT_FOUND</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_DEVICE_NOT_FOUND = 0x81;
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_INVALID_EP</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_INVALID_EP = 130;
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_INVALID_REQTYPE</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_INVALID_REQTYPE = 0x80;
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_NO_ENTRY</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_NO_ENTRY = 0x88;
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_NO_MATCH</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_NO_MATCH = 0x86;
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_NOT_ACTIVE</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_NOT_ACTIVE = 0x83;
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_NOT_SUPPORTED</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_NOT_SUPPORTED = 0x84;
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_SUCCESS</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_SUCCESS = 0;
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_TABLE_FULL</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_TABLE_FULL = 0x87;
            /// <name>TI.ZPI2.ZDO_MGMT_PERMIT_JOIN_RSP.ZDO_STATUS.ZDP_TIMEOUT</name>
            /// <summary>ZDO status</summary>
            public static final int ZDP_TIMEOUT = 0x85;
        }
}
