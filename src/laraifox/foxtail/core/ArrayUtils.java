package laraifox.foxtail.core;

import java.util.List;

public class ArrayUtils {
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];
		for (int i = 0; i < data.length; i++)
			result[i] = data[i].intValue();
		return result;
	}

	public static int[] listToIntArray(List<Integer> list) {
		int[] array = new int[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	public static float[] listToFloatArray(List<Float> list) {
		float[] array = new float[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
