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
import com.itaca.ztool.util.ByteUtils;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class ZB_FIND_DEVICE_REQUEST extends ZToolPacket /*implements IREQUEST,ISIMPLEAPI*/{
    /// <name>TI.ZPI2.ZB_FIND_DEVICE_REQUEST.SearchKey</name>
        /// <summary>IEEE address of the device to find.</summary>
        public long SearchKey;

        /// <name>TI.ZPI2.ZB_FIND_DEVICE_REQUEST</name>
        /// <summary>Constructor</summary>
        public ZB_FIND_DEVICE_REQUEST()
        {
        }

        /// <name>TI.ZPI2.ZB_FIND_DEVICE_REQUEST</name>
        /// <summary>Constructor</summary>
        public ZB_FIND_DEVICE_REQUEST(long num1)
        {
            this.SearchKey = num1;
            int[] framedata=ByteUtils.convertLongtoMultiByte(this.SearchKey);
            super.buildPacket(new DoubleByte(ZToolCMD.ZB_FIND_DEVICE_REQUEST), framedata);
        }

}