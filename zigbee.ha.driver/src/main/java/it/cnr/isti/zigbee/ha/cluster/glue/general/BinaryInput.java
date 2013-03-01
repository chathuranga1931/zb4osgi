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
import it.cnr.isti.zigbee.ha.cluster.glue.general.event.PresentValueListener;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;


/**
*
* @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
* @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
* @version $LastChangedRevision$ ($LastChangedDate$)
* @since 0.7.0
*
*/
public interface BinaryInput extends Cluster {

    public String getActiveText() throws ZigBeeHAException;
    public String getDescription() throws ZigBeeHAException;
    public String getInactiveText() throws ZigBeeHAException;
    public boolean getOutOfService() throws ZigBeeHAException;
    public boolean getPresentValue() throws ZigBeeHAException;
    public int getPolarity() throws ZigBeeHAException;
    public int getReliability() throws ZigBeeHAException;
    public int getStatusFlags() throws ZigBeeHAException;
    public long getApplicationType() throws ZigBeeHAException;

    /**
     *
     * @param listener The {@link PresentValueListener} to subscribe for events
     */
    public boolean subscribe(PresentValueListener listener);

    /**
     *
     * @param listener The {@link PresentValueListener} to unsubscribe
     */
    public boolean unsubscribe(PresentValueListener listener);

}
