package net.laraifox.libdemo.graphics;

import org.lwjgl.opengl.GL30;

public class VertexArray {
	private int id;

	// private ArrayList<DataBuffer> buffers;

	public VertexArray() {
		this.id = GL30.glGenVertexArrays();

		// this.buffers = new ArrayList<DataBuffer>();
	}

	@Override
	protected void finalize() {
		GL30.glDeleteVertexArrays(id);
	}

	public void addBuffer(DataBuffer buffer, VertexAttribPointer... pointers) {
		this.bind();
		buffer.bind();
		for (VertexAttribPointer pointer : pointers) {
			pointer.usePointer();
		}
		buffer.unbind();
		this.unbind();
	}

	public void bind() {
		GL30.glBindVertexArray(id);
	}

	public void unbind() {
		GL30.glBindVertexArray(0);
	}
}
