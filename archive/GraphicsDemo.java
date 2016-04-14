package net.laraifox.libdev._demos;

import net.laraifox.lib.display.OpenGLDisplay;
import net.laraifox.libdev.graphics.DataBuffer;
import net.laraifox.libdev.graphics.IndexBuffer;
import net.laraifox.libdev.graphics.VertexArray;
import net.laraifox.libdev.graphics.VertexAttribPointer;
import net.laraifox.libdev.utils.BufferUtils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.rendering.Camera;
import laraifox.foxtail.rendering.Shader;

public class GraphicsDemo extends OpenGLDisplay {
	private Camera camera;
	private Shader shader;

	private float[] vertices = new float[] {
			-0.5f, -0.5f, 0.0f, //
			0.5f, -0.5f, 0.0f, //
			0.5f, 0.5f, 0.0f, //
			-0.5f, 0.5f, 0.0f
	};

	private int[] indices = new int[] {
			0, 1, 2, //
			0, 2, 3
	};

	// private float[] vertices = new float[] {
	// 0.0f, 0.0f, -5.0f,
	// 2.5f, 0.0f, -5.0f,
	// 5.0f, 0.0f, -5.0f,
	// 5.0f, 0.0f, -2.5f,
	// 5.0f, 0.0f, 0.0f,
	// 5.0f, 0.0f, 2.5f,
	// 5.0f, 0.0f, 5.0f,
	// 2.5f, 0.0f, 5.0f,
	// 0.0f, 0.0f, 5.0f,
	// -2.5f, 0.0f, 5.0f,
	// -5.0f, 0.0f, 5.0f,
	// -5.0f, 0.0f, 2.5f,
	// -5.0f, 0.0f, 0.0f,
	// -5.0f, 0.0f, -2.5f,
	// -5.0f, 0.0f, -5.0f,
	// -2.5f, 0.0f, -5.0f,
	// };
	//
	// private int[] indices = new int[] {
	// 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15
	// };

	private VertexArray vao;
	private DataBuffer vbo;
	private IndexBuffer ibo;

	private int vaoID;
	private int vboID;
	private int iboID;

	private float t;

	public GraphicsDemo() {
		super("Quadtree Test Window", 1024, 768);

		// setOrthographicProjection(new OrthographicProjection(0.1f, 50f));
	}

	@Override
	protected void cleanUp() {

	}

	@Override
	protected void initializeVariables() {
		this.camera = new Camera(new Transform3D(new Vector3f(0, 0, -1)), Matrix4f.Projection(70.0f, width, height, 0.1f, 500.0f));

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

		this.t = 0.0f;

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
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices, true), GL15.GL_STATIC_DRAW);

		GL30.glBindVertexArray(0);

		shader.setUniform("fogColor", new Vector3f(0, 0, 0));

	}

	@Override
	protected void tick() {

		// System.out.println("     Shader:");
		// System.out.println(camera.getViewMatrix().transpose().multiply(camera.getProjectionMatrix().transpose()).toString());
		// System.out.println("");
		// System.out.println("     Camera:");
		// System.out.println(camera.getViewProjectionMatrix().transpose().toString());
	}

	@Override
	protected void update(float delta) {
		System.err.println(Vector3f.orthoNormalize(new Vector3f(0.0f, 0.3f, 0.4f), Vector3f.Up()).toString());
		System.out.println("");
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			camera.translate(camera.getForward(), 0.2f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.translate(camera.getForward(), -0.2f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.translate(camera.getRight(), -0.2f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.translate(camera.getRight(), 0.2f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			camera.translate(camera.getUpward(), 0.2f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			camera.translate(camera.getUpward(), -0.2f);
		}

		camera.lookAt(Vector3f.Zero(), Vector3f.Up());

		t += 0.01f;

		// System.out.println(camera.getForward().toString());
		// System.out.println(camera.getUpward().toString());
		// System.out.println(camera.getRight().toString());
		// System.out.println(" - - - - - - - - - - - - - - -");

		// camera.lookAt(Vector3f.Zero(), Vector3f.PositiveY());

		// camera.setPosition(Vector3f.PositiveZ().scale(10));
	}

	@Override
	protected void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLoadIdentity();

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL15.GL_ELEMENT_ARRAY_BUFFER_BINDING);

		shader.bind();
		shader.setUniform("ml_matrix", Matrix4f.Identity());// Matrix4f.Translation((float) Math.sin(t), 0.0f, (float) Math.cos(t)));
		// shader.setUniform("vw_matrix", Matrix4f.Identity());
		shader.setUniform("vw_matrix", camera.getViewMatrix());
		// shader.setUniform("pr_matrix", Matrix4f.Identity());
		shader.setUniform("pr_matrix", camera.getProjectionMatrix());

		// shader.setUniform("lightPosition", new Vector2f(Mouse.getX(), Mouse.getY()));

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
