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
package it.cnr.isti.zigbee.zcl.library.impl.measureament_sensing;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.IlluminanceMeasurement;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;

/**
 *
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision: 42 $ ($LastChangedDate: 2010-09-23 14:21:48 +0200 (Thu, 23 Sep 2010) $)
 * @since 0.8.0
 *
 */
public class IlluminanceMeasurementCluster extends ZCLClusterBase implements IlluminanceMeasurement {

    private final AttributeImpl measuredValue;
    private final AttributeImpl minMeasuredValue;
    private final AttributeImpl maxMeasuredValue;
    private final AttributeImpl tolerance;
    private final AttributeImpl lightSensorType;

    private final Attribute[] attributes;

    public IlluminanceMeasurementCluster(ZigBeeDevice zbDevice) {

        super(zbDevice);
        measuredValue = new AttributeImpl(zbDevice,this,Attributes.MEASURED_VALUE_UNSIGNED_16_BIT);
        minMeasuredValue = new AttributeImpl(zbDevice,this,Attributes.MIN_MEASURED_VALUE_UNSIGNED_16_BIT);
        maxMeasuredValue = new AttributeImpl(zbDevice,this,Attributes.MAX_MEASURED_VALUE_UNSIGNED_16_BIT);
        tolerance = new AttributeImpl(zbDevice,this,Attributes.TOLERANCE);
        lightSensorType = new AttributeImpl(zbDevice,this,Attributes.LIGHT_SENSOR_TYPE);

        attributes = new AttributeImpl[]{measuredValue, minMeasuredValue, maxMeasuredValue, tolerance, lightSensorType};
    }

    public Attribute getMeasuredValue() {

        return measuredValue;
    }

    public Attribute getMinMeasuredValue() {

        return minMeasuredValue;
    }

    public Attribute getMaxMeasuredValue() {

        return maxMeasuredValue;
    }

    public Attribute getTolerance() {

        return tolerance;
    }

    public Attribute getLightSensorType() {

        return lightSensorType;
    }

    @Override
    public short getId() {

        return IlluminanceMeasurement.ID;
    }

    @Override
    public String getName() {

        return IlluminanceMeasurement.NAME;
    }

    @Override
    public Attribute[] getStandardAttributes() {

        return attributes;
    }
}