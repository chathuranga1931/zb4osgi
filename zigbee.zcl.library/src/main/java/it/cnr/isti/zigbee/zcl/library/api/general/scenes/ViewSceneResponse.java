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

package it.cnr.isti.zigbee.zcl.library.api.general.scenes;

import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;

/**
 * 
 * This class represent the <i>View Scene Response</i> as defined by the document:<br>
 * <i>ZigBee Cluster Library</i> public release version 075123r01ZB
 *   
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface ViewSceneResponse extends Response{	
	
	public static final short ID = 0x01;
	
	/**
	 * 
	 * @return {@link Status} representing the <i>Status</i> field 
	 */
	public Status getStatus();
	
	/**
	 * 
	 * @return the int representing the <i>Group ID</i> field
	 */
	public int getGroupId();

	
	/**
	 * 
	 * @return the short representing the <i>Scene ID</i> field
	 */
	public short getSceneId();
	
	/**
	 * 
	 * @return the int representing the <i>Transition time</i> field
	 */
	public int getTransitionTime();
	
	/**
	 * 
	 * @return the {@link String} representing the <i>Scene Name</i> field
	 */
	public String getSceneName();
	
	/**
	 * 
	 * @return the {@link ExtensionFieldSet} representing the <i>Extension</i> field
	 */
	public ExtensionFieldSetViewResponse[] getExstensionFieldSet();
	
}
