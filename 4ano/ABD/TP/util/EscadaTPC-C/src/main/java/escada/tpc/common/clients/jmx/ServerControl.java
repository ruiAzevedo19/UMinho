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

package escada.tpc.common.clients.jmx;

import escada.tpc.common.clients.ClientEmulation;
import escada.tpc.common.clients.jmx.ClientEmulationStartupMBean.Stage;
import org.apache.log4j.Logger;

import java.util.*;

public class ServerControl {

	private final static Logger logger = Logger.getLogger(ServerControl.class);

	private final Hashtable<String, Stage> clientsStage = new Hashtable<String, Stage>();

	private final Hashtable<String, Vector<ClientEmulation>> clientsEmulation = new Hashtable<String, Vector<ClientEmulation>>();

	private final Hashtable<String, HashSet<String>> serverClients = new Hashtable<String, HashSet<String>>();

	public Stage getClientStage(String key) {
		return (clientsStage.get(key));
	}

	public void setClientStage(String key, Stage stage) {
		clientsStage.put(key, stage);
	}

	public void setClientEmulations(String key, Vector<ClientEmulation> ebs) {
		this.clientsEmulation.put(key, ebs);
	}

	public Vector<ClientEmulation> getClientEmulations(String key) {
		return (this.clientsEmulation.get(key));
	}

	public void removeClientStage(String key) {
		this.clientsStage.remove(key);
	}

	public void removeClientEmulations(String key) {
		this.clientsEmulation.remove(key);
	}

	public void attachClientToServer(String client, String machine) {
		HashSet<String> s = this.serverClients.get(machine);
		s.add(client);
	}

	public void detachClientToServer(String client, String machine) {
		HashSet<String> s = this.serverClients.get(machine);
		s.remove(client);
	}

	public HashSet<String> getClients() {
		HashSet<String> ret = new HashSet<String>();
		ret.addAll(this.clientsEmulation.keySet());
		return (ret);
	}

	public void addServer(String key) throws InvalidTransactionException {
		if (!this.serverClients.containsKey(key)) {
			this.serverClients.put(key, new HashSet<String>());
		} else {
			throw new InvalidTransactionException("Error adding server " + key);
		}
	}

	public void removeServer(String key) throws InvalidTransactionException {
		if (this.serverClients.containsKey(key)) {
			this.serverClients.remove(key);
		} else {
			throw new InvalidTransactionException("Error removing server "
					+ key);
		}
	}

	public void clearServers()
    {
		this.serverClients.clear();
	}

	public int getNumberOfClients(String key)
	throws InvalidTransactionException {
		int ret = 0;
		if (key == null || key.equals("*")) {
			Iterator it = ((Hashtable) clientsEmulation.clone()).keySet()
			.iterator();
			while (it.hasNext()) {
				String keyValue = (String) it.next();
				Vector clients = (Vector) clientsEmulation.get(keyValue);
				if (clients != null) {
					ret = ret + clients.size();
				}
			}
		} else {
			Vector clients = clientsEmulation.get(key);
			if (clients != null) {
				ret = ret + clients.size();
			} else {
				throw new InvalidTransactionException(
						"Error getting number of clients for " + key);
			}
		}
		return (ret);
	}

	public int getNumberOfClientsOnServer(String key)
	throws InvalidTransactionException {
		int ret = 0;

		if (key == null || key.equals("*")) {

			Iterator itServers = ((Hashtable) this.serverClients.clone())
			.keySet().iterator();

			while (itServers.hasNext()) {
				String keyValue = (String) itServers.next();

				HashSet server = serverClients.get(keyValue);
				if (server != null) {
					Iterator itClients = ((HashSet) server.clone()).iterator();
					while (itClients.hasNext()) {
						keyValue = (String) itClients.next();
						Vector clients = this.clientsEmulation.get(keyValue);
						ret = ret + (clients != null ? clients.size() : 0);
					}
				}
			}
		} else {
			HashSet<String> servers = serverClients.get(key);
			if (servers != null) {
				Iterator itClients = ((HashSet) servers.clone()).iterator();
				while (itClients.hasNext()) {
					String keyValue = (String) itClients.next();
					Vector clients = this.clientsEmulation.get(keyValue);
					ret = ret + (clients != null ? clients.size() : 0);
				}
			} else {
				throw new InvalidTransactionException(
						"Error getting number of clients for " + key);
			}
		}
		return (ret);
	}

	public String findServerClient(String key) {
		String ret = null;

		HashSet<String> servers = serverClients.get(key);
		if (servers != null) {
			Iterator itClients = ((HashSet) servers.clone()).iterator();
			while (itClients.hasNext()) {
				String keyValue = (String) itClients.next();
				Vector clients = this.clientsEmulation.get(keyValue);
				Stage stg = this.clientsStage.get(keyValue);
				if (clients != null && stg != null && stg.equals(Stage.RUNNING)) {
					ret = keyValue;
					break;
				}
			}
		}
		return (ret);
	}

	public HashSet<String> getServers() throws InvalidTransactionException {
		return (new HashSet<String>(this.serverClients.keySet()));
	}

	public void pauseClient(String key) throws InvalidTransactionException {
		if (key == null || key.equals("*")) {

			Iterator it = ((Hashtable) clientsEmulation.clone()).keySet()
			.iterator();
			while (it.hasNext()) {
				String keyValue = (String) it.next();

				Stage stg = this.clientsStage.get(keyValue);

				if (stg != null && stg.equals(Stage.RUNNING)) {

					this.clientsStage.put(keyValue, Stage.PAUSED);

					Vector<ClientEmulation> clients = clientsEmulation
					.get(keyValue);
					if (clients != null) {
						for (ClientEmulation e : clients) {
							e.pause();
						}
					}
				}
			}
		} else {

			Stage stg = this.clientsStage.get(key);
			if (stg != null && stg.equals(Stage.RUNNING)) {

				this.clientsStage.put(key, Stage.PAUSED);

				Vector<ClientEmulation> clients = clientsEmulation.get(key);
				if (clients != null) {
					for (ClientEmulation e : clients) {
						e.pause();
					}
				}
			} else {
				throw new InvalidTransactionException(key + " pause on "
						+ this.clientsStage.get(key));
			}
		}
	}

	public void resumeClient(String key) throws InvalidTransactionException {
		if (key == null || key.equals("*")) {
			Iterator it = ((Hashtable) clientsEmulation.clone()).keySet()
			.iterator();
			while (it.hasNext()) {
				String keyValue = (String) it.next();

				Stage stg = this.clientsStage.get(keyValue);
				if (stg != null && stg.equals(Stage.PAUSED)) {

					this.clientsStage.put(keyValue, Stage.RUNNING);

					Vector<ClientEmulation> clients = clientsEmulation
					.get(keyValue);
					if (clients != null) {
						for (ClientEmulation e : clients) {
							e.resume();
						}
					}
				}
			}
		} else {
			Stage stg = this.clientsStage.get(key);
			if (stg != null && stg.equals(Stage.PAUSED)) {

				this.clientsStage.put(key, Stage.RUNNING);

				Vector<ClientEmulation> clients = clientsEmulation.get(key);
				if (clients != null) {
					for (ClientEmulation e : clients) {
						e.resume();
					}
				}
			} else {
				throw new InvalidTransactionException(key + " resume on " + stg);
			}
		}
	}

	public void stopClient(String key) throws InvalidTransactionException {
		if (key == null || key.equals("*")) {
			Iterator it = ((Hashtable) clientsEmulation.clone()).keySet()
			.iterator();
			while (it.hasNext()) {
				String keyValue = (String) it.next();
				Stage stg = this.clientsStage.get(keyValue);
				if (stg != null
						&& (stg.equals(Stage.RUNNING) || stg
								.equals(Stage.PAUSED))) {

					Vector<ClientEmulation> clients = clientsEmulation
					.get(keyValue);
					if (clients != null) {
						for (ClientEmulation e : clients) {
							e.stopit();
						}
					}

					this.clientsEmulation.remove(keyValue);
					this.clientsStage.remove(keyValue);

                    this.detachClientToServer(keyValue,this.findServerClient(keyValue));
				}
			}
		} else {
			Stage stg = this.clientsStage.get(key);
			if (stg != null
					&& (stg.equals(Stage.RUNNING) || stg.equals(Stage.PAUSED))) {

				Vector<ClientEmulation> clients = clientsEmulation.get(key);
				if (clients != null) {
					for (ClientEmulation e : clients) {
						e.stopit();
					}
				}

				this.clientsEmulation.remove(key);
				this.clientsStage.remove(key);
			} else {
				throw new InvalidTransactionException(key + " stop on " + stg);
			}
		}
	}

	public void stopFirstClient() throws InvalidTransactionException {
		if (this.clientsStage.size()>0)
		{
			String clients=this.clientsStage.keySet().iterator().next();
			logger.info("Stopping clients:"+clients);
			this.stopClient(clients);
		}
		else
			logger.info("No clients to stop");
		
	}
}
