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
package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.BinaryInput;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.device.api.generic.SimpleSensor;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;

import org.osgi.framework.BundleContext;


/**
*
* @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
* @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
* @version $LastChangedRevision$ ($LastChangedDate$)
* @since 0.7.0
*
*/
public class SimpleSensorDevice extends HADeviceBase implements SimpleSensor {

    private BinaryInput binaryInput;
    private Scenes scenes;
    private Groups groups;

    public SimpleSensorDevice(BundleContext ctx,ZigBeeDevice zbDevice) throws ZigBeeHAException{
        super(ctx,zbDevice);
        binaryInput = (BinaryInput) addCluster(HAProfile.BINARY_INPUT);
        scenes = (Scenes) addCluster(HAProfile.SCENES);
        groups = (Groups) addCluster(HAProfile.GROUPS);
    }

    final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

        public int[] getCustomClusters() {
            return SimpleSensor.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return SimpleSensor.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return SimpleSensor.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return SimpleSensor.STANDARD;
        }

    };

    @Override
    public DeviceDescription getDescription() {
        return DEVICE_DESCRIPTOR;
    }

    @Override
    public String getName() {
        return SimpleSensor.NAME;
    }

    public BinaryInput getBinaryInput() {
        return binaryInput;
    }

    public Groups getGroups() {
        return groups;
    }

    public Scenes getScenes() {
        return scenes;
    }
}
