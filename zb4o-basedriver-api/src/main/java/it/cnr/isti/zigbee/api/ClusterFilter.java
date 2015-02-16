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

package it.cnr.isti.zigbee.api;

/**
 * This class represent the filter used by the {@link ZigBeeDevice} to identifies<br>
 * if the {@link ClusterListner} has to handle or not the {@link Cluster}.<br> 
 * In fact, the {@link ClusterListner#handleCluster(ZigBeeDevice, Cluster)} must be invoked<br>
 * if and only if either<br> 
 *  - the {@link ClusterFilter} returned by {@link ClusterListner#getClusterFilter()} is <code>null</code>, or<br>
 *  - the {@link ClusterFilter#match(Cluster)} on the cluster return <code>true</code>
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.4.0
 *
 */
public interface ClusterFilter {
	
	/**
	 * This method is invoked to check if the  {@link ZigBeeDevice} to check if {@link Cluster}<br>
	 * can be handled by the {@link ClusterListner} associated to this {@link ClusterFilter}
	 *  
	 * @param cluster the {@link Cluster} to match
	 * @return true if the {@link Cluster} match the {@link ClusterFilter}
	 */
	boolean match(Cluster cluster);
	
	
}
