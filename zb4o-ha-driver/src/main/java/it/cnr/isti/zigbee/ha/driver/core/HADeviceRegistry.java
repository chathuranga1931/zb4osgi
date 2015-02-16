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

package it.cnr.isti.zigbee.ha.driver.core;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.driver.HAImporter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 *
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.4.0
 *
 */
public class HADeviceRegistry {

    private final static Object REGISTRY = new Object();
    public final static String ANY_ZBDEVICE_FACTORY_FILTER = "("+Constants.OBJECTCLASS+"="+HADeviceFactory.class.getName()+")";

    private final static Logger log = LoggerFactory.getLogger(HADeviceRegistry.class);
    private final BundleContext ctx;
    private HashMap<String, HADeviceFactory> registry;
    private HAImporter refiner;

    public HADeviceRegistry(BundleContext ctx){
        this.ctx = ctx ;
        registry = new HashMap<String, HADeviceFactory>();

        try {
            ServiceReference[] zbDeviceSRs = ctx.getAllServiceReferences(HADeviceFactory.class.getName(), ANY_ZBDEVICE_FACTORY_FILTER);
            if (zbDeviceSRs!= null){
                for (int i= 0; i<zbDeviceSRs.length;i++){
                    addHADeviceFactory(zbDeviceSRs[i]);
                }
            }
        } catch (InvalidSyntaxException e) {
            log.error("Bad ANY_ZBDEVICE_FACTORY_FILTER: " + ANY_ZBDEVICE_FACTORY_FILTER,e);
        }
    }

    public void addHADeviceFactory(ServiceReference zbDeviceFactorySR) {
        HADeviceFactory factory = (HADeviceFactory) ctx.getService(zbDeviceFactorySR);
        String key = factory.getDeviceId();

        HADeviceFactory value;
        synchronized (REGISTRY) {
            value = registry.put(key, factory);
        }
        if(value != null){
            log.warn("ATTENTION a device with ID:" +  key + " has been replaced in the registry !!");
        }
    }

    public void  removeHADeviceFactory(ServiceReference zbDeviceFactorySR) {
        String deviceId = (String) zbDeviceFactorySR.getProperty(ZigBeeDevice.DEVICE_ID);
        synchronized (REGISTRY) {
            registry.remove(deviceId);
        }
        ctx.ungetService(zbDeviceFactorySR);
    }

    /**
     * This method selects among all the {@link HADeviceFactory} the best {@link HADeviceFactory} that can<br>
     * refine the {@link ZigBeeDevice} by using the method {@link HADeviceFactory#hasMatch(ServiceReference)}<br>
     * for comparing them
     *
     * @param sr the {@link ServiceReference} refering the the {@link ZigBeeDevice} to refine
     * @return the {@link HADeviceFactory} with the best {@link HADeviceFactory#hasMatch(ServiceReference)}
     * @since  0.7.0
     */
    public HADeviceFactory getBestFactory(ServiceReference sr) {

        synchronized (REGISTRY) {
            final Collection<HADeviceFactory> factories = registry.values();
            HADeviceFactory refining = null;
            int bestMatching = -1;

            for (Iterator<HADeviceFactory> iterator = factories.iterator(); iterator.hasNext();) {
                final HADeviceFactory factory = (HADeviceFactory) iterator.next();
                
                final int matching = factory.hasMatch( sr );
                if ( factory.hasMatch( sr ) > bestMatching ) {
                    refining = factory;
                    bestMatching = matching;
                }
            }

            return refining;
        }
    }

    /**
     * This method selects among all the {@link HADeviceFactory} the {@link HADeviceFactory} that match the rule<br>
     * {@link HADeviceFactory#getDeviceId()} equal to {@link ZigBeeDevice#getDeviceId()}
     *
     * @param zbDevice the {@link ZigBeeDevice} to refine
     * @return the {@link HADeviceFactory} whose {@link HADeviceFactory#getDeviceId()} equal to {@link ZigBeeDevice#getDeviceId()}
     */
    public HADeviceFactory getFactory(ZigBeeDevice zbDevice) {
        final String deviceId = String.valueOf(zbDevice.getDeviceId());

        synchronized (REGISTRY) {
            return registry.get(deviceId);
        }
    }

    /**
     * This method has never been used, it is a candidate for removal in the next release cycle
     * 
     * @param sr
     * @param device
     * @return
     * @throws ZigBeeHAException
     * @deprecated 
     */
    public HADeviceBase getInstance(ServiceReference sr, ZigBeeDevice device) throws ZigBeeHAException{
        final HADeviceFactory factory = getBestFactory(sr);

        if (factory != null) {
            return factory.getInstance(device);
        } else {
            return null;
        }
    }

	public HADeviceFactory getExactFactory(ZigBeeDevice zbDevice) {
	
		final String deviceId = String.valueOf(zbDevice.getDeviceId());
		
	        synchronized (REGISTRY) {
	        	HADeviceFactory exact = registry.get(deviceId);
	            if  (exact != null) 
	        		return exact;
        		return registry.get(UnknownHADevice.DEVICE_ID);
	        }
	      
	}

}
