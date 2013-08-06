/*
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

package com.itaca.ztool.api.simple;

import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class ZB_SEND_DATA_CONFIRM extends ZToolPacket /*implements IRESPONSE_CALLBACK,ISIMPLEAPI*/{
    /// <name>TI.ZPI2.ZB_SEND_DATA_CONFIRM.Handle</name>
        /// <summary>Handle.</summary>
        public int Handle;
        /// <name>TI.ZPI2.ZB_SEND_DATA_CONFIRM.Status</name>
        /// <summary>The immediate return value from executing the RPC.</summary>
        public int Status;

        /// <name>TI.ZPI2.ZB_SEND_DATA_CONFIRM</name>
        /// <summary>Constructor</summary>
        public ZB_SEND_DATA_CONFIRM()
        {
        }

        /// <name>TI.ZPI2.ZB_SEND_DATA_CONFIRM</name>
        /// <summary>Constructor</summary>
        public ZB_SEND_DATA_CONFIRM(int[] framedata)
        {
            this.Handle = framedata[0];
            this.Status = framedata[1];
            super.buildPacket(new DoubleByte(ZToolCMD.ZB_SEND_DATA_CONFIRM), framedata);
        }
        
        public class AF_STATUS 
        {
            public static final int FAILED = 1;
            public static final int INVALID_PARAMETER = 3;
            public static final int MEM_FAIL = 2;
            public static final int SUCCESS = 0;
        }

}
