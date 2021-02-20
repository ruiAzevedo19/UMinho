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

public class PerformanceCounters implements PerformanceCountersMBean {

	private long performanceRefreshInterval = DEFAULT_REFRESH_INTERVAL;

	private float inCommingRate = 0F;

	private float commitRate = 0F;

	private float abortRate = 0F;

	private double latencyRate=0F;
	
	private int inCommingCounter = 0;

	private int abortCounter = 0;

	private int commitCounter = 0;
    private int totalNewOrderCommitCounter =0;

	private long lastComputationInComming, lastComputationAbort, lastComputationCommit = 0, lastComputationLatency=0;
    private long firstNewOrderCommit =-1;
	private int totalAbortCounter =0;
	private int totalCommitCounter=0;
	private double latencyAccumulator = 0;

    private PerformanceCounters() {
	}

	public synchronized float getAbortRate() {
		long current = System.currentTimeMillis();
		long diff = current - lastComputationAbort;
		float t = abortRate;

		if (diff > performanceRefreshInterval && diff > 0) {
			t = ((float) abortCounter / (float) (diff)) * 1000;
			t = (t < MINIMUM_VALUE ? 0 : t);
			lastComputationAbort = current;
			abortCounter = 0;
		}
		abortRate = t;

		return (abortRate);
	}

	public synchronized float getCommitRate() {
		long current = System.currentTimeMillis();
		long diff = current - lastComputationCommit;
		float t = commitRate;

		if (diff > performanceRefreshInterval && diff > 0) {
			t = ((float) commitCounter / (float) (diff)) * 1000;
			t = (t < MINIMUM_VALUE ? 0 : t);
			lastComputationCommit = current;
			commitCounter = 0;
		}
		commitRate = t;

		return (commitRate);
	}

    public synchronized float getTotalNewOrderCommitRate() {
        long current = System.currentTimeMillis();
        long diff = current - firstNewOrderCommit;
        float t = ((float) totalNewOrderCommitCounter / (float) (diff)) * 1000 * 60;
        t = (t < MINIMUM_VALUE ? 0 : t);
        return (t);
    }

	public synchronized float getTotalAbortRate() {
            return ((float) totalAbortCounter*1.0f)/( (float) totalAbortCounter+totalCommitCounter);
	
    }


	public synchronized float getIncommingRate() {
		long current = System.currentTimeMillis();
		long diff = current - lastComputationInComming;
		float t = inCommingRate;

		if (diff > performanceRefreshInterval && diff > 0) {
			t = ((float) inCommingCounter / (float) (diff)) * 1000;
			t = (t < MINIMUM_VALUE ? 0 : t);
			lastComputationInComming = current;
			inCommingCounter = 0;
		}
		inCommingRate = t;

		return (inCommingRate);
	}
	
	public synchronized double getAverageLatency() {
		
		long current = System.currentTimeMillis();
		long diff = current - lastComputationLatency;
		double t = this.latencyRate;

		if (diff > performanceRefreshInterval && diff > 0) {
			if(this.latencyCounter > 0) {
				t = ((double)this.latencyAccumulator) / ((double)this.latencyCounter);
				t = (t < MINIMUM_VALUE ? 0 : t);
			} else {
				t = 0.0;
			}

			this.lastComputationLatency = current;
			this.latencyCounter = 0;
			this.latencyAccumulator = 0;			
		}
		latencyRate = t;
		
		return this.latencyRate;
	}

	public static synchronized void setIncommingRate() {
		if (reference != null) {
			reference.inCommingCounter++;
		}
	}

	public static synchronized void setAbortRate() {
		if (reference != null) {
			reference.abortCounter++;
			reference.totalAbortCounter++;
		}
	}

	public static synchronized void setCommitRate() {
		if (reference != null) {
			reference.commitCounter++;
			reference.totalCommitCounter++;
		}
	}

    public static void setTPMC() {
        if (reference != null) {
            if (reference.firstNewOrderCommit <0)
            {
                reference.firstNewOrderCommit =System.currentTimeMillis();
            }
            reference.totalNewOrderCommitCounter++;
        }
    }

	public static PerformanceCounters getReference() {
		if (reference == null) {
			reference = new PerformanceCounters();
		}
		return (reference);
	}
		
	private double latencyCounter = 0;
	public static synchronized void setLatency(double latency) {
		if (reference != null) {
			reference.latencyAccumulator += latency;
			reference.latencyCounter++;
		}
	}

	private static PerformanceCounters reference;

	public long getPerformanceRefreshInterval() {
		return this.performanceRefreshInterval;
	}

	public void setPerformanceRefreshInterval(long refreshInterval) {
		this.performanceRefreshInterval = refreshInterval;
	}

}
