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

package it.cnr.isti.zigbee.basedriver.api.impl;

import it.cnr.isti.zigbee.api.ZigBeeNode;
import it.cnr.isti.zigbee.basedriver.Activator;
import it.cnr.isti.zigbee.util.IEEEAddress;

import java.util.Dictionary;
import java.util.Properties;

import com.itaca.ztool.api.ZToolAddress64;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate: 2013-10-30 10:52:39
 *          +0100(mer, 30 ott 2013) $)
 * @since 0.1.0
 * 
 */
public class ZigBeeNodeImpl implements ZigBeeNode {

	private int nwkAddress;
	final private String ieeeAddress;
	final private Properties description;

	/**
	 * 
	 * @param nwk
	 * @param ieee
	 * @param pan
	 * @since 0.6.0 - Revision 67
	 */
	public ZigBeeNodeImpl(int nwk, String ieee, short pan) {
		this.nwkAddress = nwk;
		this.ieeeAddress = ieee;
		IEEEAddress.fromColonNotation(ieee); // Only for checking the IEEE
												// format

		description = new Properties();
		description.put(ZigBeeNode.IEEE_ADDRESS, ieee);
		description.put(ZigBeeNode.NWK_ADDRESS, nwk);
		description.put(ZigBeeNode.PAN_ID, pan);
	}

	/**
	 * 
	 * @param nwk
	 * @param ieee
	 * @param pan
	 * @since 0.6.0 - Revision 67
	 */
	public ZigBeeNodeImpl(int nwk, String ieee) {
		this(nwk, ieee, Activator.getCurrentConfiguration().getPanId());
	}

	public ZigBeeNodeImpl(int nwk, ZToolAddress64 ieee) {
		this.ieeeAddress = IEEEAddress.toString(ieee.getLong());
		description = new Properties();
		description.put(ZigBeeNode.IEEE_ADDRESS, ieee);
		description.put(ZigBeeNode.PAN_ID, Activator.getCurrentConfiguration()
				.getPanId());
		setNetworkAddress(nwk);
	}

	public Dictionary getDescription() {
		return description;
	}

	public String getIEEEAddress() {
		return ieeeAddress;
	}

	/**
	 * 
	 * @param nwk
	 *            the new network address
	 * 
	 * @since 0.6.0 - Revision 74
	 */
	public void setNetworkAddress(int nwk) {
		nwkAddress = nwk;
		description.put(ZigBeeNode.NWK_ADDRESS, nwk);
	}

	public int getNetworkAddress() {
		return nwkAddress;
	}

	public String toString() {
		return nwkAddress + "(" + ieeeAddress + ") ";
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof ZigBeeNode) {
			ZigBeeNode node = (ZigBeeNode) obj;
			return nwkAddress == node.getNetworkAddress()
					&& ieeeAddress.equals(node.getIEEEAddress());
		} else {
			return false;
		}
	}

	public int hashCode() {
		return ieeeAddress.hashCode();
	}
}
