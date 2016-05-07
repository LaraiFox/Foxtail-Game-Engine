package laraifox.foxtail.core.math;

import java.text.DecimalFormat;

/**
 * An array of 4 floating point numbers representing a 2x2 matrix in column major order. Representation of how the matrix is numbered is below.<br>
 *		[ 0, 2 ]<br>
 *		[ 1, 3 ]<br>
 * 
 * @author Larai Fox
 *
 */
public class Matrix2f {
	public static final int WIDTH = 2;
	public static final int HEIGHT = 2;

	public static final int COMPONENT_COUNT = WIDTH * HEIGHT;

	public static final int BYTE_COUNT = COMPONENT_COUNT * Float.BYTES;

	private float[] data;

	public Matrix2f() {
		this.data = new float[COMPONENT_COUNT];
	}

	public Matrix2f(float[] values) {
		this.data = new float[COMPONENT_COUNT];
		for (int i = 0; i < Matrix2f.COMPONENT_COUNT; i++) {
			this.data[i] = values[i];
		}
	}

	public Matrix2f(Matrix2f matrix) {
		this.data = new float[COMPONENT_COUNT];
		for (int i = 0; i < Matrix2f.COMPONENT_COUNT; i++) {
			this.data[i] = matrix.data[i];
		}
	}

	public Matrix2f(org.lwjgl.util.vector.Matrix2f matrix) {
		this.set(new float[] {
				matrix.m00, matrix.m01, //
				matrix.m10, matrix.m11
		});
	}

	public static Matrix2f multiply(Matrix2f left, Matrix2f right) {
		return new Matrix2f(left).multiply(right);
	}

	public Vector2f multiply(Vector2f vector) {
		Vector2f result = new Vector2f();

		result.setX(data[0] * vector.getX() + data[4] * vector.getY());
		result.setY(data[1] * vector.getX() + data[5] * vector.getY());

		return result;
	}

	public Matrix2f multiply(Matrix2f matrix) {
		float[] result = new float[COMPONENT_COUNT];
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				result[j + i * HEIGHT] = data[j + 0 * HEIGHT] * matrix.data[0 + i * HEIGHT] + data[j + 1 * HEIGHT] * matrix.data[1 + i * HEIGHT];
			}
		}

		return this.set(result);
	}

	private Matrix2f set(float[] values) {
		this.data = values;

		return this;
	}

	public Matrix2f inverse() {
//		float[] result = new float[COMPONENT_COUNT];
//
//		result[0] = data[5] * data[10] * data[15] - data[5] * data[11] * data[14] - //
//			data[9] * data[6] * data[15] + data[9] * data[7] * data[14] +//
//			data[13] * data[6] * data[11] - data[13] * data[7] * data[10];
//
//		result[1] = -data[1] * data[10] * data[15] + data[1] * data[11] * data[14] + //
//			data[9] * data[2] * data[15] - data[9] * data[3] * data[14] - //
//			data[13] * data[2] * data[11] + data[13] * data[3] * data[10];
//
//		result[2] = data[1] * data[6] * data[15] - data[1] * data[7] * data[14] - //
//			data[5] * data[2] * data[15] + data[5] * data[3] * data[14] + //
//			data[13] * data[2] * data[7] - data[13] * data[3] * data[6];
//
//		result[3] = -data[1] * data[6] * data[11] + data[1] * data[7] * data[10] + //
//			data[5] * data[2] * data[11] - data[5] * data[3] * data[10] - //
//			data[9] * data[2] * data[7] + data[9] * data[3] * data[6];
//
//		result[4] = -data[4] * data[10] * data[15] + data[4] * data[11] * data[14] + //
//			data[8] * data[6] * data[15] - data[8] * data[7] * data[14] - //
//			data[12] * data[6] * data[11] + data[12] * data[7] * data[10];
//
//		result[5] = data[0] * data[10] * data[15] - data[0] * data[11] * data[14] - //
//			data[8] * data[2] * data[15] + data[8] * data[3] * data[14] + //
//			data[12] * data[2] * data[11] - data[12] * data[3] * data[10];
//
//		result[6] = -data[0] * data[6] * data[15] + data[0] * data[7] * data[14] + //
//			data[4] * data[2] * data[15] - data[4] * data[3] * data[14] - //
//			data[12] * data[2] * data[7] + data[12] * data[3] * data[6];
//
//		result[7] = data[0] * data[6] * data[11] - data[0] * data[7] * data[10] - //
//			data[4] * data[2] * data[11] + data[4] * data[3] * data[10] + //
//			data[8] * data[2] * data[7] - data[8] * data[3] * data[6];
//
//		result[8] = data[4] * data[9] * data[15] - data[4] * data[11] * data[13] - //
//			data[8] * data[5] * data[15] + data[8] * data[7] * data[13] + //
//			data[12] * data[5] * data[11] - data[12] * data[7] * data[9];
//
//		result[9] = -data[0] * data[9] * data[15] + data[0] * data[11] * data[13] + //
//			data[8] * data[1] * data[15] - data[8] * data[3] * data[13] - //
//			data[12] * data[1] * data[11] + data[12] * data[3] * data[9];
//
//		result[10] = data[0] * data[5] * data[15] - data[0] * data[7] * data[13] - //
//			data[4] * data[1] * data[15] + data[4] * data[3] * data[13] + //
//			data[12] * data[1] * data[7] - data[12] * data[3] * data[5];
//
//		result[11] = -data[0] * data[5] * data[11] + data[0] * data[7] * data[9] + //
//			data[4] * data[1] * data[11] - data[4] * data[3] * data[9] - //
//			data[8] * data[1] * data[7] + data[8] * data[3] * data[5];
//
//		result[12] = -data[4] * data[9] * data[14] + data[4] * data[10] * data[13] +//
//			data[8] * data[5] * data[14] - data[8] * data[6] * data[13] - //
//			data[12] * data[5] * data[10] + data[12] * data[6] * data[9];
//
//		result[13] = data[0] * data[9] * data[14] - data[0] * data[10] * data[13] - //
//			data[8] * data[1] * data[14] + data[8] * data[2] * data[13] + //
//			data[12] * data[1] * data[10] - data[12] * data[2] * data[9];
//
//		result[14] = -data[0] * data[5] * data[14] + data[0] * data[6] * data[13] + //
//			data[4] * data[1] * data[14] - data[4] * data[2] * data[13] - //
//			data[12] * data[1] * data[6] + data[12] * data[2] * data[5];
//
//		result[15] = data[0] * data[5] * data[10] - data[0] * data[6] * data[9] - //
//			data[4] * data[1] * data[10] + data[4] * data[2] * data[9] + //
//			data[8] * data[1] * data[6] - data[8] * data[2] * data[5];
//
//		float determinant = data[0] * result[0] + data[1] * result[4] + data[2] * result[8] + data[3] * result[12];
//		if (determinant == 0)
//			return this;
//
//		determinant = 1.0f / determinant;
//
//		for (int i = 0; i < COMPONENT_COUNT; i++) {
//			data[i] = result[i] * determinant;
//		}

		return this;
	}

	public Matrix2f transpose() {
		float temp = data[1];
		data[1] = data[2];
		data[2] = temp;
		return this;
	}

	public Matrix2f rotate(float angle) {
		return this.multiply(Matrix2f.Rotation(angle));
	}

	public Matrix2f scale(float x, float y) {
		return this.multiply(Matrix2f.Scale(x, y));
	}

	@Override
	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean newlineAfterEachRow) {
		StringBuilder builder = new StringBuilder();

		if (newlineAfterEachRow) {
			builder.append("[ ").append(data[0]).append(", ").append(data[2]).append(" ],\n");
			builder.append("[ ").append(data[1]).append(", ").append(data[3]).append(" ];\n");
		} else {
			builder.append("[ ").append(data[0]).append(", ").append(data[2]).append(" ], ");
			builder.append("[ ").append(data[1]).append(", ").append(data[3]).append(" ];");
		}

		return builder.toString();
	}

	public String toFormattedString() {
		return this.toFormattedString(4, true);
	}

	public String toFormattedString(int precision) {
		return this.toFormattedString(precision, true);
	}

	public String toFormattedString(boolean newlineAfterEachRow) {
		return this.toFormattedString(4, newlineAfterEachRow);
	}

	public String toFormattedString(int precision, boolean newlineAfterEachRow) {
		DecimalFormat formatter = new DecimalFormat(String.format("%1$-" + (precision + 2) + "s", "0.").replace(' ', '0'));

		String[] strings = new String[] {
				formatter.format(data[0]), formatter.format(data[2]),//
				formatter.format(data[1]), formatter.format(data[3])
		};

		StringBuilder builder = new StringBuilder(46 + (3 + precision) * COMPONENT_COUNT);

		if (newlineAfterEachRow) {
			int longestStringLength = 3 + precision;
			for (String string : strings) {
				longestStringLength = Math.max(longestStringLength, string.length());
			}

			int lineWidth = 2 + longestStringLength;

			builder.append(String.format("%1$-" + (lineWidth - strings[0].length()) + "s", "[ "));
			builder.append(strings[0]).append(String.format("%1$-" + (lineWidth - strings[1].length()) + "s", ", "));
			builder.append(strings[1]).append(" ],\n");
			builder.append(String.format("%1$-" + (lineWidth - strings[2].length()) + "s", "["));
			builder.append(strings[2]).append(String.format("%1$-" + (lineWidth - strings[3].length()) + "s", ","));
			builder.append(strings[3]).append(" ];\n");
		} else {
			builder.append("[ ").append(strings[0]).append(", ").append(strings[2]).append(" ], ");
			builder.append("[ ").append(strings[1]).append(", ").append(strings[3]).append(" ]; ");
		}

		return builder.toString();
	}

	public float[] getData() {
		return this.data;
	}

	public float getData(int i) {
		return this.data[i];
	}

	public float getData(int row, int column) {
		return this.data[row + column * HEIGHT];
	}

	public Vector2f getRow(int row) {
		return new Vector2f(data[row + 0 * HEIGHT], data[row + 1 * HEIGHT]);
	}

	public Vector2f getColumn(int column) {
		return new Vector2f(data[0 + column * HEIGHT], data[1 + column * HEIGHT]);
	}

	public Matrix2f setData(float[] values) {
		this.data = values;
		return this;
	}

	public Matrix2f setData(int i, float value) {
		this.data[i] = value;
		return this;
	}

	public Matrix2f setData(int row, int column, float value) {
		this.data[row + column * HEIGHT] = value;
		return this;
	}

	public Matrix2f setRow(int i, Vector2f value) {
		this.data[i + 0 * HEIGHT] = value.getX();
		this.data[i + 1 * HEIGHT] = value.getY();
		return this;
	}

	public Matrix2f setColumn(int i, Vector2f value) {
		this.data[0 + i * HEIGHT] = value.getX();
		this.data[1 + i * HEIGHT] = value.getY();
		return this;
	}

	public static Matrix2f Identity() {
		Matrix2f result = new Matrix2f();

		result.data[0] = 1;
		result.data[1] = 0;

		result.data[2] = 0;
		result.data[3] = 1;

		return result;
	}

	public static Matrix2f Rotation(float angle) {
		Matrix2f result = new Matrix2f();

		result.data[0] = (float) Math.cos(angle);
		result.data[1] = (float) Math.sin(angle);

		result.data[2] = (float) -Math.sin(angle);
		result.data[3] = (float) Math.cos(angle);
		
		return result;
	}

	public static Matrix2f Scale(float x, float y) {
		Matrix2f result = new Matrix2f();

		result.data[0] = x;
		result.data[1] = 0;

		result.data[2] = 0;
		result.data[3] = y;

		return result;
	}
}
