/*
   Copyright 2008-2010 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 


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

package it.cnr.isti.zigbee.dongle.api;


import com.itaca.ztool.api.af.AF_INCOMING_MSG;

/**
 * This class reppresent the callback invoked by the {@link SimpleDriver} whenever a message<br>
 * that belongs to the <i>Application Framework</i> arrives from the <i>ZigBee Network</i>
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi - ISTI-CNR
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface AFMessageListner {

	/**
	 * This method is invoked by the {@link SimpleDriver} on all the {@link AFMessageListner}<br>
	 * when a {@link AF_INCOMING_MSG} command arrive from the ZigBee NIC
	 * 
	 * @param msg the {@link AF_INCOMING_MSG} arrived that has to be handled
	 */
	void notify(AF_INCOMING_MSG msg);
	

}
