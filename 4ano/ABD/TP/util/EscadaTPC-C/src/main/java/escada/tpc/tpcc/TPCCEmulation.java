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
import escada.tpc.common.util.RandGen;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * It extends the emulation class defining some methods according to the TPC-C
 * definition. Specifically, it defines the thinktime, keyingtime, process and
 * processIncrement.
 * 
 * @see Emulation
 */
public class TPCCEmulation extends Emulation implements Runnable {
	Logger logger = Logger.getLogger(TPCCEmulation.class);

	StateObject curTrans = null;

	// Pausable implementation
	private boolean paused = false, scheduled = false;
	
	public synchronized void pause() {
		this.paused = true;
	}

	public synchronized void resume() {
		this.paused = false;
		if (!scheduled)
			process();
	}

	public void stopit() {
		setFinished(true);
	}

	public void initialize() {
	}

	public long thinkTime() {
		long r = RandGen.negExp(getRandom(), curTrans.getThinkTime() * 1000,
				0.36788, curTrans.getThinkTime() * 1000, 4.54e-5, curTrans
						.getThinkTime() * 1000);
		return (r);
	}

	public long keyingTime() {
		return (curTrans.getKeyingTime());
	}
	
	public synchronized void process() {
		scheduled = false;
		
		if  (!((getMaxTransactions() == -1) || (getMaxTransactions() > 0)))
			return;
				
		if (isFinished()) {
			logger.info("Client is returning.");
			return;
		}

		// check if should pause
		if (paused)
			return;
			
		curTrans = getStateTransition().nextState();

		setKeyingTime(keyingTime());
		setThinkTime(getKeyingTime() + thinkTime());

		if (getStatusThinkTime())
			getScheduler().schedule(this, getThinkTime(), TimeUnit.MILLISECONDS);
		else
			getScheduler().execute(this);
		
		scheduled = true;
	}

	public void run() {
		try {
			curTrans.requestProcess(this, getHostId());

			curTrans.postProcess(this, getHostId());
			
			process();
			
		} catch (SQLException ex) {
			logger.error("Finishing execution since something went wrong with this thread.", ex);
			getScheduler().shutdownNow();
		}
	}
}
