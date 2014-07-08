/*
   Copyright 2013-2014 CNR-ISTI, http://isti.cnr.it
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
package it.cnr.isti.zigbee.ha.device.api.generic;


import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.LevelControl;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOff;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public interface LevelControllableOutput extends HADevice {

    public static final int DEVICE_ID = 0x0003;
    public static final String NAME = "Level Controllable Output";
    public static final int[] MANDATORY = ArraysUtil.append( HADevice.MANDATORY, new int[] {
            HAProfile.ON_OFF, HAProfile.LEVEL_CONTROL, HAProfile.SCENES, HAProfile.GROUPS
    } );
    public static final int[] OPTIONAL = HADevice.OPTIONAL;
    public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
    public static final int[] CUSTOM = {};

    /**
     * Access method for the <b>Mandatory</b> cluster: {@link OnOff}
     *
     * @return the {@link OnOff} cluster object
     */
    public OnOff getOnOff();

    /**
     * Access method for the <b>Mandatory</b> cluster: {@link LevelControl}
     *
     * @return the {@link LevelControl} cluster object
     */
    public LevelControl getLevelControl();

    /**
     * Access method for the <b>Mandatory</b> cluster: {@link Scenes}
     *
     * @return the {@link Scenes} cluster object
     */
    public Scenes getScenes();

    /**
     * Access method for the <b>Mandatory</b> cluster: {@link Groups}
     *
     * @return the {@link Groups} cluster object
     */
    public Groups getGroups();

}
