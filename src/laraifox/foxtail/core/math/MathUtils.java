package laraifox.foxtail.core.math;

public class MathUtils {
	public static final double DOUBLE_EPSILON = 2.22e-16;
	public static final float FLOAT_EPSILON = 1.19e-07f;
	public static final long MILLIARD = 1000000000L;
	public static final double TAU = 6.283185307179586;

	public static int clamp(int value, int min, int max) {
		if (max < min)
			return value;

		if (value < min)
			value = min;
		else if (value > max)
			value = max;

		return value;
	}

	public static long clamp(long value, long min, long max) {
		if (max < min)
			return value;

		if (value < min)
			value = min;
		else if (value > max)
			value = max;

		return value;
	}

	public static float clamp(float value, float min, float max) {
		if (max < min)
			return value;

		if (value < min)
			value = min;
		else if (value > max)
			value = max;

		return value;
	}

	public static double clamp(double value, double min, double max) {
		if (max < min)
			return value;

		if (value < min)
			value = min;
		else if (value > max)
			value = max;

		return value;
	}

	public static int lerp(int a, int b, float value) {
		return (int) (a + (b - a) * value);
	}

	public static long lerp(long a, long b, float value) {
		return (long) (a + (b - a) * value);
	}

	public static float lerp(float a, float b, float value) {
		return a + (b - a) * value;
	}

	public static double lerp(double a, double b, double value) {
		return a + (b - a) * value;
	}

	public static int max(int... values) {
		if (values.length < 2) {
			return values[0];
		}

		int result = Math.max(values[0], values[1]);
		for (int i = 2; i < values.length; i++) {
			result = Math.max(result, values[i]);
		}

		return result;
	}

	public static long max(long... values) {
		if (values.length < 2) {
			return values[0];
		}

		long result = Math.max(values[0], values[1]);
		for (int i = 2; i < values.length; i++) {
			result = Math.max(result, values[i]);
		}

		return result;
	}

	public static float max(float... values) {
		if (values.length < 2) {
			return values[0];
		}

		float result = Math.max(values[0], values[1]);
		for (int i = 2; i < values.length; i++) {
			result = Math.max(result, values[i]);
		}

		return result;
	}

	public static double max(double... values) {
		if (values.length < 2) {
			return values[0];
		}

		double result = Math.max(values[0], values[1]);
		for (int i = 2; i < values.length; i++) {
			result = Math.max(result, values[i]);
		}

		return result;
	}

	public static int min(int... values) {
		if (values.length < 2) {
			return values[0];
		}

		int result = Math.min(values[0], values[1]);
		for (int i = 2; i < values.length; i++) {
			result = Math.min(result, values[i]);
		}

		return result;
	}

	public static long min(long... values) {
		if (values.length < 2) {
			return values[0];
		}

		long result = Math.min(values[0], values[1]);
		for (int i = 2; i < values.length; i++) {
			result = Math.min(result, values[i]);
		}

		return result;
	}

	public static float min(float... values) {
		if (values.length < 2) {
			return values[0];
		}

		float result = Math.min(values[0], values[1]);
		for (int i = 2; i < values.length; i++) {
			result = Math.min(result, values[i]);
		}

		return result;
	}

	public static double min(double... values) {
		if (values.length < 2) {
			return values[0];
		}

		double result = Math.min(values[0], values[1]);
		for (int i = 2; i < values.length; i++) {
			result = Math.min(result, values[i]);
		}

		return result;
	}

	public static int nextPowerOfTwo(int i) {
		i -= 1;
		i |= (i >> 1);
		i |= (i >> 2);
		i |= (i >> 4);
		i |= (i >> 8);
		i |= (i >> 16);

		return i + 1;
	}

	public static long nextPowerOfTwo(long i) {
		i -= 1;
		i |= (i >> 1);
		i |= (i >> 2);
		i |= (i >> 4);
		i |= (i >> 8);
		i |= (i >> 16);
		i |= (i >> 32);

		return i + 1;
	}
}
