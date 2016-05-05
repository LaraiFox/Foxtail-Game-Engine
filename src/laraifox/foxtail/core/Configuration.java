package laraifox.foxtail.core;

import java.util.HashMap;

public class Configuration {
	private static HashMap<String, String> configMap = new HashMap<String, String>();

	//	static {
	//		for (EnumConfigKey key : EnumConfigKey.values()) {
	//			configMap.put(key, key.getDefaultValue());
	//		}
	//	}

	private Configuration() {
	}

	public static void initialize() {

	}

	public static String getValue(String key) {
		if (configMap.get(key) == null) {
			throw new RuntimeException("Unknown configuration key requested! '" + key + "'");
		}

		return configMap.get(key);
	}

	public static void setValue(String key, String value) {
		configMap.put(key, value);
	}

	public static boolean getBoolean(String key) {
		return Boolean.valueOf(Configuration.getValue(key));
	}

	public static byte getByte(String key) {
		return Byte.valueOf(Configuration.getValue(key));
	}

	public static short getShort(String key) {
		return Short.valueOf(Configuration.getValue(key));
	}

	public static int getInteger(String key) {
		return Integer.valueOf(Configuration.getValue(key));
	}

	public static long getLong(String key) {
		return Long.valueOf(Configuration.getValue(key));
	}

	public static float getFloat(String key) {
		return Float.valueOf(Configuration.getValue(key));
	}

	public static double getDouble(String key) {
		return Double.valueOf(Configuration.getValue(key));
	}
}
