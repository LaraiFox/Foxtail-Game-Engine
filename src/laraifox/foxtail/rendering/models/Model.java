package laraifox.foxtail.rendering.models;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import laraifox.foxtail.core.BufferUtils;
import laraifox.foxtail.rendering.Vertex;

public class Model {
	private int vaoID;
	private int count;

	public Model(float[] vertices, int[] indices) {
		this.vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);

		int iboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices, true), GL15.GL_STATIC_DRAW);

		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices, true), GL15.GL_STATIC_DRAW);

		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.POSITION_OFFSET);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.TEXCOORD_OFFSET);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.NORMAL_OFFSET);
		GL20.glVertexAttribPointer(3, 3, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.TANGENT_OFFSET);

		GL30.glBindVertexArray(0);

		this.count = indices.length;
	}

	public Model(Vertex[] vertices, int[] indices) {
		this.vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);

		int iboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices, true), GL15.GL_STATIC_DRAW);

		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices, true), GL15.GL_STATIC_DRAW);

		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.POSITION_OFFSET);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.TEXCOORD_OFFSET);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.NORMAL_OFFSET);
		GL20.glVertexAttribPointer(3, 3, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.TANGENT_OFFSET);

		GL30.glBindVertexArray(0);

		this.count = indices.length;
	}

	public void render() {
		GL30.glBindVertexArray(vaoID);

		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);

		GL11.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);

		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);

		GL30.glBindVertexArray(0);
	}
}
