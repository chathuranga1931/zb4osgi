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

package it.cnr.isti.zigbee.api;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface ClusterListner {

	/**
	 * Set the {@link ClusterListner}  of this ClusterListner.<br>
	 * A <code>null</code> values means no filtering  
	 * 
	 * @param filter the {@link ClusterListner} to associates to this ClusterListner.<br>
	 * 				<code>null</code> to disable the filtering
	 * @since 0.4.0
	 */
	public void setClusterFilter(ClusterFilter filter);

	/**
	 * Return the {@link ClusterListner}  associated to this ClusterListner.<br>
	 * A <code>null</code> values means no filtering  
	 * 
	 * @return the {@link ClusterListner} associated to this ClusterListner
	 * 
	 * @since 0.4.0
	 */
	public ClusterFilter getClusterFilter();
	
	/**
	 * The callback invoked by the ZigBee Base Driver to notify for a new {@link Cluster}
	 * 
	 * @param device reference to the {@link ZigBeeDevice} receiving the {@link Cluster}
	 * @param cluster reference to the received {@link Cluster}
	 */
	public void handleCluster(ZigBeeDevice device, Cluster cluster);
	
}
