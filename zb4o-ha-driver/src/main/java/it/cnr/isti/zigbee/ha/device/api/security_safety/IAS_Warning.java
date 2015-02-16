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
package it.cnr.isti.zigbee.ha.device.api.security_safety;

import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASWD;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASZone;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;

/**
 *
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 *
 */
public interface IAS_Warning {

    public static final int DEVICE_ID = 0x0403;
    public static final String NAME = "IAS Warning Device";
    public static final int[] MANDATORY = ArraysUtil.append(HADevice.MANDATORY, new int[]{
            HAProfile.IAS_WD, HAProfile.IAS_ZONE
    });
    public static final int[] OPTIONAL = ArraysUtil.append(HADevice.OPTIONAL, new int[]{
            HAProfile.SCENES, HAProfile.GROUPS
    });
    public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
    public static final int[] CUSTOM = {};

    public IASWD getIASWD();
    public IASZone getIASZone();
}