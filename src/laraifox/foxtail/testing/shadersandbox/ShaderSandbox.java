package laraifox.foxtail.testing.shadersandbox;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import laraifox.foxtail.core.IGameManager;
import laraifox.foxtail.core.InputHandler;
import laraifox.foxtail.core.Logger;
import laraifox.foxtail.core.OpenGLDisplay;
import laraifox.foxtail.core.Profiler;
import laraifox.foxtail.core.ResourceManager;
import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.PerlinNoiseGenerator;
import laraifox.foxtail.core.math.Quaternion;
import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.core.math.Vector4f;
import laraifox.foxtail.rendering.Camera;
import laraifox.foxtail.rendering.Shader;
import laraifox.foxtail.rendering.Texture2D;
import laraifox.foxtail.rendering.TextureCube;
import laraifox.foxtail.rendering.TextureFilter;
import laraifox.foxtail.rendering.models.Mesh;
import laraifox.foxtail.testing.thinmatrixfont.FontType;
import laraifox.foxtail.testing.thinmatrixfont.GUIText;
import laraifox.foxtail.testing.thinmatrixfont.TextManager;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShaderSandbox implements IGameManager {
	private OpenGLDisplay display;

	private static final float[] CUBE_VERTICES = new float[] {
			// LEFT SIDE VERTICES { 0, 1, 2, 0, 2, 2 }
			-0.5f, -0.5f, 0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (0)
			-0.5f, -0.5f, -0.5f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (1)
			-0.5f, 0.5f, -0.5f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (2)
			-0.5f, 0.5f, 0.5f, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (3)
			// RIGHT SIDE VERTICES
			0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (4)
			0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (5)
			0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (6)
			0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (7)
			// BOTTOM SIDE VERTICES
			-0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (8)
			0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (9)
			0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (10)
			-0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (11)
			// TOP SIDE VERTICES
			-0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (12)
			0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (13)
			0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (14)
			-0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (15)
			// BACK SIDE VERTICES
			-0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (16)
			0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (17)
			0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (18)
			-0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (19)
			// FRONT SIDE VERTICES
			0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (20)
			-0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (21)
			-0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (22)
			0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (23)
	};
	private static final int[] CUBE_INDICES = new int[] {
			0, 1, 2, 0, 2, 3, // LEFT FACE
			4, 5, 6, 4, 6, 7, // RIGHT FACE
			8, 9, 10, 8, 10, 11, // BOTTOM FACE
			12, 13, 14, 12, 14, 15, // TOP FACE
			16, 17, 18, 16, 18, 19, // BACK FACE
			20, 21, 22, 20, 22, 23, // FRONT FACE
	};

	private static final float[] SKYBOX_VERTICES = new float[] {
			// LEFT SIDE VERTICES
			-50.0f, -50.0f, 50.0f, // (0)
			-50.0f, -50.0f, -50.0f, // (50)
			-50.0f, 50.0f, -50.0f, // (2)
			-50.0f, 50.0f, 50.0f, // (50)
			// RIGHT SIDE VERTICES
			50.0f, -50.0f, -50.0f, // (4)
			50.0f, -50.0f, 50.0f, // (5)
			50.0f, 50.0f, 50.0f, // (6)
			50.0f, 50.0f, -50.0f, // (7)
			// BOTTOM SIDE VERTICES
			-50.0f, -50.0f, 50.0f, // (8)
			50.0f, -50.0f, 50.0f, // (9)
			50.0f, -50.0f, -50.0f, // (500)
			-50.0f, -50.0f, -50.0f, // (5050)
			// TOP SIDE VERTICES
			-50.0f, 50.0f, -50.0f, // (502)
			50.0f, 50.0f, -50.0f, // (5050)
			50.0f, 50.0f, 50.0f, // (504)
			-50.0f, 50.0f, 50.0f, // (505)
			// BACK SIDE VERTICES
			-50.0f, -50.0f, -50.0f, // (506)
			50.0f, -50.0f, -50.0f, // (507)
			50.0f, 50.0f, -50.0f, // (508)
			-50.0f, 50.0f, -50.0f, // (509)
			// FRONT SIDE VERTICES
			50.0f, -50.0f, 50.0f, // (20)
			-50.0f, -50.0f, 50.0f, // (250)
			-50.0f, 50.0f, 50.0f, // (22)
			50.0f, 50.0f, 50.0f, // (250)
	};

	private Camera camera;
	private Entity[] entity_Spheres;
	private Entity entity_Cube1;
	private Entity entity_Bunny;
	private Entity entity_Cube2;
	private Entity entity_Dragon;

	private Entity entity_Cube3;

	private Entity entity_Torus;
	private Entity entity_SphereOrbitter1;
	private Entity entity_SphereOrbitter2;

	private Texture2D texture_Checker;
	private Texture2D texture_Blank;
	private Texture2D texture_Perlin;

	private Shader shader;
	private Vector3f velocity_Camera;

	private FontType fontType;
	private GUIText fpsText;
	private GUIText memoryText;

	private Runtime runtime = Runtime.getRuntime();

	private TextureCube skyboxTexture;
	private Shader skyboxShader;
	private int skyboxVAO;

	private GUIText[] matrixText;

	private float angle = 0;

	public void initialize(OpenGLDisplay display) {
		Logger.log(ResourceManager.USERS_APPDATA_DIRECTORY, "System", Logger.MESSAGE_LEVEL_DEBUG);
		Logger.lineBreak(Logger.MESSAGE_LEVEL_DEBUG);

		this.display = display;

		this.camera = new Camera(new Transform3D(new Vector3f(0.0f, 1.0f, 0.0f)), Matrix4f.Projection(70.0f, Display.getWidth(), Display.getHeight(), 0.01f, 100.0f));

		final int SPHERE_COUNT = 500;
		this.entity_Spheres = new Entity[SPHERE_COUNT * 2];
		for (int i = 0; i < SPHERE_COUNT; i++) {
			entity_Spheres[i * 2 + 0] = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), (1.0f / (SPHERE_COUNT - 1) * i * 10.0f), 64.0f), new Mesh(
					ResourceManager.getFoxtailResourcePath("models/Sphere.obj")), new Transform3D(new Vector3f(-10.0f + (20.0f / (SPHERE_COUNT - 1) * i), 1.0f, -5.0f),
					new Vector3f(0.075f)), new Transform3D());
			entity_Spheres[i * 2 + 1] = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), (1.0f / (SPHERE_COUNT - 1) * i * 10.0f), 256.0f), new Mesh(
					ResourceManager.getFoxtailResourcePath("models/Sphere.obj")), new Transform3D(new Vector3f(-10.0f + (20.0f / (SPHERE_COUNT - 1) * i), -1.0f, -5.0f),
					new Vector3f(0.075f)), new Transform3D());
		}

		this.entity_Bunny = new Entity(new Material(new Vector4f(1.0f, 0.64f, 0.39f, 1.0f), 0.5f, 5.0f), new Mesh(
				ResourceManager.getFoxtailResourcePath("models/StanfordBunny.obj")), new Transform3D(new Vector3f(-4.0f, -0.5f, 5.0f), new Vector3f(0.3f, 0.3f, 0.3f)),
				new Transform3D(Quaternion.AxisAngle(Vector3f.Up(), 0.2f)));
		this.entity_Dragon = new Entity(new Material(new Vector4f(1.0f, 0.64f, 0.39f, 1.0f), 0.5f, 5.0f), new Mesh(
				ResourceManager.getFoxtailResourcePath("models/StanfordDragon.obj")), new Transform3D(new Vector3f(4.0f, -0.5f, 5.0f), new Vector3f(0.3f, 0.3f, 0.3f)),
				new Transform3D(Quaternion.AxisAngle(Vector3f.Up(), 0.2f)));

		this.entity_Cube1 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 0.5f, 1.0f), new Mesh(CUBE_VERTICES, CUBE_INDICES), new Transform3D(new Vector3f(-4.0f,
				-0.75f, 5.0f), new Vector3f(5.0f, 0.5f, 5.0f)), new Transform3D());
		this.entity_Cube2 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 0.5f, 1.0f), new Mesh(CUBE_VERTICES, CUBE_INDICES), new Transform3D(new Vector3f(4.0f,
				-0.75f, 5.0f), new Vector3f(5.0f, 0.5f, 5.0f)), new Transform3D());
		this.entity_Cube3 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 0.5f, 1.0f), new Mesh(CUBE_VERTICES, CUBE_INDICES), new Transform3D(new Vector3f(0.0f,
				-10.0f, 0.0f), new Vector3f(50.0f, 0.5f, 50.0f)), new Transform3D());

		entity_Torus = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 1.0f, 256.0f), new Mesh(ResourceManager.getFoxtailResourcePath("models/Torus.obj")),
				new Transform3D(new Vector3f(15.0f, 0.0f, 0.0f), new Vector3f(1.5f)), new Transform3D());
		entity_SphereOrbitter1 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 5.0f, 256.0f),
				new Mesh(ResourceManager.getFoxtailResourcePath("models/Sphere.obj")), new Transform3D(new Vector3f(15.0f, 0.0f, 0.0f), new Vector3f(0.75f)), new Transform3D());
		entity_SphereOrbitter2 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 5.0f, 256.0f),
				new Mesh(ResourceManager.getFoxtailResourcePath("models/Sphere.obj")), new Transform3D(new Vector3f(15.0f, 0.0f, 5.75f), new Vector3f(0.75f)), new Transform3D());

		TextureFilter textureFilter = new TextureFilter();
		textureFilter.setGLTextureMinFilter(GL11.GL_NEAREST_MIPMAP_LINEAR);
		textureFilter.setGLTextureMagFilter(GL11.GL_NEAREST);
		textureFilter.setGLTextureWrapS(GL12.GL_CLAMP_TO_EDGE);
		textureFilter.setGLTextureWrapT(GL12.GL_CLAMP_TO_EDGE);
		textureFilter.setGLTextureAnisotropy(2.0f);

		this.texture_Checker = new Texture2D(ResourceManager.getFoxtailResourcePath("textures/GreyscaleCheckerBoard.png"), textureFilter);
		this.texture_Blank = new Texture2D(ResourceManager.getFoxtailResourcePath("textures/Blank.png"), textureFilter);

		PerlinNoiseGenerator png = new PerlinNoiseGenerator(0);
		final int PERLIN_TEXTURE_RES = 512;
		int[] perlinPixels = new int[PERLIN_TEXTURE_RES * PERLIN_TEXTURE_RES];
		for (int i = 0; i < PERLIN_TEXTURE_RES; i++) {
			for (int j = 0; j < PERLIN_TEXTURE_RES; j++) {
				perlinPixels[i + j * PERLIN_TEXTURE_RES] = (int) (png.octavePerlin(i / 128.0f, j / 2.0f, 0, 1, 0.25f) * 255.0f);
			}
		}

		ByteBuffer perlinBuffer = BufferUtils.createByteBuffer(perlinPixels.length * 4);
		for (int i = 0; i < perlinPixels.length; i++) {
			perlinBuffer.put((byte) perlinPixels[i]);
			perlinBuffer.put((byte) perlinPixels[i]);
			perlinBuffer.put((byte) perlinPixels[i]);
			perlinBuffer.put((byte) 0xFF);
		}

		perlinBuffer.flip();

		TextureFilter textureFilter2 = new TextureFilter();
		textureFilter2.setGLTextureMinFilter(GL11.GL_LINEAR_MIPMAP_LINEAR);
		textureFilter2.setGLTextureMagFilter(GL11.GL_LINEAR);
		textureFilter2.setGLTextureAnisotropy(2.0f);

		this.texture_Perlin = new Texture2D(perlinBuffer, PERLIN_TEXTURE_RES, PERLIN_TEXTURE_RES, textureFilter2);

		try {
			this.shader = new Shader(ResourceManager.getFoxtailResourcePath("shaders/TexturedPhong.shader"), false);
			this.skyboxShader = new Shader(ResourceManager.getFoxtailResourcePath("shaders/Skybox.shader"), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.velocity_Camera = new Vector3f();

		GL11.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL13.GL_MULTISAMPLE);

		// Logger.log("Model 'StanfordBunny.obj' size: " + entity_Bunny.getMeshSize(), "AssetLoader");
		// Logger.log("Model 'StanfordDragon.obj' size: " + entity_Dragon.getMeshSize(), "AssetLoader");
		// Logger.log("Model 'Cube' size: " + entity_Cube1.getMeshSize(), "AssetLoader");
		// Logger.log("Model 'Sphere.obj' size: " + entity_Spheres[0].getMeshSize(), "AssetLoader");
		// Logger.log("Model 'Torus.obj' size: " + entity_Torus.getMeshSize(), "AssetLoader");
		// int totalModelDataSize = 0;
		// totalModelDataSize += entity_Bunny.getMeshSize();
		// totalModelDataSize += entity_Dragon.getMeshSize();
		// totalModelDataSize += entity_Torus.getMeshSize();
		// totalModelDataSize += entity_Spheres[0].getMeshSize() * (entity_Spheres.length + 2);
		// totalModelDataSize += entity_Cube1.getMeshSize() * 3;
		// Logger.log("Total model data size: " + totalModelDataSize, "AssetLoader");
		// Logger.lineBreak();
		// Logger.flush(true);

		TextManager.initialize();

		this.fontType = new FontType(ResourceManager.getFoxtailResourcePath("fonts/Inconsolata-Regular.png"),
				ResourceManager.getFoxtailResourcePath("fonts/Inconsolata-Regular.fnt"));

		this.fpsText = new GUIText("FPS: " + display.getCurrentFPS(), 1.0f, fontType, new Transform3D(new Vector3f(0.005f, 0.005f, 0.0f)), 1.0f, false);
		this.memoryText = new GUIText("Memory: " + ((runtime.totalMemory() - runtime.freeMemory()) / 1048576) + "M / " + (runtime.totalMemory() / 1048576) + "M ("
			+ ((int) (((float) (runtime.totalMemory() - runtime.freeMemory()) / (float) runtime.totalMemory()) * 10000.0f) / 100) + "%)", 1.0f, fontType, new Transform3D(
				new Vector3f(0.005f, 0.035f, 0.0f)), 1.0f, false);

		TextureFilter textureFilter3 = new TextureFilter();
		textureFilter3.setGLTextureWrapS(GL12.GL_CLAMP_TO_EDGE);
		textureFilter3.setGLTextureWrapT(GL12.GL_CLAMP_TO_EDGE);
		// textureFilter3.setGLTextureMinFilter(GL11.GL_NEAREST_MIPMAP_LINEAR);
		this.skyboxTexture = new TextureCube(new String[] {
				ResourceManager.getFoxtailResourcePath("textures/skybox/right.png"), ResourceManager.getFoxtailResourcePath("textures/skybox/left.png"), //
				ResourceManager.getFoxtailResourcePath("textures/skybox/top.png"), ResourceManager.getFoxtailResourcePath("textures/skybox/bottom.png"), //
				ResourceManager.getFoxtailResourcePath("textures/skybox/back.png"), ResourceManager.getFoxtailResourcePath("textures/skybox/front.png")
		}, textureFilter3);
		this.skyboxVAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(skyboxVAO);

		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);

		IntBuffer ibuffer = BufferUtils.createIntBuffer(CUBE_INDICES.length);
		ibuffer.put(CUBE_INDICES);
		ibuffer.flip();

		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, ibuffer, GL15.GL_STATIC_DRAW);

		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

		FloatBuffer buffer = BufferUtils.createFloatBuffer(SKYBOX_VERTICES.length);
		buffer.put(SKYBOX_VERTICES);
		buffer.flip();

		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0 * Float.BYTES);

		GL30.glBindVertexArray(0);

		matrixText = new GUIText[] {
				new GUIText("", 1.0f, fontType, new Transform3D(new Vector3f(0.005f, 0.095f, 0.0f)), 1.0f, false),//
				new GUIText("", 1.0f, fontType, new Transform3D(new Vector3f(0.005f, 0.125f, 0.0f)), 1.0f, false),//
				new GUIText("", 1.0f, fontType, new Transform3D(new Vector3f(0.005f, 0.155f, 0.0f)), 1.0f, false),//
				new GUIText("", 1.0f, fontType, new Transform3D(new Vector3f(0.005f, 0.185f, 0.0f)), 1.0f, false)
		};

		Logger.log(TextureFilter.DEFAULT_FILTER.toString(), "System", Logger.MESSAGE_LEVEL_DEBUG);
		Logger.log(textureFilter.toString(), "System", Logger.MESSAGE_LEVEL_DEBUG);
		Logger.log(textureFilter2.toString(), "System", Logger.MESSAGE_LEVEL_DEBUG);
		Logger.log(textureFilter3.toString(), "System", Logger.MESSAGE_LEVEL_DEBUG);
		Logger.lineBreak(Logger.MESSAGE_LEVEL_DEBUG);
	}

	public void cleanUp() {

	}

	public void tick() {
		 System.gc();

		Display.setTitle(display.getTitle() + " | FPS: " + display.getCurrentFPS());

		TextManager.removeText(fpsText);
		this.fpsText = new GUIText("FPS: " + display.getCurrentFPS(), 1.0f, fontType, new Transform3D(new Vector3f(0.005f, 0.005f, 0.0f)), 0.5f, false);

		Profiler.logSamples();

		Logger.flush();
	}

	public void update(float delta) {
		InputHandler.update();

		TextManager.removeText(memoryText);
		this.memoryText = new GUIText("Memory: " + ((runtime.totalMemory() - runtime.freeMemory()) / 1048576) + "M / " + (runtime.totalMemory() / 1048576) + "M ("
			+ ((int) (((float) (runtime.totalMemory() - runtime.freeMemory()) / (float) runtime.totalMemory()) * 10000.0f) / 100) + "%)", 1.0f, fontType, new Transform3D(
				new Vector3f(0.005f, 0.035f, 0.0f)), 1.0f, false);

		Profiler.beginMultiSample("Frame Time");
		Profiler.beginMultiSample("Update Time");

		if (InputHandler.isButtonPressed(0)) {
			Mouse.setGrabbed(!Mouse.isGrabbed());
		}

		if (Mouse.isGrabbed()) {
			camera.rotate(Vector3f.Up(), InputHandler.getMouseDX() * 0.28f);
			camera.rotate(camera.getRight(), -InputHandler.getMouseDY() * 0.28f);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			camera.rotate(Vector3f.Up(), -1.0f);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			camera.rotate(Vector3f.Up(), 1.0f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			camera.rotate(camera.getRight(), -1.0f);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			camera.rotate(camera.getRight(), 1.0f);
		}

		final float ACCELERATION_SPEED = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? 0.005f : 0.002f;

		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			velocity_Camera.add(Vector3f.Up().scale(-ACCELERATION_SPEED));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			velocity_Camera.add(Vector3f.Up().scale(ACCELERATION_SPEED));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)) {
			velocity_Camera.add(camera.getRight().scale(-ACCELERATION_SPEED));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D) && !Keyboard.isKeyDown(Keyboard.KEY_A)) {
			velocity_Camera.add(camera.getRight().scale(ACCELERATION_SPEED));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S) && !Keyboard.isKeyDown(Keyboard.KEY_W)) {
			velocity_Camera.add(camera.getForward().projectToPlane(Vector3f.Up()).normalize().scale(-ACCELERATION_SPEED));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_W) && !Keyboard.isKeyDown(Keyboard.KEY_S)) {
			velocity_Camera.add(camera.getForward().projectToPlane(Vector3f.Up()).normalize().scale(ACCELERATION_SPEED));
		}

		camera.translate(Vector3f.Right(), velocity_Camera.getX());
		camera.translate(Vector3f.Up(), velocity_Camera.getY());
		camera.translate(Vector3f.Forward(), velocity_Camera.getZ());
		velocity_Camera.scale(0.95f);

		entity_Cube1.update(delta);
		entity_Bunny.update(delta);
		entity_Cube2.update(delta);
		entity_Dragon.update(delta);

		// entity_Torus.setMomentum(new Transform3D(Quaternion.AxisAngle(Vector3f.Right(), (float) Math.cos(Math.toRadians(angle * 2.0f)) * -1.8f * delta)));
		// entity_SphereOrbitter1.setMomentum(new Transform3D(Vector3f.Up().rotate(Vector3f.Right(), angle * 2).scale(0.1f * delta)));
		// entity_SphereOrbitter2.setMomentum(new Transform3D(Vector3f.Down().rotate(Vector3f.Right(), -angle * 2).scale(0.1f * delta)));

		entity_Torus.setTransform(new Transform3D(new Vector3f(15.0f, 0.0f, 0.0f), Quaternion.AxisAngle(Vector3f.Right(), (float) Math.cos(Math.toRadians(angle - 35.0f)) * 60.0f),
				new Vector3f(1.5f)));
		entity_SphereOrbitter1.setTransform(new Transform3D(new Vector3f(15.0f, (float) Math.cos(Math.toRadians(-angle - 90)) * 2.0f
			+ (float) Math.sin(Math.toRadians(-angle - 90)) * 2.0f, //
				(float) Math.cos(Math.toRadians(-angle - 90)) * 2.0f - (float) Math.sin(Math.toRadians(-angle - 90)) * 2.0f - 2.75f), new Vector3f(0.75f)));
		entity_SphereOrbitter2.setTransform(new Transform3D(new Vector3f(15.0f, (float) Math.cos(Math.toRadians(angle)) * 2.0f + (float) Math.sin(Math.toRadians(angle)) * 2.0f, //
				(float) Math.cos(Math.toRadians(angle)) * 2.0f - (float) Math.sin(Math.toRadians(angle)) * 2.0f + 2.75f), new Vector3f(0.75f)));

		entity_Torus.update(delta);
		entity_SphereOrbitter1.update(delta);
		entity_SphereOrbitter2.update(delta);

		Profiler.endSample("Update Time");

		angle -= delta;
	}

	public void render() {
		// VP_MATRIX = Matrix4f.Projection(0, Display.getWidth(), 0, Display.getHeight(), 0.1f, 1.0f);

		Profiler.beginMultiSample("Render Time");

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		Matrix4f viewProjectionMatrix = camera.getViewProjectionMatrix();

		texture_Checker.bind();

		shader.bind();

		shader.setUniform("FOXTAIL_MATRIX_VIEW", camera.getViewMatrix());

		shader.setUniform("in_AmbientLight", new Vector3f(1.0f, 0.95f, 0.8f).scale(0.1f));

		// Vector3f direction = Vector3f.Down().rotate(Vector3f.Right(), -45.0f).rotate(Vector3f.Up(), (angle / 10.0f));

		// shader.setUniform("in_DirectionalLight.base.color", new Vector3f(1.0f, 0.975f, 0.95f));
		shader.setUniform("in_DirectionalLight.base.color", new Vector3f(0.8f, 0.8f, 1.0f));
		shader.setUniform("in_DirectionalLight.base.intensity", 1.2f);
		shader.setUniform("in_DirectionalLight.direction", new Vector3f(-0.48269123f, -0.78886926f, 0.3803874f));

		shader.setUniform("in_PointLights[0].base.color", new Vector3f(0.0f, 0.1f, 1.0f));
		shader.setUniform("in_PointLights[0].base.intensity", 1.0f);
		shader.setUniform("in_PointLights[0].attenuation.constant", 0.0f);
		shader.setUniform("in_PointLights[0].attenuation.linear", 0.0f);
		shader.setUniform("in_PointLights[0].attenuation.exponent", 1.0f);
		shader.setUniform("in_PointLights[0].position", new Vector3f(15.0f, 3.0f, 0.0f));
		shader.setUniform("in_PointLights[0].range", 10.0f);

		shader.setUniform("in_PointLights[1].base.color", new Vector3f(0.0f, 0.1f, 1.0f));
		shader.setUniform("in_PointLights[1].base.intensity", 1.0f);
		shader.setUniform("in_PointLights[1].attenuation.constant", 0.0f);
		shader.setUniform("in_PointLights[1].attenuation.linear", 0.0f);
		shader.setUniform("in_PointLights[1].attenuation.exponent", 1.0f);
		shader.setUniform("in_PointLights[1].position", new Vector3f(15.0f, -3.0f, 0.0f));
		shader.setUniform("in_PointLights[1].range", 10.0f);

		shader.setUniform("in_PointLights[2].base.color", new Vector3f(0.0f, 1.0f, 0.0f));
		shader.setUniform("in_PointLights[2].base.intensity", 1.0f);
		shader.setUniform("in_PointLights[2].attenuation.constant", 0.0f);
		shader.setUniform("in_PointLights[2].attenuation.linear", 0.0f);
		shader.setUniform("in_PointLights[2].attenuation.exponent", 1.0f);
		shader.setUniform("in_PointLights[2].position", new Vector3f(0.0f, 0.0f, 5.0f + (float) Math.sin(Math.toRadians(angle)) * 4.0f));
		shader.setUniform("in_PointLights[2].range", 10.0f);

		Profiler.beginMultiSample("entity_Sphere");
		GL30.glBindVertexArray(entity_Spheres[0].getMeshID());
		for (int i = 0; i < entity_Spheres.length; i++) {
			// entity_Spheres[i].render(viewProjectionMatrix, shader);
			shader.setUniform("FOXTAIL_MATRIX_MODEL", entity_Spheres[i].getTransform().getTransformationMatrix());
			shader.setUniform("FOXTAIL_MATRIX_MVP", Matrix4f.multiply(viewProjectionMatrix, entity_Spheres[i].getTransform().getTransformationMatrix()));

			shader.setUniform("in_BaseColor", entity_Spheres[i].getMaterial().getColor());
			shader.setUniform("in_SpecularIntensity", entity_Spheres[i].getMaterial().getSpecularIntensity());
			shader.setUniform("in_SpecularExponent", entity_Spheres[i].getMaterial().getSpecularExponent());

//			GL30.glBindVertexArray(entity_Spheres[i].getMeshID());

			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			GL20.glEnableVertexAttribArray(3);

			GL11.glDrawElements(GL11.GL_TRIANGLES, entity_Spheres[i].getMeshVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}

		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);

		GL30.glBindVertexArray(0);
		Profiler.endSample("entity_Sphere");

		entity_Cube1.render(viewProjectionMatrix, shader);
		entity_Cube2.render(viewProjectionMatrix, shader);

		entity_Torus.render(viewProjectionMatrix, shader);
		entity_SphereOrbitter1.render(viewProjectionMatrix, shader);
		entity_SphereOrbitter2.render(viewProjectionMatrix, shader);

		texture_Blank.bind();

		entity_Bunny.render(viewProjectionMatrix, shader);
		entity_Dragon.render(viewProjectionMatrix, shader);
		Texture2D.unbind(1);

		texture_Perlin.bind();

		entity_Cube3.render(viewProjectionMatrix, shader);

		// GL11.glDisable(GL11.GL_DEPTH_TEST);
		skyboxTexture.bind();
		skyboxShader.bind();
		skyboxShader.setUniform("FOXTAIL_MATRIX_VP", viewProjectionMatrix);
		skyboxShader.setUniform("FOXTAIL_CAMERA_POSITION", camera.getPosition());
		GL30.glBindVertexArray(skyboxVAO);
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, CUBE_INDICES.length, GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		// GL11.glEnable(GL11.GL_DEPTH_TEST);

		TextManager.removeText(matrixText[0]);
		TextManager.removeText(matrixText[1]);
		TextManager.removeText(matrixText[2]);
		TextManager.removeText(matrixText[3]);
		String[] strings = camera.getViewMatrix().toFormattedString(true).split("\n");
		matrixText = new GUIText[] {
				new GUIText(strings[0], 0.75f, fontType, new Transform3D(new Vector3f(0.005f, 0.095f, 0.0f)), 1.0f, false),//
				new GUIText(strings[1], 0.75f, fontType, new Transform3D(new Vector3f(0.005f, 0.115f, 0.0f)), 1.0f, false),//
				new GUIText(strings[2], 0.75f, fontType, new Transform3D(new Vector3f(0.005f, 0.135f, 0.0f)), 1.0f, false),//
				new GUIText(strings[3], 0.75f, fontType, new Transform3D(new Vector3f(0.005f, 0.155f, 0.0f)), 1.0f, false)
		};

		TextManager.render();

		Profiler.endSample("Render Time");
		Profiler.endSample("Frame Time");
	}

	public static Matrix4f VP_MATRIX = Matrix4f.Identity();
}
