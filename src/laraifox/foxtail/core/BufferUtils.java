package laraifox.foxtail.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.Vector2f;
import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.core.math.Vector4f;
import laraifox.foxtail.rendering.Vertex;

public class BufferUtils {
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
		return createByteBuffer(size * Short.BYTES).asShortBuffer();
	}

	public static ShortBuffer createShortBuffer(short[] data, boolean flipped) {
		ShortBuffer buffer = createShortBuffer(data.length);
		buffer.put(data);

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static IntBuffer createIntBuffer(int size) {
		return createByteBuffer(size * Integer.BYTES).asIntBuffer();
	}

	public static IntBuffer createIntBuffer(int[] indices, boolean flipped) {
		IntBuffer buffer = createIntBuffer(indices.length);
		buffer.put(indices);

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(int size) {
		return createByteBuffer(size * Float.BYTES).asFloatBuffer();
	}

	public static FloatBuffer createFloatBuffer(float[] data, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(data.length);
		buffer.put(data);

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(Vector2f value, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(Vector2f.COMPONENT_COUNT);
		buffer.put(value.getX());
		buffer.put(value.getY());

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(Vector3f value, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(Vector3f.COMPONENT_COUNT);
		buffer.put(value.getX());
		buffer.put(value.getY());
		buffer.put(value.getZ());

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(Vector4f value, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(Vector4f.COMPONENT_COUNT);
		buffer.put(value.getX());
		buffer.put(value.getY());
		buffer.put(value.getZ());
		buffer.put(value.getW());

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(Vector3f[] values, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(values.length * Vertex.BYTE_COUNT);
		for (Vector3f value : values) {
			buffer.put(value.getX());
			buffer.put(value.getY());
			buffer.put(value.getZ());
		}

		if (flipped)
			buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer(Vertex[] values, boolean flipped) {
		FloatBuffer buffer = createFloatBuffer(values.length * Vertex.BYTE_COUNT);
		for (Vertex value : values) {
			buffer.put(value.getPosition().getX());
			buffer.put(value.getPosition().getY());
			buffer.put(value.getPosition().getZ());

			buffer.put(value.getTexCoord().getX());
			buffer.put(value.getTexCoord().getY());

			buffer.put(value.getNormal().getX());
			buffer.put(value.getNormal().getY());
			buffer.put(value.getNormal().getZ());

			buffer.put(value.getTangent().getX());
			buffer.put(value.getTangent().getY());
			buffer.put(value.getTangent().getZ());

			buffer.put(value.getBinormal().getX());
			buffer.put(value.getBinormal().getY());
			buffer.put(value.getBinormal().getZ());
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
