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

/**
 * It is used as a state transition manager, which means that it controls from
 * the current step which is the next step to be executed during the simulation.
 * 
 */
public abstract class StateTransition {
	protected StateObject curTrans = null;

	protected int curState = 0;

	protected int oldState = 0;

	/**
	 * It loads the states that compose the transitions of the simulation. Using
	 * an objetct, the user can define the appropriate class acording to its
	 * needs and problems.
	 * 
	 * @param Object
	 *            used to load the simulation's state.
	 */
	abstract public void loadStates(Object obj);

	/**
	 * It returns the simulation's current state. Using an object, we can define
	 * the sates according to our needs and problems. The current implementation
	 * uses the class StateObject in order to define a simulation's state.
	 * 
	 * @return the simulation's state
	 * @see getCurrentState
	 */
	public StateObject getCurrentStateObject() {
		return (curTrans);
	}

	/**
	 * It returns the number of the simulation's current state.
	 * 
	 * @return the number of the simulation's current state
	 */
	public int getCurrentState() {
		return (curState);
	}

	/**
	 * It returns the number last simulation's state.
	 * 
	 * @return the number of the simulation's current state
	 */
	public int getOldState() {
		return (oldState);
	}

	/**
	 * It returns the next simulation's state.
	 * 
	 * @return the next simulation's state
	 */
	abstract public StateObject nextState();
}

