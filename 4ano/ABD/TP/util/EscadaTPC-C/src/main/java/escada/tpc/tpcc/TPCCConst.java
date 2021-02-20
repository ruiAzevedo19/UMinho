/*
 * Copyright 2013 Universidade do Minho
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software   distributed under the License is distributed on an "AS IS" BASIS,   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and limitations under the License.
 */

package escada.tpc.tpcc;

import escada.tpc.common.TPCConst;

/**
 * It defines important constants used in the simulation of the TPC-C.
 */
public class TPCCConst {
	
	public final static int CustomerA = 1023;

	public final static int numINICustomer = 1;

	public static int numENDCustomer = 3000;

	public final static int qtdINIItem = 5;

	public final static int qtdENDItem = 15;

	public final static int qtdItem = 10;

	public final static int iidA = 8191;

	public final static int numINIItem = 1;

	public static int numENDItem = 100000;

	public final static int rngCarrier = 10;

	public static int rngDistrict = 10;

	public final static int probABORTNewOrder = 1;

	public final static int rngABORTNewOrder = 100;

	public final static int probNewOrderLOCALWarehouse = 99;

	public final static int rngNewOrderLOCALWarehouse = 100;

	public final static int probPaymentLOCALWarehouse = 85;

	public final static int rngPaymentLOCALWarehouse = 100;

	public final static int probLASTNAME = 60;

	public final static int rngLASTNAME = 100; 

	public final static int LastNameA = 255; // 20

	public final static int numINILastName = 0;

	public static int numENDLastName = 999;

	public final static int numINIAmount = 100;

	public final static int numENDAmount = 500000;

	public final static int numDIVAmount = 100;

	public final static int numINIThreshHold = 10;

	public final static int numENDThreshHold = 20;

	public final static int numState = 5;

	public static void setNumCustomer(int numENDCustomer) {
		TPCCConst.numENDCustomer = numENDCustomer;
	}

	public static int getNumCustomer() {
		return numENDCustomer;
	}

	public static void setNumItem(int numENDItem) {
		TPCCConst.numENDItem = numENDItem;
	}

	public static int getNumItem() {
		return numENDItem;
	}

	public static void setNumDistrict(int rngDistrict) {
		TPCCConst.rngDistrict = rngDistrict;
	}

	public static int getNumDistrict() {
		return rngDistrict;
	}

	public static void setNumLastName(int numENDLastName) {
		TPCCConst.numENDLastName = numENDLastName;
	}

	public static int getNumLastName() {
		return numENDLastName;
	}

	public static int getCliWareHouse() {
		return TPCConst.getNumMinClients();
	}
}
