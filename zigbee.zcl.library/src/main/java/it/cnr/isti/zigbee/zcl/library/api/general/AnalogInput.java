/*
   Copyright 2008-2014 CNR-ISTI, http://isti.cnr.it
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
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;

/**
 * This class represent the <b>Analog Value (Basic)</b> Cluster as defined by the document:<br>
 * <i>ZigBee Cluster Library</i> public release version 075123r04ZB
 *
*
* @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
* @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
* @version $LastChangedRevision$ ($LastChangedDate$)
* @since 0.9.0
*
*/
public interface AnalogInput extends ZCLCluster {

    public static final short  ID = 0x000C;
    static final String NAME = "AnalogInput";
    static final String DESCRIPTION = "An interface for reading the value of an analog measurement and accessing various characteristics of that measurement.";


    public Attribute getAttributeDescription();
    public Attribute getAttributeMaxPresentValue();
    public Attribute getAttributeMinPresentValue();
    public Attribute getAttributeOutOfService();
    public Attribute getAttributePresentValue();
    public Attribute getAttributeReliability();
    public Attribute getAttributeResolution();
    public Attribute getAttributeStatusFlags();
    public Attribute getAttributeEngineeringUnits();
    public Attribute getAttributeApplicationType();

}
