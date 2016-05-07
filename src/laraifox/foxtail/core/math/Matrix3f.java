package laraifox.foxtail.core.math;

import java.text.DecimalFormat;

/**
 * An array of 9 floating point numbers representing a 3x3 matrix in column major order. Representation of how the matrix is numbered is below.<br>
 *		[ 0, 3, 6 ]<br>
 *		[ 1, 4, 7 ]<br>
 *		[ 2, 5, 8 ]<br>
 * 
 * @author Larai Fox
 *
 */
public class Matrix3f {
	public static final int WIDTH = 3;
	public static final int HEIGHT = 3;

	public static final int COMPONENT_COUNT = WIDTH * HEIGHT;

	public static final int BYTE_COUNT = COMPONENT_COUNT * Float.BYTES;

	private float[] data;

	public Matrix3f() {
		this.data = new float[COMPONENT_COUNT];
	}

	public Matrix3f(float[] values) {
		this.data = new float[COMPONENT_COUNT];
		for (int i = 0; i < Matrix3f.COMPONENT_COUNT; i++) {
			this.data[i] = values[i];
		}
	}

	public Matrix3f(Matrix3f matrix) {
		this.data = new float[COMPONENT_COUNT];
		for (int i = 0; i < Matrix3f.COMPONENT_COUNT; i++) {
			this.data[i] = matrix.data[i];
		}
	}

	public Matrix3f(org.lwjgl.util.vector.Matrix3f matrix) {
		this.set(new float[] {
				matrix.m00, matrix.m01, matrix.m02, //
				matrix.m10, matrix.m11, matrix.m12, //	
				matrix.m20, matrix.m21, matrix.m22
		});
	}

	public static Matrix3f multiply(Matrix3f left, Matrix3f right) {
		return new Matrix3f(left).multiply(right);
	}

	public Vector4f multiply(Vector2f vector, float z) {
		Vector4f result = new Vector4f();

		result.setX(data[0] * vector.getX() + data[3] * vector.getY() + data[6] * z);
		result.setY(data[1] * vector.getX() + data[4] * vector.getY() + data[7] * z);
		result.setZ(data[2] * vector.getX() + data[5] * vector.getY() + data[8] * z);

		return result;
	}

	public Vector4f multiply(Vector3f vector) {
		Vector4f result = new Vector4f();

		result.setX(data[0] * vector.getX() + data[3] * vector.getY() + data[6] * vector.getZ());
		result.setY(data[1] * vector.getX() + data[4] * vector.getY() + data[7] * vector.getZ());
		result.setZ(data[2] * vector.getX() + data[5] * vector.getY() + data[8] * vector.getZ());

		return result;
	}

	public Matrix3f multiply(Matrix3f matrix) {
		float[] result = new float[COMPONENT_COUNT];
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				result[j + i * HEIGHT] = data[j + 0 * HEIGHT] * matrix.data[0 + i * HEIGHT] + data[j + 1 * HEIGHT] * matrix.data[1 + i * HEIGHT] //
					+ data[j + 2 * HEIGHT] * matrix.data[2 + i * HEIGHT];
			}
		}

		return this.set(result);
	}

	private Matrix3f set(float[] values) {
		this.data = values;

		return this;
	}

	public Matrix3f inverse() {
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

	public Matrix3f transpose() {
		float temp = data[1];
		data[1] = data[3];
		data[3] = temp;
		temp = data[2];
		data[2] = data[6];
		data[6] = temp;
		temp = data[5];
		data[5] = data[7];
		data[7] = temp;
		return this;
	}

	public Matrix3f translate(float x, float y, float z) {
		return this.multiply(Matrix3f.Translation(x, y));
	}

	public Matrix3f rotate(Quaternion quaternion) {
		return this.multiply(Matrix3f.Rotation(quaternion));
	}

	public Matrix3f rotate(float angle) {
		return this.multiply(Matrix3f.Rotation(angle));
	}

	public Matrix3f scale(float x, float y) {
		return this.multiply(Matrix3f.Scale(x, y));
	}

	@Override
	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean newlineAfterEachRow) {
		StringBuilder builder = new StringBuilder();

		if (newlineAfterEachRow) {
			builder.append("[ ").append(data[0]).append(", ").append(data[3]).append(", ").append(data[6]).append(" ],\n");
			builder.append("[ ").append(data[1]).append(", ").append(data[4]).append(", ").append(data[7]).append(" ],\n");
			builder.append("[ ").append(data[2]).append(", ").append(data[5]).append(", ").append(data[8]).append(" ];\n");
		} else {
			builder.append("[ ").append(data[0]).append(", ").append(data[3]).append(", ").append(data[6]).append(" ], ");
			builder.append("[ ").append(data[1]).append(", ").append(data[4]).append(", ").append(data[7]).append(" ], ");
			builder.append("[ ").append(data[2]).append(", ").append(data[5]).append(", ").append(data[8]).append(" ]; ");
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
				formatter.format(data[0]), formatter.format(data[3]), formatter.format(data[6]),//
				formatter.format(data[1]), formatter.format(data[4]), formatter.format(data[7]),//
				formatter.format(data[2]), formatter.format(data[5]), formatter.format(data[8])
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
			builder.append(strings[2]).append(" ],\n");
			builder.append(String.format("%1$-" + (lineWidth - strings[3].length()) + "s", "["));
			builder.append(strings[3]).append(String.format("%1$-" + (lineWidth - strings[4].length()) + "s", ","));
			builder.append(strings[4]).append(String.format("%1$-" + (lineWidth - strings[5].length()) + "s", ","));
			builder.append(strings[5]).append(" ],\n");
			builder.append(String.format("%1$-" + (lineWidth - strings[6].length()) + "s", "["));
			builder.append(strings[6]).append(String.format("%1$-" + (lineWidth - strings[7].length()) + "s", ","));
			builder.append(strings[7]).append(String.format("%1$-" + (lineWidth - strings[8].length()) + "s", ","));
			builder.append(strings[8]).append(" ],\n");
		} else {
			builder.append("[ ").append(strings[0]).append(", ").append(strings[1]).append(", ").append(strings[2]).append(" ], ");
			builder.append("[ ").append(strings[3]).append(", ").append(strings[4]).append(", ").append(strings[5]).append(" ], ");
			builder.append("[ ").append(strings[6]).append(", ").append(strings[7]).append(", ").append(strings[8]).append(" ]; ");
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

	public Vector3f getRow(int row) {
		return new Vector3f(data[row + 0 * HEIGHT], data[row + 1 * HEIGHT], data[row + 2 * HEIGHT]);
	}

	public Vector3f getColumn(int column) {
		return new Vector3f(data[0 + column * HEIGHT], data[1 + column * HEIGHT], data[2 + column * HEIGHT]);
	}

	public Matrix3f setData(float[] values) {
		this.data = values;
		return this;
	}

	public Matrix3f setData(int i, float value) {
		this.data[i] = value;
		return this;
	}

	public Matrix3f setData(int row, int column, float value) {
		this.data[row + column * HEIGHT] = value;
		return this;
	}

	public Matrix3f setRow(int i, Vector3f value) {
		this.data[i + 0 * HEIGHT] = value.getX();
		this.data[i + 1 * HEIGHT] = value.getY();
		this.data[i + 2 * HEIGHT] = value.getZ();
		return this;
	}

	public Matrix3f setColumn(int i, Vector3f value) {
		this.data[0 + i * HEIGHT] = value.getX();
		this.data[1 + i * HEIGHT] = value.getY();
		this.data[2 + i * HEIGHT] = value.getZ();
		return this;
	}

	public static Matrix3f Identity() {
		Matrix3f result = new Matrix3f();

		result.data[0] = 1;
		result.data[1] = 0;
		result.data[2] = 0;

		result.data[3] = 0;
		result.data[4] = 1;
		result.data[5] = 0;

		result.data[6] = 0;
		result.data[7] = 0;
		result.data[8] = 1;

		return result;
	}

	public static Matrix3f Translation(Vector2f translation) {
		return Matrix3f.Translation(translation.getX(), translation.getY());
	}

	public static Matrix3f Translation(float x, float y) {
		Matrix3f result = new Matrix3f();

		result.data[0] = 1;
		result.data[1] = 0;
		result.data[2] = 0;

		result.data[3] = 0;
		result.data[4] = 1;
		result.data[5] = 0;

		result.data[6] = x;
		result.data[7] = y;
		result.data[8] = 1;

		return result;
	}

	public static Matrix3f Rotation(float angle) {
		Matrix3f result = new Matrix3f();

		result.data[0] = (float) Math.cos(angle);
		result.data[1] = (float) Math.sin(angle);
		result.data[2] = 0;

		result.data[3] = (float) -Math.sin(angle);
		result.data[4] = (float) Math.cos(angle);
		result.data[5] = 0;

		result.data[6] = 0;
		result.data[7] = 0;
		result.data[8] = 1;
		
		return result;
	}

	public static Matrix3f Rotation(Quaternion quaternion) {
		Vector3f forward = new Vector3f(2.0f * (quaternion.getX() * quaternion.getZ() - quaternion.getW() * quaternion.getY()), 2.0f * (quaternion.getY() * quaternion.getZ() + quaternion.getW() * quaternion.getX()), 1.0f - 2.0f * (quaternion.getX() * quaternion.getX() + quaternion.getY() * quaternion.getY()));
		Vector3f up = new Vector3f(2.0f * (quaternion.getX() * quaternion.getY() + quaternion.getW() * quaternion.getZ()), 1.0f - 2.0f * (quaternion.getX() * quaternion.getX() + quaternion.getZ() * quaternion.getZ()), 2.0f * (quaternion.getY() * quaternion.getZ() - quaternion.getW() * quaternion.getX()));
		Vector3f right = new Vector3f(1.0f - 2.0f * (quaternion.getY() * quaternion.getY() + quaternion.getZ() * quaternion.getZ()), 2.0f * (quaternion.getX() * quaternion.getY() - quaternion.getW() * quaternion.getZ()), 2.0f * (quaternion.getX() * quaternion.getZ() + quaternion.getW() * quaternion.getY()));

		return Matrix3f.Rotation(forward, up, right);
	}

	public static Matrix3f Rotation(float x, float y, float z) {
		Matrix3f result = new Matrix3f();

		Matrix3f rx = new Matrix3f();
		Matrix3f ry = new Matrix3f();
		Matrix3f rz = new Matrix3f();

		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);

		// Rotation around the Z axis
		rz.data[0] = (float) Math.cos(z);
		rz.data[1] = (float) Math.sin(z);
		rz.data[2] = 0;

		rz.data[3] = (float) -Math.sin(z);
		rz.data[4] = (float) Math.cos(z);
		rz.data[5] = 0;

		rz.data[6] = 0;
		rz.data[7] = 0;
		rz.data[8] = 1;

		// Rotation around the X axis
		rx.data[0] = 1;
		rx.data[1] = 0;
		rx.data[2] = 0;

		rx.data[3] = 0;
		rx.data[4] = (float) Math.cos(x);
		rx.data[5] = (float) Math.sin(x);

		rx.data[6] = 0;
		rx.data[7] = (float) -Math.sin(x);
		rx.data[8] = (float) Math.cos(x);

		// Rotation around the Y axis
		ry.data[0] = (float) Math.cos(y);
		ry.data[1] = 0;
		ry.data[2] = (float) Math.sin(y);

		ry.data[3] = 0;
		ry.data[4] = 1;
		ry.data[5] = 0;

		ry.data[6] = (float) -Math.sin(y);
		ry.data[7] = 0;
		ry.data[8] = (float) Math.cos(y);

		result = rz.multiply(ry.multiply(rx));

		return result;
	}

	public static Matrix3f Rotation(Vector3f forward, Vector3f upward) {
		Vector3f zAxis = Vector3f.normalize(forward);
		Vector3f xAxis = Vector3f.normalize(upward).cross(zAxis);
		Vector3f yAxis = Vector3f.cross(zAxis, xAxis).normalize();

		return Matrix3f.Rotation(zAxis, yAxis, xAxis);
	}

	public static Matrix3f Rotation(Vector3f forward, Vector3f upward, Vector3f right) {
		Matrix3f result = new Matrix3f();

		Vector3f xAxis = Vector3f.normalize(right);
		Vector3f yAxis = Vector3f.normalize(upward);
		Vector3f zAxis = Vector3f.normalize(forward);

		result.data[0] = xAxis.getX();
		result.data[1] = yAxis.getX();
		result.data[2] = zAxis.getX();

		result.data[3] = xAxis.getY();
		result.data[4] = yAxis.getY();
		result.data[5] = zAxis.getY();

		result.data[6] = xAxis.getZ();
		result.data[7] = yAxis.getZ();
		result.data[8] = zAxis.getZ();

		return result;
	}

	public static Matrix3f ViewRotation(Vector3f forward, Vector3f upward, Vector3f right) {
		Matrix3f result = new Matrix3f();

		Vector3f xAxis = Vector3f.normalize(right);
		Vector3f yAxis = Vector3f.normalize(upward);
		Vector3f zAxis = Vector3f.normalize(forward);

		result.data[0] = xAxis.getX();
		result.data[1] = xAxis.getY();
		result.data[2] = xAxis.getZ();

		result.data[3] = yAxis.getX();
		result.data[4] = yAxis.getY();
		result.data[5] = yAxis.getZ();

		result.data[6] = zAxis.getX();
		result.data[7] = zAxis.getY();
		result.data[8] = zAxis.getZ();

		return result;
	}

	public static Matrix3f Scale(Vector2f translation) {
		return Matrix3f.Scale(translation.getX(), translation.getY());
	}

	public static Matrix3f Scale(float x, float y) {
		Matrix3f result = new Matrix3f();

		result.data[0] = x;
		result.data[1] = 0;
		result.data[2] = 0;

		result.data[3] = 0;
		result.data[4] = y;
		result.data[5] = 0;

		result.data[6] = 0;
		result.data[7] = 0;
		result.data[8] = 1;

		return result;
	}
}
