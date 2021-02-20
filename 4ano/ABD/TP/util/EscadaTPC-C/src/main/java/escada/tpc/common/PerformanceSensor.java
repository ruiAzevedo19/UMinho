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

public interface PerformanceSensor {
	
	public static final long DEFAULT_REFRESH_INTERVAL = 10000L;
	
	public static final long MINIMUM_REFRESH_INTERVAL = 1000L;
		
	public static final float MINIMUM_VALUE = 0.05F;

	public long getPerformanceRefreshInterval();

	public void setPerformanceRefreshInterval(long refreshInterval);
	
}
