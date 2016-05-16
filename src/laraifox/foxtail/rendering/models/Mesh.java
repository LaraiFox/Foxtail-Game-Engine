package laraifox.foxtail.rendering.models;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import laraifox.foxtail.core.BufferUtils;
import laraifox.foxtail.core.ICleanable;
import laraifox.foxtail.core.ResourceManager;
import laraifox.foxtail.rendering.Vertex;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh implements ICleanable {
	private String resourceName;
	private int vaoID;
	public int count;
	private int byteCount;

	public Mesh(float[] vertices, int[] indices) {
		this.createModel(BufferUtils.createIntBuffer(indices, true), BufferUtils.createFloatBuffer(vertices, true), indices.length, vertices.length / Vertex.COMPONENT_COUNT);
	}

	public Mesh(String resourceName, Vertex[] vertices, int[] indices) {
		this.resourceName = resourceName;

		this.createModel(BufferUtils.createIntBuffer(indices, true), BufferUtils.createFloatBuffer(vertices, true), indices.length, vertices.length);
	}

	public Mesh(String filepath) {
		this(ResourceManager.getMeshResource(filepath));
	}

	public Mesh(Mesh mesh) {
		this.resourceName = mesh.resourceName;
		this.vaoID = mesh.vaoID;
		this.count = mesh.count;
		this.byteCount = mesh.byteCount;
	}

	@Override
	protected void finalize() throws Throwable {
		ResourceManager.releaseMeshResource(resourceName);
	}

	public void cleanUp() {
		GL30.glDeleteVertexArrays(vaoID);
	}

	protected void createModel(IntBuffer indexBuffer, FloatBuffer vertexBuffer, int indexCount, int vertexCount) {
		this.vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);

		int iboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);

		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.POSITION_OFFSET);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.TEXCOORD_OFFSET);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.NORMAL_OFFSET);
		GL20.glVertexAttribPointer(3, 3, GL11.GL_FLOAT, false, Vertex.BYTE_COUNT, Vertex.TANGENT_OFFSET);

		GL30.glBindVertexArray(0);

		GL15.glDeleteBuffers(iboID);
		GL15.glDeleteBuffers(vboID);

		this.count = indexCount;
		this.byteCount = indexCount * Integer.BYTES + vertexCount * Vertex.BYTE_COUNT;
	}

	public void draw() {
		GL11.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
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

	public int getID() {
		return vaoID;
	}

	public int getByteCount() {
		return byteCount;
	}
}
