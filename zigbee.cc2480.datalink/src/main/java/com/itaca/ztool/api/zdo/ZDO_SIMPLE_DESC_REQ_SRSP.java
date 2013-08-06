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

package com.itaca.ztool.api.zdo;

import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class ZDO_SIMPLE_DESC_REQ_SRSP extends ZToolPacket /*implements IRESPONSE,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_REQ_SRSP.Status</name>
    /// <summary>Status</summary>
    public int Status;

    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_REQ_SRSP</name>
    /// <summary>Constructor</summary>
    public ZDO_SIMPLE_DESC_REQ_SRSP() {
    }

    public ZDO_SIMPLE_DESC_REQ_SRSP(int[] framedata) {
        this.Status = framedata[0];
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_SIMPLE_DESC_REQ_SRSP), framedata);
    }

    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_REQ_SRSP.AF_STATUS</name>
    /// <summary>Status code for AF responses</summary>
    public class AF_STATUS {

        public static final int FAILED = 1;
        public static final int INVALID_PARAMETER = 2;
        public static final int MEM_FAIL = 0x10;
        public static final int SUCCESS = 0;
        }
}
