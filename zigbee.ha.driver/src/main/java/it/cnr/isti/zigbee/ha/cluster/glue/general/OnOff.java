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

package it.cnr.isti.zigbee.ha.cluster.glue.general;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.cluster.glue.general.event.OnOffListener;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface OnOff extends Cluster{

	public void on() throws ZigBeeHAException;
	
	public void off() throws ZigBeeHAException;

	public void toggle() throws ZigBeeHAException;
	
	public boolean getOnOff() throws ZigBeeHAException;
	
	/**
	 * 
	 * @param listener The {@link OnOffListener} to subscribe for events
	 * @since 0.2.0
	 */
	public boolean subscribe(OnOffListener listener);
	
	/**
	 * 
	 * @param listener The {@link OnOffListener} to unsubscribe
	 * @since 0.2.0
	 */
	public boolean unsubscribe(OnOffListener listener);
	
}
