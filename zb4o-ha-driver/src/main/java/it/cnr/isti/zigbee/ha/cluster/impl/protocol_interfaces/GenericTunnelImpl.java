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

package it.cnr.isti.zigbee.ha.cluster.impl.protocol_interfaces;

import it.cnr.isti.zigbee.ha.cluster.glue.protocol_interfaces.AdvertiseProtocolAddressListener;
import it.cnr.isti.zigbee.ha.cluster.glue.protocol_interfaces.GenericTunnel;
import it.cnr.isti.zigbee.ha.cluster.glue.protocol_interfaces.MatchProtocolAddressResponse;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
/**
 * PLACEHOLDER TO IMPLEMENT
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 *
 */
public class GenericTunnelImpl implements GenericTunnel {

    public int getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public Subscription[] getActiveSubscriptions() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute[] getAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    public Attribute getAttribute(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getMaximumIncomingTransferSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getMaximumOutgoingTransferSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getProtocolAddress() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setProtocolAddress(int address) {
        // TODO Auto-generated method stub

    }

    public MatchProtocolAddressResponse matchProtocolAddress(
            String protocolAddress) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean addAdvertiseProtocolAddressListener(
            AdvertiseProtocolAddressListener listener) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean removeAdvertiseProtocolAddressListener(
            AdvertiseProtocolAddressListener listener) {
        // TODO Auto-generated method stub
        return false;
    }


}
