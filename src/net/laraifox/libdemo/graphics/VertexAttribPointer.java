package net.laraifox.libdemo.graphics;

import org.lwjgl.opengl.GL20;

public class VertexAttribPointer {
	private int index;
	private int size;
	private int type;
	private boolean normalized;
	private int stride;
	private int offset;

	public VertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset) {
		this.index = index;
		this.size = size;
		this.type = type;
		this.normalized = normalized;
		this.stride = stride;
		this.offset = offset;
	}

	public void usePointer() {
		GL20.glEnableVertexAttribArray(index);
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, offset);
		GL20.glDisableVertexAttribArray(index);
	}

	public int getIndex() {
		return index;
	}

	public int getSize() {
		return size;
	}

	public int getType() {
		return type;
	}

	public boolean isNormalized() {
		return normalized;
	}

	public int getStride() {
		return stride;
	}

	public int getOffset() {
		return offset;
	}
}
