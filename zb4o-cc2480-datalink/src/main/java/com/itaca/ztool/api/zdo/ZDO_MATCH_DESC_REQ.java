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

package com.itaca.ztool.api.zdo;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class ZDO_MATCH_DESC_REQ extends ZToolPacket /*implements IREQUEST,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_MATCH_DESC_REQ.DstAddr</name>
    /// <summary>destination address</summary>
    public ZToolAddress16 DstAddr;
    /// <name>TI.ZPI1.ZDO_MATCH_DESC_REQ.InClusterList</name>
    /// <summary>Array of input cluster IDs - NumInClusters long</summary>
    public DoubleByte[] InClusterList;
    /// <name>TI.ZPI1.ZDO_MATCH_DESC_REQ.NumInClusters</name>
    /// <summary>Number of ClusterIds in the InClusterList</summary>
    public int NumInClusters;
    /// <name>TI.ZPI1.ZDO_MATCH_DESC_REQ.NumOutClusters</name>
    /// <summary>Number of ClusterIds in the</summary>
    public int NumOutClusters;
    /// <name>TI.ZPI1.ZDO_MATCH_DESC_REQ.NWKAddrOfInterest</name>
    /// <summary>NWK address for the request</summary>
    public ZToolAddress16 NWKAddrOfInterest;
    /// <name>TI.ZPI1.ZDO_MATCH_DESC_REQ.OutClusterList</name>
    /// <summary>Array of output cluster IDs - NumOutClusters long</summary>
    public DoubleByte[] OutClusterList;
    /// <name>TI.ZPI1.ZDO_MATCH_DESC_REQ.ProfileID</name>
    /// <summary>Profile ID to match</summary>
    public DoubleByte ProfileID;

    /// <name>TI.ZPI1.ZDO_MATCH_DESC_REQ</name>
    /// <summary>Constructor</summary>
    public ZDO_MATCH_DESC_REQ() {
        this.InClusterList = new DoubleByte[0x10];
        this.OutClusterList = new DoubleByte[0x10];
    }

    public ZDO_MATCH_DESC_REQ(ZToolAddress16 num1, ZToolAddress16 num2, DoubleByte num3, int num4, DoubleByte[] numArray1, int num5, DoubleByte[] numArray2, int security_suite1) {
        this.DstAddr = num1;
        this.NWKAddrOfInterest = num2;
        this.ProfileID = num3;
        this.NumInClusters = num4;
        this.InClusterList = new DoubleByte[numArray1.length];
        this.InClusterList = numArray1;
        /*if (numArray1.Length > 0x10)
        {
        throw new Exception("Error creating object.");
        }
        this.InClusterList = new ushort[0x10];
        Array.Copy(numArray1, this.InClusterList, numArray1.Length);*/
        this.NumOutClusters = num5;
        this.OutClusterList = new DoubleByte[numArray1.length];
        this.OutClusterList = numArray1;
        /*if (numArray2.Length > 0x10)
        {
        throw new Exception("Error creating object.");
        }
        this.OutClusterList = new ushort[0x10];
        Array.Copy(numArray2, this.OutClusterList, numArray2.Length);*/


        int[] framedata = new int[8 + this.InClusterList.length * 2 + this.OutClusterList.length * 2];
        framedata[0] = this.DstAddr.getLsb();
        framedata[1] = this.DstAddr.getMsb();
        framedata[2] = this.NWKAddrOfInterest.getLsb();
        framedata[3] = this.NWKAddrOfInterest.getMsb();
        framedata[4] = this.ProfileID.getLsb();
        framedata[5] = this.ProfileID.getMsb();
        framedata[6] = this.NumInClusters;
        for (int i = 0; i < this.InClusterList.length; i++) {
            framedata[(i * 2) + 7] = this.InClusterList[i].getLsb();
            framedata[(i * 2) + 8] = this.InClusterList[i].getMsb();
        }
        framedata[((this.InClusterList.length) * 2) + 7] = this.NumOutClusters;
        for (int i = 0; i < this.OutClusterList.length; i++) {
            framedata[(i * 2) + ((this.InClusterList.length) * 2) + 8] = this.OutClusterList[i].getLsb();
            framedata[(i * 2) + ((this.InClusterList.length) * 2) + 9] = this.OutClusterList[i].getMsb();
        }
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_MATCH_DESC_REQ), framedata);
    }
}
