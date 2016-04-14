package laraifox.foxtail.core;

import java.util.Arrays;

public class ArrayUtils {
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];
		for (int i = 0; i < data.length; i++)
			result[i] = data[i].intValue();
		return result;
	}
	
	public static <T> T[] concat(T[] a, T[] b) {
		T[] result = Arrays.copyOf(a, a.length + b.length);
		System.arraycopy(b, 0, result, a.length, b.length);

		return result;
	}

	public static <T> T[] concatAll(T[] a, T[]... r) {
		int totalLength = a.length;
		for (T[] array : r) {
			totalLength += array.length;
		}

		T[] result = Arrays.copyOf(a, totalLength);
		int offset = a.length;
		for (T[] array : r) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}

		return result;
	}
}
