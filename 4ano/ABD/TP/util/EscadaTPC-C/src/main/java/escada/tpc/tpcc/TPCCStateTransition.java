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

import escada.tpc.common.StateObject;
import escada.tpc.common.StateTransition;
import escada.tpc.common.util.RandGen;

import java.util.Random;

/**
 * It defines the state machine of the TPC-C. First of all, it loads the
 * possible states according to the TPC-C's definitions and initialize the base
 * values used to calculate the thinktime and the keyingtime of each state
 * (transaction). It also defines the probability of occurrence for each state.
 */
public class TPCCStateTransition extends StateTransition {
	private final DeliveryTrans delivery = new DeliveryTrans();

	private final NewOrderTrans neworder = new NewOrderTrans();

	private final OrderStatusTrans orderstatus = new OrderStatusTrans();

	private final PaymentTrans payment = new PaymentTrans();

	private final StockLevelTrans stocklevel = new StockLevelTrans();

	private final StateObject[] trans = { neworder, payment, orderstatus,
			delivery, stocklevel };

	private Random objRand = new Random();

	protected int curState = 0;

	protected int oldState = 0;

	public TPCCStateTransition() {
		initStates();
	}

	public StateObject nextState() {
		StateObject nextState = null;
		int nrand = RandGen.nextInt(objRand, 100000);

		int probini = 0;
		int probend = trans[0].getProb() * 1000;
		for (int i = 0; i < TPCCConst.numState; i++) {
			if (nrand >= probini && nrand < probend) {
				nextState = trans[i];
				break;
			} else {
				nextState = trans[i];
				probend = probend + trans[i + 1].getProb() * 1000;
			}
		}
		curTrans = nextState;
		return nextState;
	}

	private void initStates() {
		for (int i = 0; i < TPCCConst.numState; i++) {
			trans[i].setProb();
			trans[i].setThinkTime();
			trans[i].setKeyingTime();
		}
	}

	public void loadStates(Object obj) {
	}
}
