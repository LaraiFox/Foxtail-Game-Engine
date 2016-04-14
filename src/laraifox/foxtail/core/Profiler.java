package laraifox.foxtail.core;

import java.util.HashMap;
import java.util.Map;

public class Profiler {
	private static class ProfilerSample {
		private Long startTime, endTime;

		public ProfilerSample() {
			this.startTime = System.nanoTime();
		}

		public long getLength() {
			return this.endTime - this.startTime;
		}

		public void setStartTime() {
			this.startTime = System.nanoTime();
		}

		public void setEndTime() {
			this.endTime = System.nanoTime();
		}
	}

	public static final String DEFAULT_LOG_FILE = new String("/logs/default.log");

	private static final Map<String, ProfilerSample> SAMPLE_MAP = new HashMap<String, ProfilerSample>();

	private static String logFile = new String(DEFAULT_LOG_FILE);

	public static void beginSample(String name) {
		ProfilerSample sample = SAMPLE_MAP.get(name);
		if (sample == null) {
			sample = new ProfilerSample();
			SAMPLE_MAP.put(name, sample);
		} else {
			sample.setStartTime();
		}
	}

	public static void endSample(String name) {
		ProfilerSample sample = SAMPLE_MAP.get(name);
		if (sample != null) {
			sample.setEndTime();
		}
	}

	public static long getSampleLength(String name) {
		return SAMPLE_MAP.get(name).getLength();
	}
}
