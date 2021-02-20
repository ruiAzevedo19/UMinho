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

package escada.tpc.jmx;

import java.lang.management.ManagementFactory;

import javax.management.ObjectName;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import escada.tpc.common.PerformanceCounters;
import escada.tpc.common.clients.jmx.ClientEmulationStartup;
import escada.tpc.tpcc.database.populate.jmx.DatabasePopulate;

public class JmxStarter {
	
	private static final Logger logger= Logger.getLogger(JmxStarter.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {			
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			
            DOMConfigurator.configure(
            		factory.newDocumentBuilder().parse(
            					JmxStarter.class.getResourceAsStream("/log4j.xml"))
            		.getDocumentElement());
            
		} catch (Exception e) {
			logger.warn("Unable to initialize log4j. Continuing with default init.", e);
			if(logger.isDebugEnabled()) {
				logger.debug(e.getLocalizedMessage(), e);
			}
		}
		
		new MBeansRegister().run();
	}
	
	static class MBeansRegister implements Runnable {
		public void run() {
			
			try {
				
				DatabasePopulate dbPopulate = new DatabasePopulate();
				ClientEmulationStartup ces = new ClientEmulationStartup();
				PerformanceCounters counters = PerformanceCounters.getReference();
				
				if(logger.isInfoEnabled()) {
					logger.info("Registering Client MBean!");
				}
				
				ObjectName name = new ObjectName("escada.tpc.common.clients.jmx:type=ClientControl");
				ManagementFactory.getPlatformMBeanServer().registerMBean(ces, name);
				
				name = new ObjectName("escada.tpc.common.clients.jmx:type=ClientEmulationDatabaseProperties");
				ManagementFactory.getPlatformMBeanServer().registerMBean(ces.getDatabaseResources(), name);
				
				name = new ObjectName("escada.tpc.common.clients.jmx:type=ClientEmulationWorkloadProperties");
				ManagementFactory.getPlatformMBeanServer().registerMBean(ces.getWorkloadResources(), name);


				if(logger.isInfoEnabled()) {
					logger.info("Registering Performance MBean!");
				}
				
				name = new ObjectName("escada.tpc.common.clients.jmx:type=ClientPerformance");
				ManagementFactory.getPlatformMBeanServer().registerMBean(counters, name);
				
				if(logger.isInfoEnabled()) {
					logger.info("Registering Populate MBean!");
				}
				
				// Populate

				name = new ObjectName("escada.tpc.tpcc.database.populate.jmx:type=PopulateControl");
				ManagementFactory.getPlatformMBeanServer().registerMBean(dbPopulate, name);
				
				name = new ObjectName("escada.tpc.tpcc.database.populate.jmx:type=PopulateDatabaseProperties");
				ManagementFactory.getPlatformMBeanServer().registerMBean(dbPopulate.getDatabaseResources(), name);
				
				name = new ObjectName("escada.tpc.tpcc.database.populate.jmx:type=PopulateWorkloadProperties");
				ManagementFactory.getPlatformMBeanServer().registerMBean(dbPopulate.getWorkloadResources(), name);

				if(logger.isInfoEnabled()) {
					logger.info("Started jmx server.");
				}

				synchronized (this) {
				
					while (true) {
						wait();
					}
				}
			} catch (Exception e) {
				logger.fatal("Unable to register DatabasePopulateMBean!",e);
			}
		}
	}
}
