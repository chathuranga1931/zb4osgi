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

package it.cnr.isti.zigbee.zcl.library.impl.core;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.AnalogSubscription;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeType;
import it.cnr.isti.zigbee.zcl.library.api.global.AttributeReportingConfigurationRecord;
import it.cnr.isti.zigbee.zcl.library.api.global.AttributeStatusRecord;
import it.cnr.isti.zigbee.zcl.library.impl.ClusterImpl;
import it.cnr.isti.zigbee.zcl.library.impl.global.reporting.AttributeReportingConfigurationRecordImpl;
import it.cnr.isti.zigbee.zcl.library.impl.global.reporting.ConfigureReportingCommand;
import it.cnr.isti.zigbee.zcl.library.impl.global.reporting.ConfigureReportingResponseImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.6.0
 *
 */
public class AnalogSubscriptionImpl extends SubscriptionBase implements AnalogSubscription {

    private final Logger log = LoggerFactory.getLogger(AnalogSubscriptionImpl.class);

    private Object minimumChange = null;

    public AnalogSubscriptionImpl(final ZigBeeDevice zb, final ZCLCluster c, final Attribute attrib) {
        super(zb, c, attrib);
        final ZigBeeType type = attrib.getZigBeeType();
        if ( type.isAnalog() == false ) {
            throw new IllegalArgumentException(
                    "AnalogSubscription applies only to Attribute with analog data type, " +
                    "the attribute " + attrib.getName() + " ("+attrib.getId()+") of type "+type.toString() +
                    " is DISCRETE"
            );
        }
        setReportableChangeValue(new Double(AnalogSubscription.DEFAULT_REPORTABLE_CHANGE_INTERVAL));
    }

    protected boolean doConfigureServer() throws ZigBeeClusterException {

        log.debug(
                "Subscring to analog attribute {} ( {} )with the following parameter min = {}, max = {}, change = {}",
                new Object[]{attribute.getName(), attribute.getId(), min, max, minimumChange}
        );

        AttributeReportingConfigurationRecordImpl config = new AttributeReportingConfigurationRecordImpl(
                attribute, 0x00, max, min, minimumChange, max
        );
        ConfigureReportingCommand cmd = new ConfigureReportingCommand(
                new AttributeReportingConfigurationRecord[]{config}
        );

        final ZCLFrame frame = new ZCLFrame(cmd, true);
        final ClusterImpl input = new ClusterImpl(cluster.getId(),frame);
        Cluster cluster = null;
        try {
            cluster = device.invoke(input);
            final ConfigureReportingResponseImpl response = new ConfigureReportingResponseImpl(
                    new ResponseImpl(cluster,cluster.getId()), new Attribute[]{attribute}
            );
            final AttributeStatusRecord[] results = response.getAttributeStatusRecord();
            if ( results[0].getStatus() != 0 ) {
                throw new ZigBeeClusterException("ConfigureReporting answered with a Failed status: {} "+results[0].getStatus());
            }
        } catch (ZigBeeBasedriverException e) {
            throw new ZigBeeClusterException(e);
        }

        return true;
    }

    public Object getReportableChange() {
        return minimumChange;
    }

    private void setReportableChangeValue(Number n) {
        final ZigBeeType type = attribute.getZigBeeType();
        if( type.getJavaClass() == Long.class ) {
            minimumChange = new Long(n.longValue());
        }else if( type.getJavaClass() == Integer.class ){
            minimumChange = new Integer(n.intValue());
        }else if( type.getJavaClass() == Float.class ){
            minimumChange = new Float(n.floatValue());
        }else if( type.getJavaClass() == Double.class ){
            minimumChange = new Double(n.doubleValue());
        } else {
            throw new IllegalArgumentException(
                    "Java class used for the interpretation of the " +
                    "the attribute " + attribute.getName() + " ("+attribute.getId()+") of type "+type.toString() +
                    " is not recognized "
            );
        }
    }

    public void setReportableChange(Object value) {
        setReportableChangeValue((Number) value);
    }

}
