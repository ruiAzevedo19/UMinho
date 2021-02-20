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

import escada.tpc.common.Emulation;
import escada.tpc.common.StateObject;
import escada.tpc.common.TPCConst;
import escada.tpc.common.util.RandGen;
import escada.tpc.common.resources.WorkloadResources;
import escada.tpc.tpcc.database.transaction.dbTPCCDatabase;

import java.sql.SQLException;

/**
 * It implements the states according to the definition of the TPC-C. Basically,
 * it sets up import information used in the execution of the new order
 * transaction. Additionally, it defines the trace flag, which is a boolean
 * value used to log traces or not and the trace file.
 */
public class NewOrderTrans extends StateObject {

	private WorkloadResources workloadResources;
	
	public void initProcess(Emulation em, String hid) throws SQLException {
		int wid = (em.getEmulationId() / TPCConst.getNumMinClients()) + 1;
		int did = 0;
		int cid = 0;
		int qtd = 0;
		boolean error = false;
		boolean localWarehouse = false;
		
		System.out.println("Accessing warehouse " + wid);

		outInfo
				.put("resubmit", Boolean
						.toString(em.getStatusReSubmit()));
		outInfo.put("trace", em.getTraceInformation());
		outInfo.put("abort", Integer.toString(0));
		outInfo.put("hid", hid);

		outInfo.put("wid", Integer.toString(wid));

		did = RandGen.nextInt(em.getRandom(), 1, TPCCConst.getNumDistrict() + 1);
		outInfo.put("did", Integer.toString(did));

		cid = RandGen.NURand(em.getRandom(), TPCCConst.CustomerA,
				TPCCConst.numINICustomer, TPCCConst.getNumCustomer());
		outInfo.put("cid", Integer.toString(cid));

		qtd = RandGen.nextInt(em.getRandom(), TPCCConst.qtdINIItem,
				TPCCConst.qtdENDItem + 1);

		outInfo.put("qtd", Integer.toString(qtd));
		
		if (RandGen.nextInt(em.getRandom(), TPCCConst.rngABORTNewOrder + 1) == TPCCConst.probABORTNewOrder) {
			error = true;
		}

		int i = 0;
		int iid = 1;
		int qtdi = 0;
		int supwid = 0;
		while (i < qtd) {
			iid = RandGen.NURand(em.getRandom(), TPCCConst.iidA,
					TPCCConst.numINIItem, TPCCConst.getNumItem());
			qtdi = RandGen.nextInt(em.getRandom(), 1, TPCCConst.qtdItem + 1);
			if ((error) && ((i + 1) >= qtd)) {
				iid = 1;
				outInfo.put("abort", Integer.toString(1));
			}
			outInfo.put("iid" + i, Integer.toString(iid));
			outInfo.put("qtdi" + i, Integer.toString(qtdi));
			if ((RandGen.nextInt(em.getRandom(),
					TPCCConst.rngNewOrderLOCALWarehouse + 1) <= TPCCConst.probNewOrderLOCALWarehouse)
					|| (em.getNumberConcurrentEmulators() <= TPCConst.getNumMinClients())) {
				outInfo.put("supwid" + i, Integer.toString(wid));
			} else {
				supwid = RandGen.nextInt(em.getRandom(), 1, (em
						.getNumberConcurrentEmulators() / TPCConst.getNumMinClients()) + 1);
				outInfo.put("supwid" + i, Integer.toString(supwid));
				localWarehouse = false;
			}
			i++;
		}

		if (localWarehouse) {
			outInfo.put("localwid", Integer.toString(1));
		} else {
			outInfo.put("localwid", Integer.toString(0));
		}

		outInfo.put("thinktime", Long.toString(em.getThinkTime()));
		outInfo.put("file", em.getEmulationName());
	}

	public void prepareProcess(Emulation em, String hid) throws SQLException {

	}

	public Object requestProcess(Emulation em, String hid) throws SQLException {
		Object requestProcess = null;
		dbTPCCDatabase db = (dbTPCCDatabase) em.getDatabase();
		initProcess(em, hid);
		requestProcess = db.TraceNewOrderDB(outInfo, hid);

		return (requestProcess);
	}

	public void postProcess(Emulation em, String hid) throws SQLException {
		inInfo.clear();
		outInfo.clear();
	}

	public void setProb() {
		this.workloadResources = new WorkloadResources();
		prob=this.workloadResources.getProbNewOrder();
		//prob = 45;
	}

	public void setKeyingTime() {
		keyingtime = 18;
	}

	public void setThinkTime() {
		thinktime = 12;
	}

	public String toString() {
		return ("NewOrderTrans");
	}
}
