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

package escada.tpc.common.clients;

import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.Logger;

import escada.tpc.common.Emulation;
import escada.tpc.common.EmulationConfiguration;
import escada.tpc.common.PausableEmulation;
import escada.tpc.common.StateTransition;
import escada.tpc.common.TPCConst;

/**
 * It initializes the client Emulation instantiating the appropriate objects.
 * 
 */
public class ClientEmulation extends EmulationConfiguration implements
		PausableEmulation {
	private static Logger logger = Logger.getLogger(ClientEmulation.class);

	private ClientEmulationMaster master;

	private Emulation e = null;

	private String controlKey = null;

	/**
	 * It initializes the client Emulation instantiating the appropriate
	 * objects. Specifically, it defines the emulation object to be used (e.g.,
	 * TPCCEmulation), the state transition object (e.g., TPCCStateTransition),
	 * the database interface (e.g., dbPostgresql). Besides, it also defines the
	 * total number of clients, the client id and the host id to which the
	 * client belongs. It is important to notice that the parameters bellow
	 * which reference a class must be fully qualified.
	 * 
	 * @param emParam
	 *            the class used to instantiate the emulation object (e.g.,
	 *            TPCCEmulation)
	 * @param stateParam
	 *            the class used to instantiate the state transition object
	 *            (e.g., TPCCStateTransition)
	 * @param dbParam
	 *            the class used to instantiate the database object (e.g.,
	 *            dbPostgresql)
	 * @param totalCli
	 *            the total number of clients
	 * @param ncli
	 *            the identification of the client
	 * @param nfrag
	 *            the offset the must be applied to the client in order to it to
	 *            get the correct identification.
	 * @param hid
	 *            the host to which the client belongs
	 */
	public void create(ScheduledExecutorService ses, String emParam, String stateParam, int ncli, int nfrag,
			ClientEmulationMaster master, String controlKey) {

		StateTransition s = null;

		logger.info("Starting emulator #" + ncli);

		try {
			e = (Emulation) Class.forName(emParam).newInstance();
			s = (StateTransition) Class.forName(stateParam).newInstance();

			int temp = (ncli + ((nfrag - 1) * TPCConst.getNumMinClients()));

			e.initialize();
			e.setEmulationId(temp);
			e.setEmulationName(this.getEmulationName() + "-" + temp);
			e.setStateTransition(s);
			e.setDatabase(this.getDatabase());
			e.setHostId(this.getHostId());

			e.setFinished(this.isFinished());
			e.setTraceInformation(this.getTraceInformation());
			e.setNumberConcurrentEmulators(this.getNumberConcurrentEmulators());
			e.setStatusThinkTime(this.getStatusThinkTime());
			e.setStatusReSubmit(this.getStatusReSubmit());
			e.setDatabase(this.getDatabase());
			e.setEmulationName(this.getEmulationName());
			e.setHostId(this.getHostId());
			e.setScheduler(ses);

			this.master = master;
			this.controlKey = controlKey;

		} catch (java.lang.Exception ex) {
			logger.fatal("Unexpected error. Something bad happend.");
			ex.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	public void pause() {
		e.pause();
	}

	public void resume() {
		e.resume();
	}

	public void stopit() {
		e.stopit();
		master.notifyThreadsCompletion(controlKey);
	}

	public void setCompletion(boolean fin) {
		setFinished(fin);
		e.setFinished(fin);
	}

	public void start() {
		e.process();
	}
}

