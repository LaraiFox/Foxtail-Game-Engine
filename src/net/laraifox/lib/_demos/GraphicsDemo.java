package net.laraifox.lib._demos;

import net.laraifox.lib.display.OpenGLDisplay;
import net.laraifox.lib.graphics.DataBuffer;
import net.laraifox.lib.graphics.IndexBuffer;
import net.laraifox.lib.graphics.Shader;
import net.laraifox.lib.graphics.VertexArray;
import net.laraifox.lib.graphics.VertexAttribPointer;
import net.laraifox.lib.math.Vector2f;
import net.laraifox.lib.utils.BufferUtils;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class GraphicsDemo extends OpenGLDisplay {
	private Shader shader;

	private float[] vertices = new float[] {
			-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f
	};
	private int[] indices = new int[] {
			0, 1, 2, 0, 2, 3
	};
	private VertexArray vao;
	private DataBuffer vbo;
	private IndexBuffer ibo;

	private int vaoID;
	private int vboID;
	private int iboID;

	public GraphicsDemo() {
		super("Quadtree Test Window");

		// setOrthographicProjection(new OrthographicProjection(800, 600, false));
	}

	@Override
	protected void cleanUp() {

	}

	@Override
	protected void initializeVariables() {
		try {
			this.shader = new Shader("res/shaders/basic.vs", "res/shaders/basic.fs", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.vao = new VertexArray();
		this.vbo = new DataBuffer(vertices, DataBuffer.GL_STATIC_DRAW);
		this.ibo = new IndexBuffer(indices, IndexBuffer.GL_STATIC_DRAW);
		vao.addBuffer(vbo, new VertexAttribPointer(0, vbo.getCount(), GL11.GL_FLOAT, false, 0, 0));

		// GL20.glEnableVertexAttribArray(0);
		// GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		// GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		// GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
		// GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		// GL20.glDisableVertexAttribArray(0);

		this.vaoID = GL30.glGenVertexArrays();
		this.vboID = GL15.glGenBuffers();
		this.iboID = GL15.glGenBuffers();

		// glBindBuffer(GL_ARRAY_BUFFER, m_Buffers[POS_VB]);
		// glBufferData(GL_ARRAY_BUFFER, sizeof(Positions[0]) * Positions.size(), &Positions[0], GL_STATIC_DRAW);
		// glEnableVertexAttribArray(0);
		// glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, 0);
		//
		// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_Buffers[INDEX_BUFFER]);
		// glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(Indices[0]) * Indices.size(), &Indices[0], GL_STATIC_DRAW);

		GL30.glBindVertexArray(vaoID);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices, true), GL15.GL_STATIC_DRAW);

		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices, true), GL15.GL_STATIC_DRAW);

		GL30.glBindVertexArray(0);

	}

	@Override
	protected void tick() {

	}

	@Override
	protected void update(float delta) {

	}

	@Override
	protected void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLoadIdentity();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL15.GL_ELEMENT_ARRAY_BUFFER_BINDING);

		shader.bindShader();

		shader.setUniform("lightPosition", new Vector2f(Mouse.getX(), Mouse.getY()));

		GL30.glBindVertexArray(vaoID);
		GL32.glDrawElementsBaseVertex(GL11.GL_TRIANGLES, indices.length, GL11.GL_UNSIGNED_INT, iboID, 0);
		GL30.glBindVertexArray(0);

		// GL20.glEnableVertexAttribArray(0);
		// GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		// GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
		// // GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
		// GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboID);
		// GL11.glDrawElements(GL11.GL_TRIANGLES, indices.length, GL11.GL_UNSIGNED_INT, 0);
		// GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		// GL20.glDisableVertexAttribArray(0);

		GL20.glUseProgram(0);
		// vao.bind();
		// ibo.bind();
		// GL11.glDrawElements(GL11.GL_TRIANGLES, ibo.getCount(),
		// GL11.GL_UNSIGNED_INT, 0);
		// ibo.unbind();
		// vao.unbind();
	}
}
