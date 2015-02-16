/*
   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
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

package it.cnr.isti.zigbee.hc.driver.core;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.hc.Activator;
import it.cnr.isti.zigbee.hc.driver.HCDriverConfiguration.ProvidedClusterMode;
import it.cnr.isti.zigbee.hc.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.hc.driver.core.reflection.DeviceDescription;

import org.osgi.framework.BundleContext;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public class UnknowHCDevice extends HCDeviceBase  {

    public  UnknowHCDevice(BundleContext ctx,ZigBeeDevice zbDevice) throws ZigBeeHCException {
        super(ctx,zbDevice);
        int[] inputClusters = zbDevice.getInputClusters();
        for (int i = 0; i < inputClusters.length; i++) {
            addCluster( inputClusters[i] );
        }

        if ( Activator.getConfiguration().getClusterMode() == ProvidedClusterMode.EitherInputAndOutput ) {
            int[] outputClusters = zbDevice.getOutputClusters();
            for (int i = 0; i < inputClusters.length; i++) {
                addCluster( outputClusters[i] );
            }
        }
    }

    @Override
    public String getName() {
        return "Unknow HC Device";
    }

    final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

        public int[] getCustomClusters() {
            return new int[]{};
        }

        public int[] getMandatoryCluster() {
            return HCDevice.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return HCDevice.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return HCDevice.STANDARD;
        }

    };
	public static final String DEVICE_ID = "-1";

    @Override
    public DeviceDescription getDescription() {
        return DEVICE_DESCRIPTOR;
    }




}
