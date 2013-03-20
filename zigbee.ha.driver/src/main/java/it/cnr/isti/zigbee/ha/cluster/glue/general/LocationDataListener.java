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
package it.cnr.isti.zigbee.ha.cluster.glue.general;

import it.cnr.isti.zigbee.zcl.library.api.core.Status;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.7.0
 */
public interface LocationDataListener {

    public void locationDataFailure(Status state);
    
    public void locationAbsoluteData(boolean rectangular, int x, int y, int z, int power, int ple, LocationMethod method);
    
    public void locationAbsolute2DData(boolean rectangular, int x, int y, int power, int ple, LocationMethod method);

    public void locationData(boolean rectangular, int x, int y, int z, int power, int ple, LocationMethod method, int quality, int age);
    
    public void location2DData(boolean rectangular, int x, int y, int power, int ple, LocationMethod method, int quality, int age);
    
    public void locationAbsoluteData(boolean rectangular, int x, int y, int z);
    
    public void locationAbsolute2DData(boolean rectangular, int x, int y);

    public void locationData(boolean rectangular, int x, int y, int z, int quality, int age);
    
    public void location2DData(boolean rectangular, int x, int y, int quality, int age);
    
}
