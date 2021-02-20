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

import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Emulation extends EmulationConfiguration implements PausableEmulation {
	/**
	 * It sets up the emulator.
	 * 
	 */
	public abstract void initialize();

	/**
	 * It returns the calculated thinktime, according to the properties of the
	 * benchmark, using the value defined by the setThinkTime and retrieved with
	 * the getKeyingTime.
	 * 
	 * @return the thinktime
	 * @see getThinkTime,setThinkTime
	 */
	public abstract long thinkTime();

	/**
	 * It returns the calculated keyingtime, according to the properties of the
	 * benchmark, using the value defined by the setKeyingTime and retrieved
	 * with the getKeyingTime.
	 * 
	 * @see getKeyingTime,setKeyingTime
	 */
	public abstract long keyingTime();

	/**
	 * It proceeds with the emulation according to the host to which it belongs
	 * and based on the benchmark's properties.
	 * 
	 * @see run,processIncrement
	 */
	public abstract void process();
}
