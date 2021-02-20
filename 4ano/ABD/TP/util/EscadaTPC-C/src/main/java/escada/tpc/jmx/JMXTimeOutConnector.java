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

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.log4j.Logger;

public class JMXTimeOutConnector {

	private static final Logger logger = Logger
			.getLogger(JMXTimeOutConnector.class);

	private static ExecutorService executor = Executors.newCachedThreadPool();

	public static Object closeWithTimeout(final JMXConnector jmxc,
			long timeout, TimeUnit unit) {

		final Future<Object> future = executor.submit(new Callable<Object>() {
			public Object call() throws ExecutionException {
				try {
					jmxc.close();

				} catch (IOException e) {
					throw new ExecutionException(e);
				}
				return null;
			}
		});

		return (wait(future, "close",timeout, unit));
	}

	public static Object invokeWithTimeout(final JMXConnector jmxc,
			final String url, final String method, long timeout, TimeUnit unit) {

		final Future<Object> future = executor.submit(new Callable<Object>() {
			public Object call() throws ExecutionException {
				Object res = null;
				try {
					MBeanServerConnection mbsc = jmxc
							.getMBeanServerConnection();
					Set<ObjectName> beans;
					beans = mbsc.queryNames(new ObjectName(url), null);
					ObjectName bean = beans.iterator().next();
					res = mbsc.invoke(bean, method, null, null);

				} catch (Exception e) {
					throw new ExecutionException(e);
				}
				return res;
			}
		});

		return (wait(future,"invoke", timeout, unit));
	}

	public static JMXConnector connectWithTimeout(final JMXServiceURL url,
			final HashMap<String, Object> environment, long timeout,
			TimeUnit unit) {

		final Future future = executor.submit(new Callable<JMXConnector>() {
			public JMXConnector call() throws ExecutionException {
				JMXConnector res = null;
				try {
					res = JMXConnectorFactory.connect(url, environment);
				} catch (IOException e) {
					throw new ExecutionException(e);
				}
				return res;
			}
		});

		return ((JMXConnector) wait(future, "connection", timeout, unit));
	}

	private static Object wait(Future future, String from, long timeout, TimeUnit unit) {
		Object res = null;
		try {
			res = future.get(timeout, unit);
		} catch (InterruptedException e) {
			logger.debug("Unavailable due to interrupted execution (" + from +")." );
		} catch (ExecutionException e) {
			logger.debug("Unavailable due to exception execution (" + from +").");
		} catch (TimeoutException e) {
			logger.debug("Unavailable due to timeout execution (" + from +").");
		}

		// if the timeout happened and no
		if (future.isDone() == false && future.isCancelled() == false) {
			future.cancel(true);
		}

		return res;
	}
}
