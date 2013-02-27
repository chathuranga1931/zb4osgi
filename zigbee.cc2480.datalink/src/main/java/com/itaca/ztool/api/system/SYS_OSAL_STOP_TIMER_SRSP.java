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

package com.itaca.ztool.api.system;

import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class SYS_OSAL_STOP_TIMER_SRSP extends ZToolPacket /*implements IRESPONSE,ISYSTEM*/{
    /// <name>TI.ZPI2.SYS_OSAL_STOP_TIMER_SRSP.Status</name>
        /// <summary>Status</summary>
        public int Status;

        /// <name>TI.ZPI2.SYS_OSAL_STOP_TIMER_SRSP</name>
        /// <summary>Constructor</summary>
        public SYS_OSAL_STOP_TIMER_SRSP()
        {
        }

        public SYS_OSAL_STOP_TIMER_SRSP(int[] framedata)
        {
            this.Status = framedata[0];
            super.buildPacket(new DoubleByte(ZToolCMD.SYS_OSAL_STOP_TIMER_SRSP), framedata);
        }

        /// <name>TI.ZPI2.SYS_OSAL_STOP_TIMER_SRSP.CMD_STATUS</name>
        /// <summary>Status of command</summary>
        public class CMD_STATUS
        {
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
            public static final int ZMAC_ACK_PENDING = 0x1b;
            public static final int ZMAC_BAD_STATE = 0x19;
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
            public static final int ZMAC_NO_RESOURCE = 0x1a;
            public static final int ZMAC_NO_SHORT_ADDRESS = 0xec;
            public static final int ZMAC_NO_TIME = 0x1c;
            public static final int ZMAC_ON_TIME_TOO_LONG = 0xf6;
            public static final int ZMAC_OUT_OF_CAP = 0xed;
            public static final int ZMAC_PANID_CONFLICT = 0xee;
            public static final int ZMAC_PAST_TIME = 0xf7;
            public static final int ZMAC_REALIGNMENT = 0xef;
            public static final int ZMAC_SCAN_IN_PROGRESS = 0xfc;
            public static final int ZMAC_TRACKING_OFF = 0xf8;
            public static final int ZMAC_TRANSACTION_EXPIRED = 240;
            public static final int ZMAC_TRANSACTION_OVERFLOW = 0xf1;
            public static final int ZMAC_TX_ABORTED = 0x1d;
            public static final int ZMAC_TX_ACTIVE = 0xf2;
            public static final int ZMAC_UNAVAILABLE_KEY = 0xf3;
            public static final int ZMAC_UNSUPPORTED_2 = 0x18;
            public static final int ZMAC_UNSUPPORTED = 0xf5;
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
