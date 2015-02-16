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

package com.itaca.ztool.api.af;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 */
public class AF_DATA_CONFIRM extends ZToolPacket/* implements ICONFIRMATION, IAF*/{
		
		private final static Logger profiler = LoggerFactory.getLogger("profiling."+AF_DATA_CONFIRM.class.getName());

	/// <name>TI.ZPI2.AF_DATA_CONFIRM.Endpoint</name>
        /// <summary>specifies the endpoint of the message</summary>
        public int Endpoint;
        /// <name>TI.ZPI2.AF_DATA_CONFIRM.Status</name>
        /// <summary>Status</summary>
        public int Status;
        /// <name>TI.ZPI2.AF_DATA_CONFIRM.TransID</name>
        /// <summary>Transaction ID</summary>
        public int TransID;

        /// <name>TI.ZPI2.AF_DATA_CONFIRM</name>
        /// <summary>Constructor</summary>
        public AF_DATA_CONFIRM()
        {
        }

        public AF_DATA_CONFIRM(int af_status1, int num1, int num2)
        {
            this.Status = af_status1;
            this.Endpoint = num1;
            this.TransID = num2;
            int[] framedata=new int[3];
            framedata[0]=this.Status;
            framedata[1]=this.Endpoint;
            framedata[2]=this.TransID;
            super.buildPacket(new DoubleByte(ZToolCMD.AF_DATA_CONFIRM), framedata);
        }
        
        public AF_DATA_CONFIRM(int[] framedata)
        {
        	profiler.info("AF_DATA_CONFIRM: creating object");
            this.Status=framedata[0];
            this.Endpoint=framedata[1];
            this.TransID=framedata[2];
            super.buildPacket(new DoubleByte(ZToolCMD.AF_DATA_CONFIRM), framedata);
        	profiler.info("AF_DATA_CONFIRM: object created");
        }

        /// <name>TI.ZPI2.AF_DATA_CONFIRM.AF_STATUS</name>
        /// <summary>Status code for AF responses</summary>
        public class AF_STATUS
        {
            /// <name>TI.ZPI1.AF_DATA_CONFIRM.AF_STATUS.FAILED</name>
            /// <summary>Status code for AF responses</summary>
            public static final int FAILED = 1;
            /// <name>TI.ZPI1.AF_DATA_CONFIRM.AF_STATUS.INVALID_PARAMETER</name>
            /// <summary>Status code for AF responses</summary>
            public static final int INVALID_PARAMETER = 3;
            /// <name>TI.ZPI1.AF_DATA_CONFIRM.AF_STATUS.MEM_FAIL</name>
            /// <summary>Status code for AF responses</summary>
            public static final int MEM_FAIL = 2;
            /// <name>TI.ZPI1.AF_DATA_CONFIRM.AF_STATUS.SUCCESS</name>
            /// <summary>Status code for AF responses</summary>
            public static final int SUCCESS = 0;
        }

		public byte getStatus() {
			byte value = (byte) super.packet[ZToolPacket.PAYLOAD_START_INDEX];
			return value;
		}

}
