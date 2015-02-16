/*
   Copyright 2008-2013 Andrew Rapp, http://code.google.com/p/xbee-api/

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

package com.itaca.ztool.util;

import java.util.ArrayList;
import java.util.List;

// TODO replace with nio.IntBuffer
/**
 * 
 * @author <a href="mailto:andrew.rapp@gmail.com">Andrew Rapp</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class IntArrayOutputStream {

	private List<Integer> intList = new ArrayList<Integer>();
	
	public IntArrayOutputStream() {

	}
	
	public void write (int val) {
		intList.add(val);
	}
	
	public void write(int[] val) {
		for (int i = 0; i < val.length; i++) {
			this.write(val[i]);
		}
	}
	
	public int[] getIntArray() {
		//int[] integer = (int[]) intList.toArray(new int[0]);
		// TODO there has got to be a better way -- how to convert list to int[] array?
		int[] intArr = new int[intList.size()];
		
		int i = 0;
		
		for (Integer integer : intList) {
			intArr[i++] = integer.intValue();
		}
		
		return intArr;
	}
}
