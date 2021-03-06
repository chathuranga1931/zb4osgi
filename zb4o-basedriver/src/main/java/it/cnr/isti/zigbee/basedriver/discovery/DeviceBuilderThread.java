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

package it.cnr.isti.zigbee.basedriver.discovery;

import gnu.trove.TByteObjectHashMap;
import it.cnr.isti.thread.Stoppable;
import it.cnr.isti.thread.ThreadUtils;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.api.ZigBeeNode;
import it.cnr.isti.zigbee.basedriver.Activator;
import it.cnr.isti.zigbee.basedriver.api.impl.ZigBeeDeviceImpl;
import it.cnr.isti.zigbee.basedriver.api.impl.ZigBeeNodeImpl;
import it.cnr.isti.zigbee.basedriver.communication.AFLayer;
import it.cnr.isti.zigbee.basedriver.discovery.ImportingQueue.ZigBeeNodeAddress;
import it.cnr.isti.zigbee.dongle.api.ConfigurationProperties;
import it.cnr.isti.zigbee.dongle.api.DuplicateMacPolicy;
import it.cnr.isti.zigbee.dongle.api.SimpleDriver;
import it.cnr.isti.zigbee.util.IEEEAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolAddress64;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_REQ;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_RSP;

/**
 *
 * This class implements the {@link Thread} that completes the discovery of the
 * node<br>
 * found either by {@link NetworkBrowserThread} or
 * {@link AnnounceListenerThread} by<br>
 * inspecting the <i>End Point</i> on the node.<br>
 * The inspection of each <i>End Point</i> lead to the creation
 * {@link ZigBeeDevice}<br>
 * service, that is registered on the OSGi framework.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 *
 * @version $LastChangedRevision$ ($LastChangedDate: 2013-10-09 16:27:35
 *          +0200 (mer, 09 ott 2013) $)
 * @since 0.1.0
 *
 */
public class DeviceBuilderThread implements Stoppable {

    private static final Logger logger = LoggerFactory
            .getLogger(DeviceBuilderThread.class);

    private final ImportingQueue queue;
    private final List<ZigBeeDeviceReference> failedDevice = new ArrayList<ZigBeeDeviceReference>();

    private Map<ZigBeeDeviceReference, Integer> failedAttempts = new HashMap<ZigBeeDeviceReference, Integer>();
    private final int maxRetriesFailedDevices = 5;

    private Map<ZigBeeDeviceReference, Long> delayedReattempts = new HashMap<ZigBeeDeviceReference, Long>();
    private final long delay = 30000;

    private final SimpleDriver driver;
    private boolean end;

    private long nextInspectionSlot = 0;
    private ZigBeeNodeAddress dev;

    private class ZigBeeDeviceReference {
        ZigBeeNode node;
        byte endPoint;

        ZigBeeDeviceReference(ZigBeeNode node, byte endPoint) {
            super();
            this.node = node;
            this.endPoint = endPoint;
        }
    }

    public DeviceBuilderThread(ImportingQueue queue, SimpleDriver driver) {
        this.queue = queue;
        this.driver = driver;
    }

    private ZDO_ACTIVE_EP_RSP doInspectDeviceOfNode(final int nwkAddress,
            final ZigBeeNode node) {
        // TODO Move into SimpleDriver?!?!?
        logger.info(
                "Inspecting device on node #{} by issuing ZDO_ACTIVE_EP_REQ",
                nwkAddress);

        int i = 1;
        ZDO_ACTIVE_EP_RSP result = null;

        while (i <= Activator.getCurrentConfiguration().getMessageRetryCount()) {
            result = driver.sendZDOActiveEndPointRequest(new ZDO_ACTIVE_EP_REQ(
                    nwkAddress));

            if (result == null) {
                final long waiting = Activator.getCurrentConfiguration()
                        .getMessageRetryDelay();
                logger.debug(
                        "Inspecting device on node {} failed during it {}-nth attempt. "
                                + "Waiting for {}ms before retrying",
                        new Object[] { node, i, waiting });
                ThreadUtils.waitNonPreemptive(waiting);
                i++;
            } else {
                break;
            }
        }

        return result;
    }

    private boolean inspectDeviceOfNode(final int nwkAddress,
            final ZigBeeNode node) {

        final ZDO_ACTIVE_EP_RSP result = doInspectDeviceOfNode(nwkAddress, node);
        if (result == null) {
            logger.warn("ZDO_ACTIVE_EP_REQ FAILED on {}", node);
            return false;
        }

        byte[] endPoints = result.getActiveEndPointList();
        logger.info("ZDO_ACTIVE_EP_REQ SUCCESS with {} from {}",
                endPoints.length, node);

        final ZigBeeNetwork network = AFLayer.getAFLayer(driver)
                .getZigBeeNetwork();

        for (int i = 0; i < endPoints.length; i++) {
            logger.info("Creating {} service for endpoint {} on node {}",
                    new Object[] { ZigBeeDevice.class, endPoints[i], node });

            synchronized (network) {
                if (network.containsDevice(node.getIEEEAddress(), endPoints[i])) {
                    switch (Activator.getCurrentConfiguration().getDiscoveryDuplicateMacPolicy()) {
                    case IGNORE:
                        logger.info(
                                "Skipping service creation for endpoint {} on node {} it is already registered as a Service",
                                endPoints[i], node);

                        break;
                    case REGISTER:
                        logger.info(
                                "Node ieee collision, stored is {} and new one is {}, De-registering old service and registering new one",
                                endPoints[i], node);
                        doRemoveZigBeeDeviceService(node, endPoints[i]);
                        doCreateZigBeeDeviceService(node, endPoints[i]);

                        break;
                    case UPDATE:
                        logger.info(
                                "Node ieee collision, stored is {} and new one is {}, updating NetworkAddress",
                                endPoints[i], node);
                        doUpdateZigBeeDeviceService(node, endPoints[i]);
                        break;
                    default:
                        break;
                    }

                } else {
                    doCreateZigBeeDeviceService(node, endPoints[i]);
                }

            }

        }
        return true;
    }

	private void doUpdateZigBeeDeviceService(ZigBeeNode node, byte ep) {
		final ZigBeeNetwork network = AFLayer.getAFLayer(driver).getZigBeeNetwork();
		try {
			ZigBeeDeviceImpl device = new ZigBeeDeviceImpl(driver, node, ep);
			if (network.removeDevice(node, ep) && network.addDevice(device)) {
				synchronized (Activator.devices) {
					TByteObjectHashMap<ServiceRegistration> nodeServices = Activator.devices.get(node.getIEEEAddress());
					ServiceRegistration endpointService = nodeServices.get(ep);
					// FIX the line below should be throw a ClassCastException but it is not tested by any TestUnit
					((ZigBeeNodeImpl) endpointService).setNetworkAddress(node.getNetworkAddress());
				}
			}
		} catch (ZigBeeBasedriverException e) {
			e.printStackTrace();
		}
	}

    private void doRemoveZigBeeDeviceService(ZigBeeNode node, byte ep) {

        final ZigBeeNetwork network = AFLayer.getAFLayer(driver)
                .getZigBeeNetwork();

        if (network.removeDevice(node, ep)) {
            synchronized (Activator.devices) {
                TByteObjectHashMap<ServiceRegistration> nodeServices = Activator.devices
                        .get(node.getIEEEAddress());
                ServiceRegistration endpointService = nodeServices.get(ep);
                endpointService.unregister();
            }
        }
    }

    private void doCreateZigBeeDeviceService(ZigBeeNode node, byte ep) {
        final ZigBeeNetwork network = AFLayer.getAFLayer(driver)
                .getZigBeeNetwork();
        try {
            ZigBeeDeviceImpl device = new ZigBeeDeviceImpl(driver, node, ep);
            if (network.addDevice(device)) {
                ServiceRegistration registration = Activator.getBundleContext()
                        .registerService(ZigBeeDevice.class.getName(), device,
                                device.getDescription());
                TByteObjectHashMap<ServiceRegistration> list;
                synchronized (Activator.devices) {
                    final String ieee = node.getIEEEAddress();
                    list = Activator.devices.get(ieee);
                    if (list == null) {
                        list = new TByteObjectHashMap<ServiceRegistration>();
                        Activator.devices.put(ieee, list);
                    }
                }
                synchronized (list) {
                    list.put(ep, registration);
                }
            } else {
                logger.error(
                        "Failed to add endpoint {} to the network map for node {}",
                        ep, node);
            }
        } catch (ZigBeeBasedriverException e) {
            logger.error("Error building the device: {}", node, e);

            ZigBeeDeviceReference last = new ZigBeeDeviceReference(node, ep);
            if (!failedAttempts.containsKey(last))
                failedAttempts.put(last, 0);
            else if (failedAttempts.get(last) + 1 < maxRetriesFailedDevices)
                failedAttempts.put(last, failedAttempts.get(last) + 1);
            else {
                logger.debug(
                        "Too many attempts failed, device {}:{} adding delayed of {} ms",
                        new Object[] { node, ep, delay });
                failedDevice.remove(last);
                delayedReattempts.put(last, delay);
            }
        }
    }

    private void inspectNode(ZToolAddress16 nwkAddress,
            ZToolAddress64 ieeeAddress) {

        int nwk = nwkAddress.get16BitValue();
        final String ieee = IEEEAddress.toString(ieeeAddress.getLong());
        ZigBeeNodeImpl node = null;
        boolean isNew = false, correctlyInspected = false;
        final ZigBeeNetwork network = AFLayer.getAFLayer(driver)
                .getZigBeeNetwork();
        synchronized (network) {
            node = network.containsNode(ieee);
            if (node == null) {
                node = new ZigBeeNodeImpl(nwk, ieeeAddress);
                isNew = true;
                network.addNode(node);
                logger.debug(
                        "Created node object for {} that was not available on the network",
                        node);
            }
        }
        if (isNew) {
            logger.info(
                    "Creating a new set of services for ZigBee Node {} ({})",
                    nwk, ieee);
            correctlyInspected = inspectDeviceOfNode(nwk, node);
            if (correctlyInspected) {
                return;
            } else {
                // if you don't remove node with devices not yet inspected from
                // network, you won't be able to re-inspect them later
                // maybe device is sleeping and you have to wait for a
                // non-sleeping period
                logger.debug(
                        "Node {} removed from network because attempts to instantiate devices on it are failed",
                        node);
                network.removeNode(node);
            }
        } else if (node.getNetworkAddress() != nwk) { // TODO We have to verify
                                                        // this step by means of
                                                        // JUnit
            logger.warn(
                    "The device {} has been found again with a new network address {} ",
                    node, nwkAddress.get16BitValue());

            switch (Activator.getCurrentConfiguration()
                    .getDiscoveryDuplicateMacPolicy()) {
            case IGNORE:
                logger.debug(
                        "We are going to use the old network address for the EndPoint as specified by the current {} configuration",
                        ConfigurationProperties.DISCOVERY_DUPLICATE_MAC_KEY);
                break;
            case REGISTER:
                logger.error("The policy {} has not been implemented yet, so we fall back to {} policy", DuplicateMacPolicy.REGISTER, DuplicateMacPolicy.UPDATE);
            case UPDATE:
                logger.debug("We are going to update the network address for all the ZigBeeDevice serive host by this node");
                /*
                 * TODO 1 - Verify that it is actually the same device: ieee
                 * matches and all the endpoints contained are not changed 1.1 -
                 * If it is true ( we assume that it is always true, only during
                 * firmware development of device the device can change the on
                 * board endpoints), then we have to update the network address
                 * for the services
                 *
                 * 1.2 - If it is false (very rare and not handled) we should
                 * remove all the service and inspect again the device NOT
                 * HANDLED AT THE MOMENT
                 */
                updateNetworkAddress(node, nwk);
                break;
            }

            if (Activator.devices.get(node.getIEEEAddress()) == null) {
                /*
                 * No previous device inspection completed successfully, so we
                 * should try to inspect the device again
                 */
                inspectDeviceOfNode(nwk, node);
            }

        }
    }

    /**
     * This method updates the network address on all the device belonging the
     * node<br>
     * with the change network address<br>
     *
     * @param node
     *            {@link ZigBeeNodeImpl} the old node with the obsoleted network
     *            address
     * @param nwk
     *            the new network address of the node
     * @return if at least a device has been updated
     * @since 0.6.0 - Revision 74
     */
    private void updateNetworkAddress(ZigBeeNodeImpl node, int nwk) {
        /*
         * This may happen either for two reason: A - Device has re-joined the
         * network, it may happen either in end-user or ZigBee developer
         * environment B - Device has been re-programmed and it joins as new
         * device on the network, it could happen only on ZigBee developer
         * environment The actual code handle only the case A
         */

        node.setNetworkAddress(nwk);
        ZigBeeNodeImpl aux = node.clone();

        final TByteObjectHashMap<ServiceRegistration> registrations;
        synchronized (Activator.devices) {
            registrations = Activator.devices.get(node.getIEEEAddress());
        }
        if (registrations == null) {
            logger.error("No registered service to update, but we thought that it was a clashing because "
                    + "we identified a network address changing");
            return;
        }
        ServiceRegistration[] regs = registrations
                .getValues(new ServiceRegistration[] {});
        for (int i = 0; i < regs.length; i++) {
            ServiceReference ref = regs[i].getReference();
            final ZigBeeDeviceImpl device = (ZigBeeDeviceImpl) Activator
                    .getBundleContext().getService(ref);
            device.setPhysicalNode(aux);
            regs[i].setProperties(device.getDescription());
        }
    }

    private void inspectNewlyDevice() {
        nextInspectionSlot = Activator.getCurrentConfiguration()
                .getDeviceInspectionPeriod() + System.currentTimeMillis();
        final ZigBeeNodeAddress dev = queue.pop();
        if (dev == null)
            return;
        logger.info("Trying to register a node extracted from ImportingQueue");
        final ZToolAddress16 nwk = dev.getNetworkAddress();
        final ZToolAddress64 ieee = dev.getIEEEAddress();
        logger.info("Creating a new set of services for ZigBee Node {} ({})",
                nwk, ieee);
        inspectNode(nwk, ieee);
        logger.debug(
                "Devices inspection completed, next inspection slot in {}",
                Math.max(nextInspectionSlot - System.currentTimeMillis(), 0));
    }

    private void inspectFailedDevice() {
        // TODO We should add a statistical history for removing a device when
        // we tried it too many times
        logger.info("Trying to register a node extracted from FailedQueue");
        final ZigBeeDeviceReference failed = failedDevice.get(0);
        nextInspectionSlot = Activator.getCurrentConfiguration()
                .getDeviceInspectionPeriod() + System.currentTimeMillis();
        doCreateZigBeeDeviceService(failed.node, failed.endPoint);
    }

    /**
     * @return the number of Node waiting for inspection
     * @since 0.6.0 - Revision 71
     */
    public int getPendingNodes() {
        return queue.size();
    }

    /**
     * @return the number of Node waiting for inspection
     * @since 0.6.0 - Revision 71
     */
    public int getPendingDevices() {
        return failedDevice.size();
    }

    public void run() {
        logger.info("{} STARTED Successfully", Thread.currentThread().getName());

        while (!isEnd()) {
            try {
                if (!delayedReattempts.isEmpty()) {
                    Iterator<Entry<ZigBeeDeviceReference, Long>> iterator = delayedReattempts
                            .entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<ZigBeeDeviceReference, Long> device = iterator
                                .next();
                        if ((device.getValue() + delay) >= System
                                .currentTimeMillis()) {
                            failedDevice.add(device.getKey());
                            logger.debug(
                                    "EP {} of node {} has been readded to queue for inspection after {} ms",
                                    new Object[] {
                                            device.getKey().endPoint,
                                            device.getKey().node,
                                            System.currentTimeMillis()
                                                    - device.getValue() });
                        }
                    }
                }
                ThreadUtils.waitingUntil(nextInspectionSlot);

                if (queue.size() > 0 && failedDevice.size() > 0) {
                    double sel = Math.random();
                    if (sel > 0.6) {
                        inspectFailedDevice();
                    } else {
                        inspectNewlyDevice();
                    }
                } else if (queue.size() == 0 && failedDevice.size() > 0) {
                    inspectFailedDevice();
                } else if (queue.size() > 0 && failedDevice.size() == 0) {
                    inspectNewlyDevice();
                } else if (queue.size() == 0 && failedDevice.size() == 0) {
                    inspectNewlyDevice();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.info("{} TERMINATED Successfully", Thread.currentThread()
                .getName());
    }

    public synchronized boolean isEnd() {
        return end;
    }

    public synchronized void end() {
        end = true;
    }
}