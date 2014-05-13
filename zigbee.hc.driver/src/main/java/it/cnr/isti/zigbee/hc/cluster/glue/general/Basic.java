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

package it.cnr.isti.zigbee.hc.cluster.glue.general;

import it.cnr.isti.zigbee.hc.cluster.glue.Cluster;
import it.cnr.isti.zigbee.hc.driver.core.ZigBeeHCException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 18:00:05 +0200 (mar, 06 ago 2013) $)
 *
 */
public interface Basic extends Cluster {

	public Attribute getZCLVersion();
	public Attribute getApplicationVersion();
	public Attribute getStackVersion();
	public Attribute getHWVersion();
	public Attribute getManufacturerName();
	public Attribute getModelIdentifier();
	public Attribute getDateCode();
	public Attribute getPowerSource();
	public String getLocationDescription() throws ZigBeeHCException;
	public Attribute getPhysicalEnvironment();
	public boolean getDeviceEnabled() throws ZigBeeHCException;
	public Attribute getAlarmMask();
	/**
	 * @since 0.7.0
	 */
	public Attribute getDisableLocalConfig();

	public void resetToFactoryDefault() throws ZigBeeHCException;

}
