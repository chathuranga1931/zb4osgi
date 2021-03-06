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

package it.cnr.isti.zigbee.hc.driver.core.reflection;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 18:00:05 +0200 (mar, 06 ago 2013) $)
 *
 */
public abstract class AbstractDeviceDescription implements DeviceDescription{

	public abstract int[] getCustomClusters();
	public abstract int[] getMandatoryCluster();
	public abstract int[] getOptionalCluster() ;
	public abstract int[] getStandardClusters() ;

	public boolean isCustom(int clusterId) {
		int[] array = getCustomClusters();
		for (int i = 0; i < array.length; i++) {
			if (array[i]==clusterId) return true;
		}
		return false;
	}

	public boolean isMandatory(int clusterId) {
		int[] array = getMandatoryCluster();
		for (int i = 0; i < array.length; i++) {
			if (array[i]==clusterId) return true;
		}
		return false;
	}

	public boolean isOptional(int clusterId) {
		int[] array = getOptionalCluster();
		for (int i = 0; i < array.length; i++) {
			if (array[i]==clusterId) return true;
		}
		return false;
	}

	public boolean isStandard(int clusterId) {
		int[] array = getStandardClusters();
		for (int i = 0; i < array.length; i++) {
			if (array[i]==clusterId) return true;
		}
		return false;
	}

}
