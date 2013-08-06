/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
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
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.TemperatureMeasurement;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.event.MeasuredValueListener;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.event.ToleranceListener;
import it.cnr.isti.zigbee.ha.cluster.impl.event.MeasuredValueBridgeListeners;
import it.cnr.isti.zigbee.ha.cluster.impl.event.ToleranceBridgeListeners;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.impl.measureament_sensing.TemperatureMeasurementCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class TemperatureMeasurementImpl implements TemperatureMeasurement {
	
	private final TemperatureMeasurementCluster temperatureMeasurementCluster; 
	private final Attribute measuredValue;
	private final Attribute minMeasuredValue;
	private final Attribute maxMeasuredValue;
	private final Attribute tolerance;
	
	private final MeasuredValueBridgeListeners measureBridge;	
	private final ToleranceBridgeListeners toleranceBridge;
	
	public TemperatureMeasurementImpl(ZigBeeDevice zbDevice){
		temperatureMeasurementCluster = new TemperatureMeasurementCluster(zbDevice);
		measuredValue = temperatureMeasurementCluster.getAttributeMeasuredValue();
		minMeasuredValue = temperatureMeasurementCluster.getAttributeMinMeasuredValue();
		maxMeasuredValue = temperatureMeasurementCluster.getAttributeMaxMeasuredValue();
		tolerance = temperatureMeasurementCluster.getAttributeTolerance();
		
		toleranceBridge = new ToleranceBridgeListeners(Activator.getConfiguration(), tolerance, this);
		measureBridge = new MeasuredValueBridgeListeners(Activator.getConfiguration(), measuredValue, this);
	}

	public Attribute getMaxMeasuredValue() {
		return maxMeasuredValue;
	}

	public Attribute getMeasuredValue() {
		return measuredValue;
	}

	public Attribute getMinMeasuredValue() {
		return minMeasuredValue;
	}

	public Attribute getTolerance() {
		return tolerance;
	}

	public Subscription[] getActiveSubscriptions() {
		return temperatureMeasurementCluster.getActiveSubscriptions();
	}

	public int getId() {
		return temperatureMeasurementCluster.getId();
	}

	public String getName() {
		return temperatureMeasurementCluster.getName();
	}

	public Attribute getAttribute(int id) {		
		Attribute[] attributes = temperatureMeasurementCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public Attribute[] getAttributes() {
		return temperatureMeasurementCluster.getAvailableAttributes();
	}

	public boolean subscribe(MeasuredValueListener listener) {
		return measureBridge.subscribe(listener);
	}

	public boolean subscribe(ToleranceListener listener) {
		return toleranceBridge.subscribe(listener);
	}

	public boolean unsubscribe(MeasuredValueListener listener) {
		return measureBridge.unsubscribe(listener);
	}

	public boolean unsubscribe(ToleranceListener listener) {
		return toleranceBridge.unsubscribe(listener);
	}
	
}
