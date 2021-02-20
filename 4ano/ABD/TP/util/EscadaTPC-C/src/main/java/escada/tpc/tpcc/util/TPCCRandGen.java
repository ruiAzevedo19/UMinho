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

package escada.tpc.tpcc.util;

/**
 * It defines important functions used to generate random strings as specified
 * in the TPC-C benchmark.
 */
public class TPCCRandGen {

	/**
	 * It defines a set of words that are combined to produce strings used in
	 * the TPC-C
	 */
	private static final String[] digS = { "BAR", "OUGHT", "ABLE", "PRI",
			"PRES", "ESE", "ANTI", "CALLY", "ATION", "EING" };

	public static String digSyl(int d, int n, int l) {
		StringBuffer s = new StringBuffer();
		int length = l - countLength(d);

		s.append(Integer.toString(d));
		for (; length > 0; length--) {
			s.append("0");
		}

		d = Integer.parseInt(s.toString());
		s = new StringBuffer();

		for (; n > 0; n--) {
			int c = d % 10;
			s.append(digS[c]);
			d = d / 10;
		}

		return (s.toString());
	}

	public static String digSyl(int d, int n) {
		return (digSyl(d, ((n == 0) ? countLength(d) : n), countLength(d)));
	}

	public static String digSyl(int d) {
		return (digSyl(d, 3, 3));
	}

	public static int countLength(int d) {
		int c = 0;

		for (; d > 0; d = d / 10, c++)
			;

		return (c);
	}
}

