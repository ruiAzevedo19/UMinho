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

import java.util.HashSet;

public interface ClientEmulationStartupMBean {
	public enum Stage {
		INIT, PAUSED, RUNNING, STOPPED, FAILOVER
	};

    public void startClients(String prefix, String connectionString,int clients,int frag,int hostId, boolean exit)
            throws InvalidTransactionException ;

	public void pause(String key) throws InvalidTransactionException;

	public void resume(String key) throws InvalidTransactionException;

	public void stop(String key) throws InvalidTransactionException;
	
	public void stop() throws InvalidTransactionException;
	
	public void addServer(String key) throws InvalidTransactionException;
	
	public void removeServer(String key) throws InvalidTransactionException;
	
	public HashSet<String> getServers() throws InvalidTransactionException;
	
	public HashSet<String> getClients() throws InvalidTransactionException;
	
	public int getNumberOfClients(String key) throws InvalidTransactionException;
	
	public int getNumberOfClients() throws InvalidTransactionException;
	
	public int getNumberOfClientsOnServer(String key) throws InvalidTransactionException;
	
	public int getNumberOfClientsOnServer() throws InvalidTransactionException;
	
	public void kill();
}
