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
package com.itaca.ztool.api.util;

import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @since 0.6.0
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class UTIL_CALLBACK_SUBSCRIBE extends ZToolPacket /*implements IREQUEST, ISYSTEM*/ {
    /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE.Action</name>
    /// <summary>contains the action of the command</summary>
    public int Action;
    /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE.CommandID</name>
    /// <summary>contains the Command ID field of the expected callback message</summary>
    public DoubleByte CommandID;

    /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE</name>
    /// <summary>Constructor</summary>
    public UTIL_CALLBACK_SUBSCRIBE() {
    }

    public UTIL_CALLBACK_SUBSCRIBE(DoubleByte num, int subs_action) {
        this.CommandID = num;
        this.Action = subs_action;
        
        int[] framedata = new int[3];
        framedata[0] = this.CommandID.getLsb();
        framedata[1] = this.CommandID.getMsb();
        framedata[2] = this.Action;
        
        super.buildPacket(new DoubleByte(ZToolCMD.UTIL_CALLBACK_SUBSCRIBE), framedata);
    }
    
    public UTIL_CALLBACK_SUBSCRIBE(int[] framedata) {

        this.CommandID=new DoubleByte(framedata[0],framedata[1]) ;
        this.Action= framedata[2] ;
        
        super.buildPacket(new DoubleByte(ZToolCMD.UTIL_CALLBACK_SUBSCRIBE), framedata);
    }

    /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE.SUBS_ACTION</name>
    /// <summary>Subscribe Action</summary>
    public class SUBS_ACTION {
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE.SUBS_ACTION.SUBSCRIBE</name>
        /// <summary>Subscribe Action</summary>
        public static final int SUBSCRIBE = 1;
        /// <name>TI.ZPI1.SYS_CALLBACK_SUBSCRIBE.SUBS_ACTION.UNSUBSCRIBE</name>
        /// <summary>Subscribe Action</summary>
        public static final int UNSUBSCRIBE = 0;
        }
}
