package net.laraifox.libdev.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import net.laraifox.libdev.graphics.Vertex3D;
import net.laraifox.libdev.math.Matrix4f;
import net.laraifox.libdev.math.Vector3f;

public class BufferUtils {
	private static final int SHORT_SIZE = Short.SIZE / Byte.SIZE;
	private static final int INTEGER_SIZE = Integer.SIZE / Byte.SIZE;
	private static final int FLOAT_SIZE = Float.SIZE / Byte.SIZE;

	public static ByteBuffer createByteBuffer(int size) {
		return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
	}

	public static ByteBuffer createShortBuffer(byte[] data, boolean flipped) {
		ByteBuffer buffer = createByteBuffer(data.length);
		buffer.put(data);

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static ShortBuffer createShortBuffer(int size) {
		return createByteBuffer(size * SHORT_SIZE).asShortBuffer();
	}

	public static ShortBuffer createShortBuffer(short[] data, boolean flipped) {
		ShortBuffer buffer = createShortBuffer(data.length);
		buffer.put(data);

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static IntBuffer createIntBuffer(int size) {
		return createByteBuffer(size * INTEGER_SIZE).asIntBuffer();
	}

	public static IntBuffer createIntBuffer(int[] indices, boolean flipped) {
		IntBuffer buffer = createIntBuffer(indices.length);
		buffer.put(indices);

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(int size) {
		return createByteBuffer(size * FLOAT_SIZE).asFloatBuffer();
	}

	public static FloatBuffer createFloatBuffer(float[] data, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(data.length);
		buffer.put(data);

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(Vector3f[] values, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(values.length * Vertex3D.SIZE);
		for (Vector3f value : values) {
			buffer.put(value.getX());
			buffer.put(value.getY());
			buffer.put(value.getZ());
		}

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(Vertex3D[] values, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(values.length * Vertex3D.SIZE);
		for (Vertex3D value : values) {
			buffer.put(value.getPosition().getX());
			buffer.put(value.getPosition().getY());
			buffer.put(value.getPosition().getZ());

			buffer.put(value.getTextureCoord().getX());
			buffer.put(value.getTextureCoord().getY());

			buffer.put(value.getNormal().getX());
			buffer.put(value.getNormal().getY());
			buffer.put(value.getNormal().getZ());
		}

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(Matrix4f matrix, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(16);
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				buffer.put(matrix.getDataAt(i, j));
			}
		}

		if (flipped)
			buffer.flip();

		return buffer;
	}
}
