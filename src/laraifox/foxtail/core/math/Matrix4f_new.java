package laraifox.foxtail.core.math;

import java.text.DecimalFormat;

public class Matrix4f_new {
	public static final int WIDTH = 4;
	public static final int HEIGHT = 4;

	public static final int COMPONENT_COUNT = WIDTH * HEIGHT;

	public static final int BYTE_COUNT = COMPONENT_COUNT * Float.BYTES;

	// TODO: Matrix seems to use the wrong coordinate system specifically for the getDataAt and setDataAt functions. (x and y coords are reversed)!!!
	private float[] data;
	private float[][] data_old;

	public Matrix4f_new() {
		this.data = new float[COMPONENT_COUNT];
	}

	public Matrix4f_new(float[] values) {
		this.data = new float[COMPONENT_COUNT];
		for (int i = 0; i < Matrix4f.COMPONENT_COUNT; i++) {
			this.data[i] = values[i];
		}
	}

	public Matrix4f_new(Matrix4f matrix) {
		this.data = new float[COMPONENT_COUNT];
		for (int i = 0; i < Matrix4f.COMPONENT_COUNT; i++) {
			this.data[i] = matrix.data[i];
		}
	}

	public static Matrix4f multiply(Matrix4f left, Matrix4f right) {
		float[] result = new float[COMPONENT_COUNT];
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				result[j + i * HEIGHT] = left.data[j + 0 * HEIGHT] * right.data[0 + i * HEIGHT] + left.data[j + 1 * HEIGHT] * right.data[1 + i * HEIGHT] //
					+ left.data[j + 2 * HEIGHT] * right.data[2 + i * HEIGHT] + left.data[j + 3 * HEIGHT] * right.data[3 + i * HEIGHT];
			}
		}

		return new Matrix4f(result);
	}

	public Vector4f multiply(Vector3f vector, float w) {
		Vector4f result = new Vector4f();

		result.setX(data[0] * vector.getX() + data[4] * vector.getY() + data[8] * vector.getZ() + data[12] * w);
		result.setY(data[1] * vector.getX() + data[5] * vector.getY() + data[9] * vector.getZ() + data[13] * w);
		result.setZ(data[2] * vector.getX() + data[6] * vector.getY() + data[10] * vector.getZ() + data[14] * w);
		result.setW(data[3] * vector.getX() + data[7] * vector.getY() + data[11] * vector.getZ() + data[15] * w);

		return result;
	}

	public Vector4f multiply(Vector4f vector) {
		Vector4f result = new Vector4f();

		result.setX(data[0] * vector.getX() + data[4] * vector.getY() + data[8] * vector.getZ() + data[12] * vector.getW());
		result.setY(data[1] * vector.getX() + data[5] * vector.getY() + data[9] * vector.getZ() + data[13] * vector.getW());
		result.setZ(data[2] * vector.getX() + data[6] * vector.getY() + data[10] * vector.getZ() + data[14] * vector.getW());
		result.setW(data[3] * vector.getX() + data[7] * vector.getY() + data[11] * vector.getZ() + data[15] * vector.getW());

		return result;
	}

	public Matrix4f multiply(Matrix4f matrix) {
		float[] result = new float[COMPONENT_COUNT];
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				result[j + i * HEIGHT] = data[j + 0 * HEIGHT] * matrix.data[0 + i * HEIGHT] + data[j + 1 * HEIGHT] * matrix.data[1 + i * HEIGHT] //
					+ data[j + 2 * HEIGHT] * matrix.data[2 + i * HEIGHT] + data[j + 3 * HEIGHT] * matrix.data[3 + i * HEIGHT];
			}
		}

		return this.set(result);
	}

	private Matrix4f set(float[] values) {
		this.data = values;

		return this;
	}

	public Matrix4f scale(float scalar) {
		for (int i = 0; i < COMPONENT_COUNT; i++) {
			this.data[i] *= scalar;
		}

		return this;
	}

	public Matrix4f inverse() {
		float[] result = new float[COMPONENT_COUNT];

		result[0] = data[5] * data[10] * data[15] - data[5] * data[11] * data[14] - //
			data[9] * data[6] * data[15] + data[9] * data[7] * data[14] +//
			data[13] * data[6] * data[11] - data[13] * data[7] * data[10];

		result[1] = -data[1] * data[10] * data[15] + data[1] * data[11] * data[14] + //
			data[9] * data[2] * data[15] - data[9] * data[3] * data[14] - //
			data[13] * data[2] * data[11] + data[13] * data[3] * data[10];

		result[2] = data[1] * data[6] * data[15] - data[1] * data[7] * data[14] - //
			data[5] * data[2] * data[15] + data[5] * data[3] * data[14] + //
			data[13] * data[2] * data[7] - data[13] * data[3] * data[6];

		result[3] = -data[1] * data[6] * data[11] + data[1] * data[7] * data[10] + //
			data[5] * data[2] * data[11] - data[5] * data[3] * data[10] - //
			data[9] * data[2] * data[7] + data[9] * data[3] * data[6];

		result[4] = -data[4] * data[10] * data[15] + data[4] * data[11] * data[14] + //
			data[8] * data[6] * data[15] - data[8] * data[7] * data[14] - //
			data[12] * data[6] * data[11] + data[12] * data[7] * data[10];

		result[5] = data[0] * data[10] * data[15] - data[0] * data[11] * data[14] - //
			data[8] * data[2] * data[15] + data[8] * data[3] * data[14] + //
			data[12] * data[2] * data[11] - data[12] * data[3] * data[10];

		result[6] = -data[0] * data[6] * data[15] + data[0] * data[7] * data[14] + //
			data[4] * data[2] * data[15] - data[4] * data[3] * data[14] - //
			data[12] * data[2] * data[7] + data[12] * data[3] * data[6];

		result[7] = data[0] * data[6] * data[11] - data[0] * data[7] * data[10] - //
			data[4] * data[2] * data[11] + data[4] * data[3] * data[10] + //
			data[8] * data[2] * data[7] - data[8] * data[3] * data[6];

		result[8] = data[4] * data[9] * data[15] - data[4] * data[11] * data[13] - //
			data[8] * data[5] * data[15] + data[8] * data[7] * data[13] + //
			data[12] * data[5] * data[11] - data[12] * data[7] * data[9];

		result[9] = -data[0] * data[9] * data[15] + data[0] * data[11] * data[13] + //
			data[8] * data[1] * data[15] - data[8] * data[3] * data[13] - //
			data[12] * data[1] * data[11] + data[12] * data[3] * data[9];

		result[10] = data[0] * data[5] * data[15] - data[0] * data[7] * data[13] - //
			data[4] * data[1] * data[15] + data[4] * data[3] * data[13] + //
			data[12] * data[1] * data[7] - data[12] * data[3] * data[5];

		result[11] = -data[0] * data[5] * data[11] + data[0] * data[7] * data[9] + //
			data[4] * data[1] * data[11] - data[4] * data[3] * data[9] - //
			data[8] * data[1] * data[7] + data[8] * data[3] * data[5];

		result[12] = -data[4] * data[9] * data[14] + data[4] * data[10] * data[13] +//
			data[8] * data[5] * data[14] - data[8] * data[6] * data[13] - //
			data[12] * data[5] * data[10] + data[12] * data[6] * data[9];

		result[13] = data[0] * data[9] * data[14] - data[0] * data[10] * data[13] - //
			data[8] * data[1] * data[14] + data[8] * data[2] * data[13] + //
			data[12] * data[1] * data[10] - data[12] * data[2] * data[9];

		result[14] = -data[0] * data[5] * data[14] + data[0] * data[6] * data[13] + //
			data[4] * data[1] * data[14] - data[4] * data[2] * data[13] - //
			data[12] * data[1] * data[6] + data[12] * data[2] * data[5];

		result[15] = data[0] * data[5] * data[10] - data[0] * data[6] * data[9] - //
			data[4] * data[1] * data[10] + data[4] * data[2] * data[9] + //
			data[8] * data[1] * data[6] - data[8] * data[2] * data[5];

		float determinant = data[0] * result[0] + data[1] * result[4] + data[2] * result[8] + data[3] * result[12];
		if (determinant == 0)
			return this;

		determinant = 1.0f / determinant;

		for (int i = 0; i < COMPONENT_COUNT; i++) {
			data[i] = result[i] * determinant;
		}

		return this;

		//		float determinant = data[0] * data[5] * data[10] * data[15] + data[0] * data[6] * data[11] * data[13] + data[0] * data[7] * data[9] * data[14] //
		//			+ data[1] * data[4] * data[11] * data[14] + data[1] * data[6] * data[8] * data[15] + data[1] * data[7] * data[10] * data[12] //
		//			+ data[2] * data[4] * data[9] * data[15] + data[2] * data[5] * data[11] * data[12] + data[2] * data[7] * data[8] * data[13] //
		//			+ data[3] * data[4] * data[10] * data[13] + data[3] * data[5] * data[8] * data[14] + data[3] * data[6] * data[9] * data[12] //
		//			- data[0] * data[5] * data[11] * data[14] - data[0] * data[6] * data[9] * data[15] - data[0] * data[7] * data[10] * data[13] //
		//			- data[1] * data[4] * data[6] * data[11] - data[1] * data[6] * data[11] * data[12] - data[1] * data[7] * data[12] * data[14] //
		//			- data[2] * data[4] * data[11] * data[13] - data[2] * data[5] * data[8] * data[15] - data[2] * data[7] * data[9] * data[12] //
		//			- data[3] * data[4] * data[9] * data[14] - data[3] * data[5] * data[10] * data[12] - data[3] * data[6] * data[8] * data[13];
		//		if (determinant == 0) {
		//			return this;
		//		}
		//
		//		float[] result = new float[] {
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//				data[0] * data[0] * data[0] + data[0] * data[0] * data[0] + data[0] * data[0] * data[0] - data[0] * data[0] * data[0] - data[0] * data[0] * data[0], //
		//		};
		//
		//		return this;
	}

	public Matrix4f transpose() {
		float temp = data[1];
		data[1] = data[4];
		data[4] = temp;
		temp = data[2];
		data[2] = data[8];
		data[8] = temp;
		temp = data[3];
		data[3] = data[12];
		data[12] = temp;
		temp = data[6];
		data[6] = data[9];
		data[9] = temp;
		temp = data[7];
		data[7] = data[13];
		data[13] = temp;
		temp = data[11];
		data[11] = data[14];
		data[14] = temp;
		return this;
	}

	public Matrix4f translate() {

		return this;
	}

	public Matrix4f rotate() {

		return this;
	}

	public Matrix4f scale() {

		return this;
	}

	@Override
	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean newlineAfterEachRow) {
		StringBuilder builder = new StringBuilder();

		if (newlineAfterEachRow) {
			builder.append("[ ").append(data[0]).append(", ").append(data[1]).append(", ").append(data[2]).append(", ").append(data[3]).append(" ],\n");
			builder.append("[ ").append(data[4]).append(", ").append(data[5]).append(", ").append(data[6]).append(", ").append(data[7]).append(" ],\n");
			builder.append("[ ").append(data[8]).append(", ").append(data[9]).append(", ").append(data[10]).append(", ").append(data[11]).append(" ],\n");
			builder.append("[ ").append(data[12]).append(", ").append(data[13]).append(", ").append(data[14]).append(", ").append(data[15]).append(" ];");
		} else {
			builder.append("[ ").append(data[0]).append(", ").append(data[1]).append(", ").append(data[2]).append(", ").append(data[3]).append(" ], ");
			builder.append("[ ").append(data[4]).append(", ").append(data[5]).append(", ").append(data[6]).append(", ").append(data[7]).append(" ], ");
			builder.append("[ ").append(data[8]).append(", ").append(data[9]).append(", ").append(data[10]).append(", ").append(data[11]).append(" ], ");
			builder.append("[ ").append(data[12]).append(", ").append(data[13]).append(", ").append(data[14]).append(", ").append(data[15]).append(" ];");
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
				formatter.format(data[0]), formatter.format(data[1]), formatter.format(data[2]), formatter.format(data[3]),//
				formatter.format(data[4]), formatter.format(data[5]), formatter.format(data[6]), formatter.format(data[7]),//
				formatter.format(data[8]), formatter.format(data[9]), formatter.format(data[10]), formatter.format(data[11]),//
				formatter.format(data[12]), formatter.format(data[13]), formatter.format(data[14]), formatter.format(data[15])
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
			builder.append(strings[1]).append(String.format("%1$-" + (lineWidth - strings[2].length()) + "s", ", "));
			builder.append(strings[2]).append(String.format("%1$-" + (lineWidth - strings[3].length()) + "s", ", "));
			builder.append(strings[3]).append(" ],\n");
			builder.append(String.format("%1$-" + (lineWidth - strings[4].length()) + "s", "["));
			builder.append(strings[4]).append(String.format("%1$-" + (lineWidth - strings[5].length()) + "s", ","));
			builder.append(strings[5]).append(String.format("%1$-" + (lineWidth - strings[6].length()) + "s", ","));
			builder.append(strings[6]).append(String.format("%1$-" + (lineWidth - strings[7].length()) + "s", ","));
			builder.append(strings[7]).append(" ],\n");
			builder.append(String.format("%1$-" + (lineWidth - strings[8].length()) + "s", "["));
			builder.append(strings[8]).append(String.format("%1$-" + (lineWidth - strings[9].length()) + "s", ","));
			builder.append(strings[9]).append(String.format("%1$-" + (lineWidth - strings[10].length()) + "s", ","));
			builder.append(strings[10]).append(String.format("%1$-" + (lineWidth - strings[11].length()) + "s", ","));
			builder.append(strings[11]).append(" ],\n");
			builder.append(String.format("%1$-" + (lineWidth - strings[12].length()) + "s", "["));
			builder.append(strings[12]).append(String.format("%1$-" + (lineWidth - strings[13].length()) + "s", ","));
			builder.append(strings[13]).append(String.format("%1$-" + (lineWidth - strings[14].length()) + "s", ","));
			builder.append(strings[14]).append(String.format("%1$-" + (lineWidth - strings[15].length()) + "s", ","));
			builder.append(strings[15]).append(" ];\n");
		} else {
			builder.append("[ ").append(strings[0]).append(", ").append(strings[1]).append(", ").append(strings[2]).append(", ").append(strings[3]).append(" ], ");
			builder.append("[ ").append(strings[4]).append(", ").append(strings[5]).append(", ").append(strings[6]).append(", ").append(strings[7]).append(" ], ");
			builder.append("[ ").append(strings[8]).append(", ").append(strings[9]).append(", ").append(strings[10]).append(", ").append(strings[11]).append(" ], ");
			builder.append("[ ").append(strings[12]).append(", ").append(strings[13]).append(", ").append(strings[14]).append(", ").append(strings[15]).append(" ]; ");
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

	public Vector4f getRow(int row) {
		return new Vector4f(data[row + 0 * HEIGHT], data[row + 1 * HEIGHT], data[row + 2 * HEIGHT], data[row + 3 * HEIGHT]);
	}

	public Vector4f getColumn(int column) {
		return new Vector4f(data[0 + column * HEIGHT], data[1 + column * HEIGHT], data[2 + column * HEIGHT], data[3 + column * HEIGHT]);
	}

	public Matrix4f setData(float[] values) {
		this.data = values;
		return this;
	}

	public Matrix4f setData(int i, float value) {
		this.data[i] = value;
		return this;
	}

	public Matrix4f setData(int row, int column, float value) {
		this.data[row + column * HEIGHT] = value;
		return this;
	}

	public Matrix4f setRow(int i, Vector4f value) {
		this.data[i + 0 * HEIGHT] = value.getX();
		this.data[i + 1 * HEIGHT] = value.getY();
		this.data[i + 2 * HEIGHT] = value.getZ();
		this.data[i + 3 * HEIGHT] = value.getW();
		return this;
	}

	public Matrix4f setColumn(int i, Vector4f value) {
		this.data[0 + i * HEIGHT] = value.getX();
		this.data[1 + i * HEIGHT] = value.getY();
		this.data[2 + i * HEIGHT] = value.getZ();
		this.data[3 + i * HEIGHT] = value.getW();
		return this;
	}

	public static Matrix4f Identity() {
		Matrix4f result = new Matrix4f();

		result.data[0] = 1;
		result.data[1] = 0;
		result.data[2] = 0;
		result.data[3] = 0;

		result.data[4] = 0;
		result.data[5] = 1;
		result.data[6] = 0;
		result.data[7] = 0;

		result.data[8] = 0;
		result.data[9] = 0;
		result.data[10] = 1;
		result.data[11] = 0;

		result.data[12] = 0;
		result.data[13] = 0;
		result.data[14] = 0;
		result.data[15] = 1;

		return result;
	}

	public static Matrix4f Translation(Vector3f translation) {
		return Matrix4f.Translation(translation.getX(), translation.getY(), translation.getZ());
	}

	public static Matrix4f Translation(float x, float y, float z) {
		Matrix4f result = new Matrix4f();

		result.data[0] = 1;
		result.data[1] = 0;
		result.data[2] = 0;
		result.data[3] = 0;

		result.data[4] = 0;
		result.data[5] = 1;
		result.data[6] = 0;
		result.data[7] = 0;

		result.data[8] = 0;
		result.data[9] = 0;
		result.data[10] = 1;
		result.data[11] = 0;

		result.data[12] = x;
		result.data[13] = y;
		result.data[14] = z;
		result.data[15] = 1;

		return result;
	}

	public static Matrix4f Rotation(float x, float y, float z) {
		Matrix4f result = new Matrix4f();

		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();

		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);

		// Rotation around the Z axis
		rz.data[0] = (float) Math.cos(z);
		rz.data[1] = (float) Math.sin(z);
		rz.data[2] = 0;
		rz.data[3] = 0;

		rz.data[4] = (float) -Math.sin(z);
		rz.data[5] = (float) Math.cos(z);
		rz.data[6] = 0;
		rz.data[7] = 0;

		rz.data[8] = 0;
		rz.data[9] = 0;
		rz.data[10] = 1;
		rz.data[11] = 0;

		rz.data[12] = 0;
		rz.data[13] = 0;
		rz.data[14] = 0;
		rz.data[15] = 1;

		// Rotation around the X axis
		rx.data[0] = 1;
		rx.data[1] = 0;
		rx.data[2] = 0;
		rx.data[3] = 0;

		rx.data[4] = 0;
		rx.data[5] = (float) Math.cos(x);
		rx.data[6] = (float) Math.sin(x);
		rx.data[7] = 0;

		rx.data[8] = 0;
		rx.data[9] = (float) -Math.sin(x);
		rx.data[10] = (float) Math.cos(x);
		rx.data[11] = 0;

		rx.data[12] = 0;
		rx.data[13] = 0;
		rx.data[14] = 0;
		rx.data[15] = 1;

		// Rotation around the Y axis
		ry.data[0] = (float) Math.cos(y);
		ry.data[1] = 0;
		ry.data[2] = (float) Math.sin(y);
		ry.data[3] = 0;

		ry.data[4] = 0;
		ry.data[5] = 1;
		ry.data[6] = 0;
		ry.data[7] = 0;

		ry.data[8] = (float) -Math.sin(y);
		ry.data[9] = 0;
		ry.data[10] = (float) Math.cos(y);
		ry.data[11] = 0;

		ry.data[12] = 0;
		ry.data[13] = 0;
		ry.data[14] = 0;
		ry.data[15] = 1;

		result = rz.multiply(ry.multiply(rx));

		return result;
	}

	public static Matrix4f Rotation(Vector3f forward, Vector3f upward) {
		Vector3f zAxis = Vector3f.normalize(forward);
		Vector3f xAxis = Vector3f.normalize(upward).cross(zAxis);
		Vector3f yAxis = Vector3f.cross(zAxis, xAxis).normalize();

		return Matrix4f.Rotation(zAxis, yAxis, xAxis);
	}

	public static Matrix4f Rotation(Vector3f forward, Vector3f upward, Vector3f right) {
		Matrix4f result = new Matrix4f();

		Vector3f xAxis = Vector3f.normalize(right);
		Vector3f yAxis = Vector3f.normalize(upward);
		Vector3f zAxis = Vector3f.normalize(forward);

		result.data[0] = xAxis.getX();
		result.data[1] = yAxis.getX();
		result.data[2] = zAxis.getX();
		result.data[3] = 0;

		result.data[4] = xAxis.getY();
		result.data[5] = yAxis.getY();
		result.data[6] = zAxis.getY();
		result.data[7] = 0;

		result.data[8] = xAxis.getZ();
		result.data[9] = yAxis.getZ();
		result.data[10] = zAxis.getZ();
		result.data[11] = 0;

		result.data[12] = 0;
		result.data[13] = 0;
		result.data[14] = 0;
		result.data[15] = 1;

		return result;
	}

	public static Matrix4f ViewRotation(Vector3f forward, Vector3f upward, Vector3f right) {
		Matrix4f result = new Matrix4f();

		Vector3f xAxis = Vector3f.normalize(right);
		Vector3f yAxis = Vector3f.normalize(upward);
		Vector3f zAxis = Vector3f.normalize(forward);

		result.data[0] = xAxis.getX();
		result.data[1] = xAxis.getY();
		result.data[2] = xAxis.getZ();
		result.data[3] = 0;

		result.data[4] = yAxis.getX();
		result.data[5] = yAxis.getY();
		result.data[6] = yAxis.getZ();
		result.data[7] = 0;

		result.data[8] = zAxis.getX();
		result.data[9] = zAxis.getY();
		result.data[10] = zAxis.getZ();
		result.data[11] = 0;

		result.data[12] = 0;
		result.data[13] = 0;
		result.data[14] = 0;
		result.data[15] = 1;

		return result;
	}

	public static Matrix4f Scale(float x, float y, float z) {
		Matrix4f result = new Matrix4f();

		result.data[0] = x;
		result.data[1] = 0;
		result.data[2] = 0;
		result.data[3] = 0;

		result.data[4] = 0;
		result.data[5] = y;
		result.data[6] = 0;
		result.data[7] = 0;

		result.data[8] = 0;
		result.data[9] = 0;
		result.data[10] = z;
		result.data[11] = 0;

		result.data[12] = 0;
		result.data[13] = 0;
		result.data[14] = 0;
		result.data[15] = 1;

		return result;
	}

	public static Matrix4f Projection(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f result = new Matrix4f();

		float xRange = right - left;
		float yRange = top - bottom;
		float zRange = far - near;

		result.data[0] = 2.0f / xRange;
		result.data[1] = 0;
		result.data[2] = 0;
		result.data[3] = -((right + left) / xRange);

		result.data[4] = 0;
		result.data[5] = 2.0f / yRange;
		result.data[6] = 0;
		result.data[7] = -((top + bottom) / yRange);

		result.data[8] = 0;
		result.data[9] = 0;
		result.data[10] = -2.0f / zRange;
		result.data[11] = -((far + near) / zRange);

		result.data[12] = 0;
		result.data[13] = 0;
		result.data[14] = 0;
		result.data[15] = 1;

		return result;
	}

	public static Matrix4f Projection(float fov, float width, float height, float zNear, float zFar) {
		Matrix4f result = new Matrix4f();

		float aspect = width / height;
		float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2.0f));
		float zRange = zNear - zFar;

		result.data[0] = 1.0f / (tanHalfFOV * aspect);
		result.data[1] = 0;
		result.data[2] = 0;
		result.data[3] = 0;

		result.data[4] = 0;
		result.data[5] = 1.0f / tanHalfFOV;
		result.data[6] = 0;
		result.data[7] = 0;

		result.data[8] = 0;
		result.data[9] = 0;
		result.data[10] = (-zNear - zFar) / zRange;
		result.data[11] = 1;

		result.data[12] = 0;
		result.data[13] = 0;
		result.data[14] = (2.0f * zFar * zNear) / zRange;
		result.data[15] = 0;

		//		 float aspect = width / height;
		//		 float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2.0f));
		//		 float zRange = zNear - zFar;
		//		
		//		 result.data[0][0] = 1.0f / (tanHalfFOV * aspect);
		//		 result.data[0][1] = 0;
		//		 result.data[0][2] = 0;
		//		 result.data[0][3] = 0;
		//		
		//		 result.data[1][0] = 0;
		//		 result.data[1][1] = 1.0f / tanHalfFOV;
		//		 result.data[1][2] = 0;
		//		 result.data[1][3] = 0;
		//		
		//		 result.data[2][0] = 0;
		//		 result.data[2][1] = 0;
		//		 result.data[2][2] = (-zNear - zFar) / zRange;
		//		 result.data[2][3] = 1;
		//		
		//		 result.data[3][0] = 0;
		//		 result.data[3][1] = 0;
		//		 result.data[3][2] = (2.0f * zFar * zNear) / zRange;
		//		 result.data[3][3] = 0;

		return result;
	}
}
