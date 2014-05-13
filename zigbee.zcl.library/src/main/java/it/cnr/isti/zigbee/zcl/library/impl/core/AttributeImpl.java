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

package it.cnr.isti.zigbee.zcl.library.impl.core;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeType;
import it.cnr.isti.zigbee.zcl.library.api.global.DefaultResponse;
import it.cnr.isti.zigbee.zcl.library.api.global.ReadAttributesResponse;
import it.cnr.isti.zigbee.zcl.library.api.global.ReadAttributesStatus;
import it.cnr.isti.zigbee.zcl.library.api.global.WriteAttributeRecord;
import it.cnr.isti.zigbee.zcl.library.api.global.WriteAttributesResponse;
import it.cnr.isti.zigbee.zcl.library.api.global.WriteAttributesStatus;
import it.cnr.isti.zigbee.zcl.library.impl.ClusterImpl;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.AttributeDescriptor;
import it.cnr.isti.zigbee.zcl.library.impl.global.DefaultResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.global.read.ReadAttributeCommand;
import it.cnr.isti.zigbee.zcl.library.impl.global.read.ReadAttributesResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.global.write.WriteAttributeCommand;
import it.cnr.isti.zigbee.zcl.library.impl.global.write.WriteAttributeRecordImpl;
import it.cnr.isti.zigbee.zcl.library.impl.global.write.WriteAttributesResponseImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision$ ($LastChangedDate: 2014-04-01 12:02:50
 *          +0200 (mar, 01 apr 2014) $)
 * @since 0.1.0
 * 
 */
public class AttributeImpl implements Attribute {

	private static final Logger logger = LoggerFactory
			.getLogger(AttributeImpl.class);

	final private Object LazyInstantiation = new Object();
	private ZigBeeDevice zbDevice;
	private ZCLCluster zclCluster;
	private Subscription subscription;
	private AttributeDescriptor descriptor;

	public AttributeImpl(ZigBeeDevice zbDevice, ZCLCluster zclCluster,
			AttributeDescriptor descriptor) {
		this.zbDevice = zbDevice;
		this.zclCluster = zclCluster;
		this.descriptor = descriptor;
	}

	public int getId() {
		return descriptor.getId();
	}

	public String getName() {
		return descriptor.getName();
	}

	public Class<?> getType() {
		return descriptor.getType();
	}

	public ZigBeeType getZigBeeType() {
		return descriptor.getZigBeeType();
	}

	public boolean isReportable() {
		return descriptor.isReportable();
	}

	public boolean isWritable() {
		return descriptor.isWritable();
	}

	public void setValue(Object o) throws ZigBeeClusterException {
		if (isWritable() == false) {
			throw new ZigBeeClusterException("Trying to set the attribute "
					+ getName() + "(" + getId() + ") that is Read Only");
		}
		doClusterWideWrite(o);
	}

	public Object getValue() throws ZigBeeClusterException {
		return doClusterWideRead();
	}

	public Subscription getSubscription() {
		if (isReportable() == false)
			return null;

		synchronized (LazyInstantiation) {
			if (subscription == null) {
				if (getZigBeeType().isAnalog()) {
					subscription = new AnalogSubscriptionImpl(zbDevice,
							zclCluster, this);
				} else {
					subscription = new SubscriptionImpl(zbDevice, zclCluster,
							this);
				}
			}
		}
		return subscription;
	}

	private Object doClusterWideRead() throws ZigBeeClusterException {

		ReadAttributeCommand readAttrCom = new ReadAttributeCommand(
				new int[] { getId() });
		ZCLFrame frame = new ZCLFrame(readAttrCom,
				zclCluster.isDefaultResponseEnabled());
		ClusterImpl input;
		input = new ClusterImpl(zclCluster.getId(), frame);
		Cluster cluster = null;
		try {
			cluster = zbDevice.invoke(input);
			Response response = new ResponseImpl(cluster, zclCluster.getId());
			if (response.getZCLHeader().getTransactionId() != frame.getHeader()
					.getTransactionId()) {
				logger.error(
						"Received mismatching transaction response, "
								+ "we have to change heuristic for dispatching. Received {} while sent {}",
						response.getZCLHeader().getTransactionId(), frame
								.getHeader().getTransactionId());
				return null;
			}
			AttributeDescriptor[] requestedAttributes = new AttributeDescriptor[] { descriptor };

			switch (response.getZCLHeader().getCommandId()) {
			case ReadAttributesResponse.ID:
				ReadAttributesResponse readResponse = new ReadAttributesResponseImpl(
						response, requestedAttributes);
				ReadAttributesStatus attributeStatus = readResponse
						.getReadAttributeStatus()[0];
				if (attributeStatus.getStatus() == Status.SUCCESS.id) {
					return attributeStatus.getAttributeData();
				} else {
					Status state = Status
							.getStatus(attributeStatus.getStatus());
					throw new ZigBeeClusterException("Read Attribute of "
							+ getId() + " failed." + "Due to " + state
							+ " that means " + state.description);
				}
			case DefaultResponse.ID:
				// Means that the read command is not supported
				final DefaultResponse result = new DefaultResponseImpl(response);
				Status state = result.getStatus();
				throw new ZigBeeClusterException("Read Attribute of " + getId()
						+ " failed because command is not supported."
						+ "Due to " + state + " that means "
						+ state.description + " Follows the ZCLFrame recieved "
						+ ResponseImpl.toString(response));

			default:
				throw new ZigBeeClusterException("Read Attribute of " + getId()
						+ " failed due to: Unsupported answer: " + response
						+ " Follows the ZCLFrame recieved "
						+ ResponseImpl.toString(response));
			}
		} catch (ZigBeeBasedriverException e) {
			throw new ZigBeeClusterException(e);
		}
	}

	private void doClusterWideWrite(Object o) throws ZigBeeClusterException {
		WriteAttributeRecord writeAttrComRec = new WriteAttributeRecordImpl(
				this, o);
		WriteAttributeCommand writeAttrCom = new WriteAttributeCommand(
				new WriteAttributeRecord[] { writeAttrComRec });
		ZCLFrame frame = new ZCLFrame(writeAttrCom,
				zclCluster.isDefaultResponseEnabled());
		ClusterImpl input = new ClusterImpl(zclCluster.getId(), frame);
		try {
			Cluster cluster = zbDevice.invoke(input);
			Response response = new ResponseImpl(cluster, zclCluster.getId());
			AttributeDescriptor[] requestedAttributes = new AttributeDescriptor[] { descriptor };
			WriteAttributesResponse writeResposne = new WriteAttributesResponseImpl(
					response, requestedAttributes);
			WriteAttributesStatus attributeStatus = writeResposne
					.getWriteAttributesStatus()[0];
			if (attributeStatus.getStatus() != Status.SUCCESS.id) {
				Status state = Status.getStatus(attributeStatus.getStatus());
				throw new ZigBeeClusterException("Unable to write value "
						+ o.toString() + ". It failed with error " + state
						+ "(" + state.id + "):" + state.description
						+ ". Follows the ZCLFrame recieved "
						+ ResponseImpl.toString(writeResposne));
			}
		} catch (ZigBeeBasedriverException e) {
			throw new ZigBeeClusterException(e);
		}
		return;
	}

	public Object getDefaultValue() throws ZigBeeClusterException {
		// TODO Auto-generated method stub
		return null;
	}
}
