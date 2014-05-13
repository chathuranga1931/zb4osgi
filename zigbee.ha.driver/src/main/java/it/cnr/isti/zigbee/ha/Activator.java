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

package it.cnr.isti.zigbee.ha;

import it.cnr.isti.zigbee.ha.cluster.factory.HAClustersFactory;
import it.cnr.isti.zigbee.ha.device.api.generic.LevelControlSwitch;
import it.cnr.isti.zigbee.ha.device.api.generic.MainsPowerOutlet;
import it.cnr.isti.zigbee.ha.device.api.generic.OnOffOutput;
import it.cnr.isti.zigbee.ha.device.api.generic.OnOffSwitch;
import it.cnr.isti.zigbee.ha.device.api.generic.SimpleSensor;
import it.cnr.isti.zigbee.ha.device.api.hvac.Pump;
import it.cnr.isti.zigbee.ha.device.api.hvac.TemperatureSensor;
import it.cnr.isti.zigbee.ha.device.api.lighting.ColorDimmableLight;
import it.cnr.isti.zigbee.ha.device.api.lighting.DimmableLight;
import it.cnr.isti.zigbee.ha.device.api.lighting.LightSensor;
import it.cnr.isti.zigbee.ha.device.api.lighting.OccupancySensor;
import it.cnr.isti.zigbee.ha.device.api.lighting.OnOffLight;
import it.cnr.isti.zigbee.ha.device.api.lighting.OnOffLightSwitch;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IASAncillaryControlEquipment;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IASControlAndIndicatingEquipment;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IAS_Warning;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IAS_Zone;
import it.cnr.isti.zigbee.ha.device.impl.ColorDimmableLightDevice;
import it.cnr.isti.zigbee.ha.device.impl.DimmableLightDevice;
import it.cnr.isti.zigbee.ha.device.impl.IASAncillaryControlEquipmentDevice;
import it.cnr.isti.zigbee.ha.device.impl.IASControlAndIndicatingEquipmentDevice;
import it.cnr.isti.zigbee.ha.device.impl.IAS_Warning_Device;
import it.cnr.isti.zigbee.ha.device.impl.IAS_ZoneDevice;
import it.cnr.isti.zigbee.ha.device.impl.LevelControlSwitchDevice;
import it.cnr.isti.zigbee.ha.device.impl.LightSensorDevice;
import it.cnr.isti.zigbee.ha.device.impl.MainsPowerOutletDevice;
import it.cnr.isti.zigbee.ha.device.impl.OccupancySensorDevice;
import it.cnr.isti.zigbee.ha.device.impl.OnOffLightDevice;
import it.cnr.isti.zigbee.ha.device.impl.OnOffLightSwitchDevice;
import it.cnr.isti.zigbee.ha.device.impl.OnOffOutputDevice;
import it.cnr.isti.zigbee.ha.device.impl.OnOffSwitchDevice;
import it.cnr.isti.zigbee.ha.device.impl.PumpDevice;
import it.cnr.isti.zigbee.ha.device.impl.SimpleSensorDevice;
import it.cnr.isti.zigbee.ha.device.impl.TemperatureSensorDevice;
import it.cnr.isti.zigbee.ha.driver.HADriverConfiguration;
import it.cnr.isti.zigbee.ha.driver.HAImporter;
import it.cnr.isti.zigbee.ha.driver.core.GenericHADeviceFactory;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ReportingConfiguration;
import it.cnr.isti.zigbee.ha.driver.core.UnknownHADeviceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class Activator implements BundleActivator {

    private final static Logger logger = LoggerFactory.getLogger(Activator.class);

    private static final String HA_CONFIG_PID = HADriverConfiguration.PID;

    private HAImporter haImporter;

    private final ArrayList<HADeviceFactoryBase> factories = new ArrayList<HADeviceFactoryBase>();

    private ServiceRegistration configRegistration;

    private static HADriverConfiguration configuration = null;


    private void doRegisterConfigurationService(BundleContext ctx){
        Properties properties = new Properties();

        properties.setProperty(Constants.SERVICE_PID, HA_CONFIG_PID);

        configuration = new HADriverConfiguration(ctx);
        configRegistration = ctx.registerService(
                new String[]{ManagedService.class.getName(), ReportingConfiguration.class.getName()},
                configuration,
                null
                );
    }

    public void start(BundleContext ctx) throws Exception {
        doRegisterConfigurationService(ctx);
        try {
            new HAClustersFactory(ctx).register();
        } catch( Exception ex ) {
            logger.debug( "Failed to register HAClustersFactory ", ex );
        }
        doRegisterDeviceFactories(ctx);
        haImporter = new HAImporter(ctx);
    }

    private void doRegisterDeviceFactories(final BundleContext bc) throws Exception {
        Map< Class<?>, Class<?> > refinedAvailables = new HashMap< Class<?>, Class<?> >();
        refinedAvailables.put( ColorDimmableLight.class, ColorDimmableLightDevice.class );
        refinedAvailables.put( DimmableLight.class, DimmableLightDevice.class );
        refinedAvailables.put( IAS_Zone.class, IAS_ZoneDevice.class );
        refinedAvailables.put( IASAncillaryControlEquipment.class, IASAncillaryControlEquipmentDevice.class );
        refinedAvailables.put( IASControlAndIndicatingEquipment.class, IASControlAndIndicatingEquipmentDevice.class );
        refinedAvailables.put( LevelControlSwitch.class, LevelControlSwitchDevice.class );
        refinedAvailables.put( LightSensor.class, LightSensorDevice.class );
        refinedAvailables.put( MainsPowerOutlet.class, MainsPowerOutletDevice.class );
        refinedAvailables.put( OccupancySensor.class, OccupancySensorDevice.class );
        refinedAvailables.put( OnOffLight.class, OnOffLightDevice.class );
        refinedAvailables.put( OnOffLightSwitch.class, OnOffLightSwitchDevice.class );
        refinedAvailables.put( OnOffOutput.class, OnOffOutputDevice.class );
        refinedAvailables.put( OnOffSwitch.class, OnOffSwitchDevice.class );
        refinedAvailables.put( OnOffLight.class, OnOffLightDevice.class );
        refinedAvailables.put( Pump.class, PumpDevice.class );
        refinedAvailables.put( TemperatureSensor.class, TemperatureSensorDevice.class );
        refinedAvailables.put( IAS_Warning.class, IAS_Warning_Device.class );
        refinedAvailables.put( SimpleSensor.class, SimpleSensorDevice.class );

        final Iterator< Entry<Class<?>, Class<?>> > i = refinedAvailables.entrySet().iterator();
        while ( i.hasNext() ) {
            Entry<Class<?>, Class<?>> refining = i.next();
            try {
                factories.add( new GenericHADeviceFactory( bc, refining.getKey(), refining.getValue() ).register() );
            } catch ( Exception ex) {
                logger.error( "Failed to register GenericHADeviceFactory for " + refining.getKey(), ex );
            }
        }

        try {
            factories.add( new UnknownHADeviceFactory( bc ).register() );
        } catch ( Exception ex) {
            logger.error( "Failed to register UnknownHADeviceFactory", ex );
        }
    }

    public void stop(BundleContext context) throws Exception {
        haImporter.close();

        for (HADeviceFactoryBase factory : factories) {
            factory.unregister();
        }

    }

    public static HADriverConfiguration getConfiguration(){
        return configuration;
    }
}