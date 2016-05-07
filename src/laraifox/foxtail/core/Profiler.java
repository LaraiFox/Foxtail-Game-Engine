package laraifox.foxtail.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profiler {
	private static class ProfilerSample {
		private ArrayList<Long> startTime, endTime;
		private int samples;

		public ProfilerSample() {
			this.startTime = new ArrayList<Long>();
			this.endTime = new ArrayList<Long>();
			this.samples = 0;

			this.addSample();
		}

		public void addSample() {
			startTime.add(System.nanoTime());
			endTime.add(System.nanoTime());
			samples++;
		}

		public long getLength() {
			long total = 0;
			for (int i = 0; i < samples; i++) {
				total += endTime.get(i) - startTime.get(i);
			}

			return total / (samples);
		}

		public void setStartTime() {
			startTime.set(samples - 1, System.nanoTime());
		}

		public void setEndTime() {
			endTime.set(samples - 1, System.nanoTime());
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

	public static void beginMultiSample(String name) {
		ProfilerSample sample = SAMPLE_MAP.get(name);
		if (sample == null) {
			sample = new ProfilerSample();
			SAMPLE_MAP.put(name, sample);
		} else {
			sample.addSample();
		}
	}

	public static void endSample(String name) {
		ProfilerSample sample = SAMPLE_MAP.get(name);
		if (sample != null) {
			sample.setEndTime();
		}
	}

	public static void logSamples() {
		List<String> sampleList = new ArrayList<String>(SAMPLE_MAP.keySet());
		Collections.sort(sampleList);
		for (String sampleName : sampleList) {
			ProfilerSample sample = SAMPLE_MAP.get(sampleName);

			Logger.log("<" + sampleName + "> " + (int) (sample.getLength() / 10000.0f) / 100.0f + " ms", "Profiler", Logger.MESSAGE_LEVEL_DEBUG);
		}

		SAMPLE_MAP.clear();

		Logger.lineBreak(Logger.MESSAGE_LEVEL_DEBUG);
	}

	public static long getSampleLength(String name) {
		return SAMPLE_MAP.get(name).getLength();
	}
}
