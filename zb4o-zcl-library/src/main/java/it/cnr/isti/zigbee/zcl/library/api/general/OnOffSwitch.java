/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
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

package it.cnr.isti.zigbee.zcl.library.api.general;

import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;

/**
 * This class represent the <b>On/off</b> Cluster as defined by the document:<br>
 * <i>ZigBee Cluster Library</i> public release version 075123r01ZB
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface OnOffSwitch extends ZCLCluster {
	
	public Attribute getAttributeOnOff();
	
	public Response on(boolean disableDefatultResponse);
	public Response off(boolean disableDefatultResponse);
	public Response toggle(boolean disableDefatultResponse);

	
	/**
	 * Alias for the method {@link #on(true)}
	 * @see #on(boolean)
	 */
	public void on();
	
	/**
	 * Alias for the method {@link #off(true)}
	 * @see #off(boolean)
	 */
	public void off();
	
	/**
	 * Alias for the method {@link #toggle(true)}
	 * @see #toggle(boolean)
	 */
	public void toggle();
}
