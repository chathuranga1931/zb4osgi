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

package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.Activator;
import it.cnr.isti.zigbee.ha.cluster.glue.general.BinaryInput;
import it.cnr.isti.zigbee.ha.cluster.glue.general.event.BinaryPresentValueListener;
import it.cnr.isti.zigbee.ha.cluster.impl.event.BinaryPresentValueBridgeListeners;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.impl.general.BinaryInputCluster;


/**
*
* @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
* @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
* @version $LastChangedRevision$ ($LastChangedDate$)
* @since 0.7.0
*
*/
public class BinaryInputImpl implements BinaryInput {

    private final Attribute presentValue;
    private final Attribute outOfService;
    private final Attribute statusFlags;
    private final BinaryInputCluster binaryInput;
    private BinaryPresentValueBridgeListeners eventBridge;

    public BinaryInputImpl(ZigBeeDevice zbDevice){
        binaryInput = new BinaryInputCluster(zbDevice);
        presentValue = binaryInput.getAttributePresentValue();
        outOfService = binaryInput.getAttributeOutOfService();
        statusFlags = binaryInput.getAttributeStatusFlags();
        eventBridge = new BinaryPresentValueBridgeListeners(Activator.getConfiguration(), presentValue, this);
    }

    public boolean subscribe(BinaryPresentValueListener listener) {
        return eventBridge.subscribe(listener);
    }

    public boolean unsubscribe(BinaryPresentValueListener listener) {
        return eventBridge.unsubscribe(listener);
    }

    public Subscription[] getActiveSubscriptions() {
        return binaryInput.getActiveSubscriptions();
    }

    public int getId() {
        return binaryInput.getId();
    }

    public String getName() {
        return binaryInput.getName();
    }

    public Attribute getAttribute(int id) {
        Attribute[] attributes = binaryInput.getAvailableAttributes();
        for (int i = 0; i < attributes.length; i++) {
            if( attributes[i].getId() == id )
                return attributes[i];
        }
        return null;
    }

    public Attribute[] getAttributes() {
        return binaryInput.getAvailableAttributes();
    }


    public String getActiveText() throws ZigBeeHAException {
        try {
            return (String) binaryInput.getAttributeActiveText().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }


    public String getDescription() throws ZigBeeHAException {
        try {
            return (String) binaryInput.getAttributeDescription().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }


    public String getInactiveText() throws ZigBeeHAException {
        try {
            return (String) binaryInput.getAttributeInactiveText().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }


    public int getPolarity() throws ZigBeeHAException {
        try {
            return (Integer) binaryInput.getAttributePolarity().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }


    public int getReliability() throws ZigBeeHAException {
        try {
            return (Integer) binaryInput.getAttributeReliability().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }


    public long getApplicationType() throws ZigBeeHAException {
        try {
            return (Long) binaryInput.getAttributeApplicationType().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }


    public boolean getOutOfService() throws ZigBeeHAException {
        try {
            return (Boolean) binaryInput.getAttributeOutOfService().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }


    public boolean getPresentValue() throws ZigBeeHAException {
        try {
            return (Boolean) binaryInput.getAttributePresentValue().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }


    public int getStatusFlags() throws ZigBeeHAException {
        try {
            return (Integer) binaryInput.getAttributeStatusFlags().getValue();
        } catch (ZigBeeClusterException e) {
            throw new ZigBeeHAException(e);
        }
    }

}
