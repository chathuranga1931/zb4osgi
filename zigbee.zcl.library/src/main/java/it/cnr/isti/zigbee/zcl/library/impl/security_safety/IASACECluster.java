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
package it.cnr.isti.zigbee.zcl.library.impl.security_safety;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASACE;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.BypassPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.ZoneTable;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.ArmCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.ArmResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.BypassCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.EmergencyCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.FireCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.PanicCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.ZoneIDMapCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.ZoneIDMapResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.ZoneInformationCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.ZoneInformationResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace.ZoneTableImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 *
 */
public class IASACECluster extends ZCLClusterBase implements IASACE {

    private ZoneTable zoneTable;
    private final Attribute[] attributes;

    private final Logger log = LoggerFactory.getLogger(IASACECluster.class);

    public IASACECluster(ZigBeeDevice zbDevice) {

        super(zbDevice);
        zoneTable = new ZoneTableImpl();
        attributes = new AttributeImpl[]{};
    }

    public Response arm(byte armMode) throws ZigBeeClusterException {
        ArmCommand cmd = new ArmCommand(armMode);
        Response response = invoke(cmd, false);
        return new ArmResponseImpl(response);
    }

    public void bypass(BypassPayload payload) throws ZigBeeClusterException {
        BypassCommand cmd = new BypassCommand(payload);
        invoke(cmd);
    }

    public void emergency() throws ZigBeeClusterException {
        EmergencyCommand cmd = new EmergencyCommand();
        invoke(cmd);
    }

    public void fire() throws ZigBeeClusterException {
        FireCommand cmd = new FireCommand();
        invoke(cmd);
    }

    public void panic() throws ZigBeeClusterException {
        PanicCommand cmd = new PanicCommand();
        invoke(cmd);
    }

    public Response getZoneIdMap() throws ZigBeeClusterException {
        ZoneIDMapCommand cmd = new ZoneIDMapCommand();
        Response response = invoke(cmd);
        return new ZoneIDMapResponseImpl(response);
    }

    public Response getZoneInformation(int zoneID) throws ZigBeeClusterException {
        ZoneInformationCommand cmd = new ZoneInformationCommand(zoneID);
        Response response = invoke(cmd);
        return new ZoneInformationResponseImpl(response);
    }

    @Override
    public short getId() {
        return IASACE.ID;
    }

    @Override
    public String getName() {
        return IASACE.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {
        return attributes;
    }
}