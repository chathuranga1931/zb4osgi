/*
   Copyright 2008-2013 Andrew Rapp, http://code.google.com/p/xbee-api/

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

package com.itaca.ztool.api;

/**
 * I usually detest checked exceptions but given this is a public api, it is reasonable to
 * notify users what they can expect.
 * 
 * @author <a href="mailto:andrew.rapp@gmail.com">Andrew Rapp</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a> 
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class ZToolException extends Exception {

	private static final long serialVersionUID = -5501299728920565639L;
	
	public ZToolException(String message) {
		super(message);
	}
	
	/**
	 * 
	 * @param msg
	 * @param t
	 * @since 0.6.0
	 */
	public ZToolException(String msg, Throwable t) {
		super(msg,t);
	}
	
	public ZToolException() {
		super();
	}
	
	public ZToolException(Exception cause) {
		super(cause);
	}

}