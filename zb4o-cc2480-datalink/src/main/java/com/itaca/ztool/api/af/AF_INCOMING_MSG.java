/*
   Copyright 2008-2013 ITACA-TSB, http://www.tsb.upv.es/
   Instituto Tecnologico de Aplicaciones de Comunicacion 
   Avanzadas - Grupo Tecnologias para la Salud y el 
   Bienestar (TSB)


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

package com.itaca.ztool.api.af;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.ByteUtils;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate: 2014-05-13 15:58:12
 *          +0200 (Tue, 13 May 2014) $)
 */
public class AF_INCOMING_MSG extends ZToolPacket /* implements IINDICATION,IAF */{

	private final static Logger profiler = LoggerFactory.getLogger("profiling."+ AF_INCOMING_MSG.class.getName());

	// / <name>TI.ZPI2.AF_INCOMING_MSG.ClusterID</name>
	// / <summary>specifies the cluster ID</summary>
	@Deprecated
	protected DoubleByte ClusterID;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.Data</name>
	// / <summary>Dynamic array, variable length field of size 'Len' and is the
	// transaction data frame</summary>
	@Deprecated
	protected int[] Data;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.DstEndpoint</name>
	// / <summary>specifies the endpoint of the destination device</summary>
	@Deprecated
	protected int DstEndpoint;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.GroupID</name>
	// / <summary>Group ID</summary>
	@Deprecated
	protected DoubleByte GroupID;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.Len</name>
	// / <summary>specifies the length of the Data field</summary>
	@Deprecated
	protected int Len;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.LinkQuality</name>
	// / <summary>indicates the link quality measured during reception.
	// TBD</summary>
	@Deprecated
	protected int LinkQuality;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.SecurityUse</name>
	// / <summary>indicates the type of security applied for the incoming NPDU.
	// This field is no longer used.</summary>
	@Deprecated
	protected int SecurityUse;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.SrcAddr</name>
	// / <summary>specifies the address of the source device</summary>
	@Deprecated
	protected ZToolAddress16 SrcAddr;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.SrcEndpoint</name>
	// / <summary>specifies the endpoint of the source device</summary>
	@Deprecated
	protected int SrcEndpoint;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.Timestamp</name>
	// / <summary>Timestamp</summary>
	@Deprecated
	protected long Timestamp;
	// /<name>TI.ZPI2.AF_INCOMING_MSG.TransSeqNumber</name>
	// / <summary>specifies the transaction Id of the device</summary>
	@Deprecated
	protected int TransSeqNumber;
	// / <name>TI.ZPI2.AF_INCOMING_MSG.WasBroadcast</name>
	// / <summary>WasBroadcast</summary>
	@Deprecated
	protected int WasBroadcast;
	private byte[] payload;

	// / <name>TI.ZPI2.AF_INCOMING_MSG</name>
	// / <summary>Constructor</summary>
	public AF_INCOMING_MSG() {
		this.Data = new int[0xff];
	}

	public AF_INCOMING_MSG(int[] framedata) {
		profiler.info("AF_INCOMING_MSG: creating object");
		this.GroupID = new DoubleByte(framedata[1], framedata[0]);
		this.ClusterID = new DoubleByte(framedata[3], framedata[2]);
		this.SrcAddr = new ZToolAddress16(framedata[5], framedata[4]);
		this.SrcEndpoint = framedata[6];
		this.DstEndpoint = framedata[7];
		this.WasBroadcast = framedata[8];
		this.LinkQuality = framedata[9];
		this.SecurityUse = framedata[10];
		byte[] bytes = new byte[4];
		bytes[3] = (byte) framedata[11];
		bytes[2] = (byte) framedata[12];
		bytes[1] = (byte) framedata[13];
		bytes[0] = (byte) framedata[14];
		this.Timestamp = ByteUtils.convertMultiByteToLong(bytes);
		this.TransSeqNumber = framedata[15];
		this.Len = framedata[16];
		this.Data = new int[framedata.length - 17];
		for (int i = 0; i < this.Data.length; i++) {
			this.Data[i] = framedata[17 + i];
		}
		super.buildPacket(new DoubleByte(ZToolCMD.AF_INCOMING_MSG), framedata);
		profiler.info("AF_INCOMING_MSG: object created");
	}

	// / <name>TI.ZPI2.AF_INCOMING_MSG.SECURITY_STATUS</name>
	// / <summary>Security status</summary>
	public class SECURITY_STATUS {
		// / <name>TI.ZPI2.AF_INCOMING_MSG.SECURITY_STATUS.ENABLED</name>
		// / <summary>Security status</summary>
		public static final int ENABLED = 1;
		// / <name>TI.ZPI2.AF_INCOMING_MSG.SECURITY_STATUS.NOT_ENABLED</name>
		// / <summary>Security status</summary>
		public static final int NOT_ENABLED = 0;
	}

	public byte getTransId() {
		return (byte) (super.packet[ZToolPacket.PAYLOAD_START_INDEX + 15]);
	}

	public byte getSrcEndpoint() {
		return (byte) super.packet[ZToolPacket.PAYLOAD_START_INDEX + 6];
	}

	public short getDstEndpoint() {
		return (byte) super.packet[ZToolPacket.PAYLOAD_START_INDEX + 7];
	}

	public short getSrcAddr() {
		return (short) ((super.packet[ZToolPacket.PAYLOAD_START_INDEX + 5] << 8) 
				+ (super.packet[ZToolPacket.PAYLOAD_START_INDEX + 4]));
	}

	public short getClusterId() {
		return (short) ((super.packet[ZToolPacket.PAYLOAD_START_INDEX + 3] << 8) 
				+ (super.packet[ZToolPacket.PAYLOAD_START_INDEX + 2]));
	}

	public short getGroupId() {
		return (short) ((super.packet[ZToolPacket.PAYLOAD_START_INDEX + 1] << 8) 
				+ (super.packet[ZToolPacket.PAYLOAD_START_INDEX + 0]));
	}

	public byte[] getData() {
		if (payload == null) {
			payload = new byte[Data.length];
			for (int i = 0; i < payload.length; i++) {
				payload[i] = (byte) Data[i];
			}

		}
		return payload;
	}

}