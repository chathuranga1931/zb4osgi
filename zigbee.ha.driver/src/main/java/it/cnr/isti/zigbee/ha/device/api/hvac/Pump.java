/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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

package it.cnr.isti.zigbee.ha.device.api.hvac;

import it.cnr.isti.zigbee.ha.cluster.glue.general.Alarms;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.LevelControl;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOff;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.TemperatureMeasurement;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;

/**
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 *
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public interface Pump extends HADevice {

    public static final int DEVICE_ID = 0x0303;
    public static final String NAME = "Pump";

    public static final int[] MANDATORY = ArraysUtil.append(HADevice.MANDATORY, new int[]{
            /* TODO HAProfile.PUMP_CONFIGURATION_AND_CONTROL, */HAProfile.ON_OFF, HAProfile.SCENES, HAProfile.GROUPS
    });

    public static final int[] OPTIONAL = ArraysUtil.append(HADevice.OPTIONAL, new int[]{
            HAProfile.LEVEL_CONTROL, HAProfile.ALARMS, HAProfile.TEMPERATURE_MEASUREMENT, /* TODO HAProfile.PRESSURE_MEASUREMENT, HAProfile.FLOW_MEASUREMENT*/
    });

    public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
    public static final int[] CUSTOM = {};

    /**
     * Access method for the <b>Mandatory</b> cluster: {@link TemperatureMeasurement}
     *
     * @return the {@link TemperatureMeasurement} cluster object
     */
    public OnOff getOnOff();

    public Scenes getScenes();

    public Groups getGroups();

    public LevelControl getLevelControl();

    public Alarms getAlarms();

    public TemperatureMeasurement getTemperatureMeasurement();

    //public PUMP_CONFIGURATION_AND_CONTROL getPUMP_CONFIGURATION_AND_CONTROL();

    //public PRESSURE_MEASUREMENT getPRESSURE_MEASUREMENT();

    //public FLOW_MEASUREMENT getFLOW_MEASUREMENT();
}