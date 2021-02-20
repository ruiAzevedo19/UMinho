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

package escada.tpc.common.util;

import java.util.Random;

public class RandGen {

	/**
	 * It defines a non-uniform distribution according to the specification of
	 * the TPC.
	 * 
	 * @param rand
	 *            the random generator
	 * @param A
	 *            it is a constant defined according to the range of x and y
	 * @param x
	 *            it is the first value of the possible range, including it
	 * @param y
	 *            it is the final value of the possible range, including it
	 * @return the calculated non-uniform number
	 */
	public static final int NURand(Random rand, int A, int x, int y) {
		return ((((nextInt(rand, A + 1)) | (nextInt(rand, y - x + 1) + x)) % (y
				- x + 1)) + x);
	}

	/**
	 * The exponetial distribution used by the TPC, for instance to calulate the
	 * transaction's thinktime.
	 * 
	 * @param rand
	 *            the random generator
	 * @param min
	 *            the minimum number which could be accepted for this
	 *            distribution
	 * @param max
	 *            the maximum number which could be accept for this distribution
	 * @param lMin
	 *            the minimum number which could be accept for the following
	 *            execution rand.nextDouble
	 * @param lMax
	 *            the maximum number which could be accept for the following
	 *            execution rand.nexDouble
	 * @param mu
	 *            the base value provided to calculate the exponetial number.
	 *            For instance, it could be the mean thinktime
	 * @return the caluclated exponetial number
	 */
	public static final long negExp(Random rand, long min, double lMin,
			long max, double lMax, double mu) {
		double r = rand.nextDouble();

		if (r < lMax) {
			return (max);
		}
		return ((long) (-mu * Math.log(r)));

	}

	/**
	 * It return a uniform random number, from 0 to (range - 1).
	 * 
	 * @param rand
	 *            the random generator
	 * @param int
	 *            the range
	 * @return the uniform random number
	 */
	public static int nextInt(Random rand, int range) {
		int i = Math.abs(rand.nextInt());
		return (i % (range));
	}

	/**
	 * It return a uniform random number, from inirange to (endrange - 1).
	 * 
	 * @param rand
	 *            the random generator
	 * @param inirange
	 *            the start of the range
	 * @param endrange
	 *            the end of the range
	 * @return the uniform random number
	 */
	public static int nextInt(Random rand, int inirange, int endrange) {
		return (inirange + nextInt(rand, endrange - inirange));
	}
}
