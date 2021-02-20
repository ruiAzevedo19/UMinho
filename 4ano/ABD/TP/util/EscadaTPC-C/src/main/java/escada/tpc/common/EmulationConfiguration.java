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

package escada.tpc.common;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

import escada.tpc.common.database.DatabaseManager;

public abstract class EmulationConfiguration {
	
	private static Random rand = new Random();

	private boolean finished = false;

	private String traceInformation = null;

	private int numberConcurrentEmulators = 1;

	private boolean key = true;

	private boolean resubmit = true;

	private DatabaseManager db = null;

	private StateTransition sb = null;

	private String name = null;

	private int id = 0;

	private long usmd = 0;

	private long thinkTime = 0;

	private long keyTime = 0;

	private long maxTrans = 1;

	private String hid = null;

	private ScheduledExecutorService ses = null;

	/**
	 * It defines the maximum number of transactions that can be executed.
	 * 
	 * @param long
	 *            the number of transactions. If a negative number is passed as
	 *            parameter the last value is not changed. A default value of 1
	 *            it is assumed.
	 * @see getMaxTransactions
	 */
	public void setMaxTransactions(long maxTrans) {
		if (maxTrans > 0)
			this.maxTrans = maxTrans;
	}

	/**
	 * It returns the maximum number of transactions that can be executed.
	 * 
	 * @return the number of transactions
	 */
	public long getMaxTransactions() {
		return (maxTrans);
	}

	/**
	 * It controls the remain number of transactions that can be executed.
	 */
	public void decMaxTransactions() {
		maxTrans--;
	}

	/**
	 * It returns if the emulation was finished or not.
	 * 
	 * @return the status (finished - true or unfinished - false) of the
	 *         emulation.
	 * @see setFinished
	 */
	public boolean isFinished() {
		return (finished);
	}

	/**
	 * It defines the status of the emulation as finished or unfinished,
	 * otherwise the name of the method is called setFinished. It is used to
	 * stop the emulation.
	 * 
	 * @param boolean
	 *            use true in order to stop or false otherwise not.
	 */
	public void setFinished(boolean fin) {
		finished = fin;
	}

	/**
	 * It defines the object used as a state transition, which determines the
	 * possible steps of the simulation.
	 * 
	 * @param StateTransition
	 *            the object that determines the state transition
	 * @see StateTransition,getStateTransition
	 */
	public void setStateTransition(StateTransition sb) {
		this.sb = sb;
	}

	/**
	 * It returns the object used as a state transition, which determines the
	 * possible steps of the simulation.
	 * 
	 * @return the object used as the state transition
	 * @see StateTransition,setStateTransition
	 */
	public StateTransition getStateTransition() {
		return (sb);
	}

	/**
	 * It defines the emulator's id.
	 * 
	 * @param int
	 *            the id of the emulator
	 * @see getEmulationId
	 */
	public void setEmulationId(int id) {
		this.id = id;
	}

	/**
	 * It returns the emulator's id.
	 * 
	 * @return the id of the emulator
	 * @see setEmulationId
	 */
	public int getEmulationId() {
		return (id);
	}

	/**
	 * It defines the emulator's name.
	 * 
	 * @param String
	 *            the emulator's name
	 * @see getEmulationName
	 */
	public void setEmulationName(String name) {
		this.name = name;
	}

	/**
	 * It returns the emulator's name.
	 * 
	 * @return the emulator's name
	 * @see setEmulationName
	 */
	public String getEmulationName() {
		return (name);
	}

	public void setStatusThinkTime(boolean key) {
		this.key = key;
	}

	public boolean getStatusThinkTime() {
		return (key);
	}

	public void setStatusReSubmit(boolean resubmit) {
		this.resubmit = resubmit;
	}

	public boolean getStatusReSubmit() {
		return resubmit;
	}

	/**
	 * It returns the last thinktime used by the emulator, which means the time
	 * used by the operator to take a decision. It is important to notice that
	 * we consider this value the thinktime as specified in the TPC-C plus the
	 * keyingtime to calculate this value.
	 * 
	 * @return the thinktime
	 * @see setThinkTime
	 */
	public long getThinkTime() {
		return (thinkTime);
	}

	/**
	 * It returns the last keyingtime time used by the emulator, which means the
	 * time used by the operator to fill a form.
	 * 
	 * @return the keyingtime
	 * @see setKeyingTime
	 */
	public long getKeyingTime() {
		return (keyTime);
	}

	/**
	 * It stores the last thinktime used by the emulator, which means the time
	 * used by the operator to take a decision. It is important to notice that
	 * we consider this value the thinktime as specified in the TPC-C plus the
	 * keyingtime to calculate this value.
	 * 
	 * @param long
	 *            the thinktime
	 * @see getThinkTime
	 */
	public void setThinkTime(long thinkTime) {
		this.thinkTime = thinkTime;
	}

	/**
	 * It stores the last keyingtime time used by the emulator, which means the
	 * time used by the operator to fill a form.
	 * 
	 * @param long
	 *            the keyingtime
	 * @see getKeyingTime
	 */
	public void setKeyingTime(long keyTime) {
		this.keyTime = keyTime;
	}

	/**
	 * It defines the trace file used to log information.
	 * 
	 * @param String
	 *            the name of the trace file
	 * @see getTraceInformation
	 */
	public void setTraceInformation(String trace) {
		traceInformation = trace;
	}

	/**
	 * It returns the name of the trace file used to log information.
	 * 
	 * @return the name of the trace file
	 * @see setTraceInformation
	 */
	public String getTraceInformation() {
		return (traceInformation);
	}

	/**
	 * It defines the number of concurrent emulators.
	 * 
	 * @param int
	 *            the number of concurrent emulators
	 * @see getNumberConcurrentEmulators
	 */
	public void setNumberConcurrentEmulators(int emu) {
		numberConcurrentEmulators = emu;
	}

	/**
	 * It returns the number of concurrent emulators.
	 * 
	 * @return the number of concurrent emulators
	 * @see setNumberConcurrentEmulators
	 */
	public int getNumberConcurrentEmulators() {
		return (numberConcurrentEmulators);
	}

	/**
	 * It returns the random object.
	 * 
	 * @return random object
	 */
	public Random getRandom() {
		return (rand);
	}

	/**
	 * It defines the host to which it the emulator belongs to.
	 * 
	 * @param String
	 *            the host id
	 * @see getHosId
	 */
	public void setHostId(String hid) {
		this.hid = hid;
	}

	/**
	 * It returns the host to which it the emulator belongs to.
	 * 
	 * @return the host id
	 * @see settHosId
	 */
	public String getHostId() {
		return (hid);
	}

	/**
	 * It returns the database used by the emulator in order to handle the
	 * transaction requests.
	 * 
	 * @return database based on the class CommonDatabase
	 */
	public DatabaseManager getDatabase() {
		return (db);
	}

	/**
	 * It defines the database used by the emulator in order to handle the
	 * transaction requests.
	 * 
	 * @param CommonDatabase
	 *            the object that defines the database, which
	 */
	public void setDatabase(DatabaseManager db) {
		this.db = db;
	}
	
	public void setScheduler(ScheduledExecutorService ses) {
		this.ses  = ses;
	}
	
	public ScheduledExecutorService getScheduler() {
		return (ses);
	}
}
