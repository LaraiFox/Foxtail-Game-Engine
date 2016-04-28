package laraifox.foxtail.testing.shadersandbox;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import laraifox.foxtail.core.IGameManager;
import laraifox.foxtail.core.Logger;
import laraifox.foxtail.core.OpenGLDisplay;
import laraifox.foxtail.core.Profiler;
import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.Quaternion;
import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.core.math.Vector4f;
import laraifox.foxtail.rendering.Camera;
import laraifox.foxtail.rendering.Shader;
import laraifox.foxtail.rendering.Texture2D;
import laraifox.foxtail.rendering.TextureFilter;
import laraifox.foxtail.rendering.models.Model;
import laraifox.foxtail.rendering.models.ModelLoader;

public class ShaderSandbox implements IGameManager {
	private OpenGLDisplay display;

	private static final float[] CUBE_VERTICES = new float[] {
			// LEFT SIDE VERTICES { 0, 1, 2, 0, 2, 2 }
			-0.5f, -0.5f, 0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (0)
			-0.5f, -0.5f, -0.5f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (1)
			-0.5f, 0.5f, -0.5f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (2)
			-0.5f, 0.5f, 0.5f, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (3)
			// RIGHT SIDE VERTICES
			0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (4)
			0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (5)
			0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (6)
			0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (7)
			// BOTTOM SIDE VERTICES
			-0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (8)
			0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (9)
			0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (10)
			-0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (11)
			// TOP SIDE VERTICES
			-0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (12)
			0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (13)
			0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (14)
			-0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, // (15)
			// BACK SIDE VERTICES
			-0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, // (16)
			0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, // (17)
			0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, // (18)
			-0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, // (19)
			// FRONT SIDE VERTICES
			0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, // (20)
			-0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, // (21)
			-0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, // (22)
			0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, // (23)
	};
	private static final int[] CUBE_INDICES = new int[] {
			0, 1, 2, 0, 2, 3, // LEFT FACE
			4, 5, 6, 4, 6, 7, // RIGHT FACE
			8, 9, 10, 8, 10, 11, // BOTTOM FACE
			12, 13, 14, 12, 14, 15, // TOP FACE
			16, 17, 18, 16, 18, 19, // BACK FACE
			20, 21, 22, 20, 22, 23, // FRONT FACE
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

	private Shader shader;
	private Vector3f velocity_Camera;

	public void initialize(OpenGLDisplay display) {
		this.display = display;

		this.camera = new Camera(new Transform3D(new Vector3f(0.0f, 1.0f, 0.0f)), Matrix4f.Projection(70.0f, Display.getWidth(), Display.getHeight(), 0.01f, 100.0f));

		final int SPHERE_COUNT = 10;
		this.entity_Spheres = new Entity[SPHERE_COUNT * 2];
		for (int i = 0; i < SPHERE_COUNT; i++) {
			entity_Spheres[i * 2 + 0] = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), (1.0f / (SPHERE_COUNT - 1) * i * 10.0f), 64.0f), ModelLoader.loadMesh("res/models/Sphere.obj"),
					new Transform3D(new Vector3f(-10.0f + (20.0f / (SPHERE_COUNT - 1) * i), 1.0f, -4.0f), new Vector3f(0.75f)), new Transform3D());
			entity_Spheres[i * 2 + 1] = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), (1.0f / (SPHERE_COUNT - 1) * i * 10.0f), 256.0f), ModelLoader.loadMesh("res/models/Sphere.obj"),
					new Transform3D(new Vector3f(-10.0f + (20.0f / (SPHERE_COUNT - 1) * i), -1.0f, -4.0f), new Vector3f(0.75f)), new Transform3D());
		}

		this.entity_Bunny = new Entity(new Material(new Vector4f(1.0f, 0.64f, 0.39f, 1.0f), 0.5f, 5.0f), ModelLoader.loadMesh("res/models/StanfordBunny.obj"), new Transform3D(new Vector3f(-4.0f,
				-0.5f, 5.0f), new Vector3f(0.3f, 0.3f, 0.3f)), new Transform3D(Quaternion.AxisAngle(Vector3f.Up(), 0.2f)));
		this.entity_Dragon = new Entity(new Material(new Vector4f(1.0f, 0.64f, 0.39f, 1.0f), 0.5f, 5.0f), ModelLoader.loadMesh("res/models/StanfordDragon.obj"), new Transform3D(new Vector3f(4.0f,
				-0.5f, 5.0f), new Vector3f(0.3f, 0.3f, 0.3f)), new Transform3D(Quaternion.AxisAngle(Vector3f.Up(), 0.2f)));

		this.entity_Cube1 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 0.5f, 1.0f), new Model(CUBE_VERTICES, CUBE_INDICES), new Transform3D(new Vector3f(-4.0f, -0.75f, 5.0f),
				new Vector3f(5.0f, 0.5f, 5.0f)), new Transform3D());
		this.entity_Cube2 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 0.5f, 1.0f), new Model(CUBE_VERTICES, CUBE_INDICES), new Transform3D(new Vector3f(4.0f, -0.75f, 5.0f),
				new Vector3f(5.0f, 0.5f, 5.0f)), new Transform3D());
		this.entity_Cube3 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 0.5f, 1.0f), new Model(CUBE_VERTICES, CUBE_INDICES), new Transform3D(new Vector3f(0.0f, -10.0f, 0.0f),
				new Vector3f(50.0f, 0.5f, 50.0f)), new Transform3D());

		entity_Torus = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 1.0f, 256.0f), ModelLoader.loadMesh("res/models/Torus.obj"), new Transform3D(new Vector3f(15.0f, 0.0f, 0.0f),
				new Vector3f(1.5f)), new Transform3D());
		entity_SphereOrbitter1 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 5.0f, 256.0f), ModelLoader.loadMesh("res/models/Sphere.obj"), new Transform3D(new Vector3f(15.0f, 0.0f,
				0.0f), new Vector3f(0.75f)), new Transform3D());
		entity_SphereOrbitter2 = new Entity(new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 5.0f, 256.0f), ModelLoader.loadMesh("res/models/Sphere.obj"), new Transform3D(new Vector3f(15.0f, 0.0f,
				5.75f), new Vector3f(0.75f)), new Transform3D());

		TextureFilter textureFilter = new TextureFilter();
		textureFilter.setGLTextureMinFilter(GL11.GL_NEAREST_MIPMAP_LINEAR);
		textureFilter.setGLTextureMagFilter(GL11.GL_NEAREST);
		textureFilter.setGLTextureAnisotropy(2.0f);

		this.texture_Checker = new Texture2D("res/textures/GreyscaleCheckerBoard.png", textureFilter);
		this.texture_Blank = new Texture2D("res/textures/Blank.png", textureFilter);

		try {
			this.shader = new Shader("src/laraifox/foxtail/rendering/shaders/TexturedPhong.shader", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.velocity_Camera = new Vector3f();

		GL11.glClearColor(0.5f, 0.5f, 1.0f, 1.0f);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL13.GL_MULTISAMPLE);
	}

	public void cleanUp() {

	}

	public void tick() {
		Display.setTitle(display.getTitle() + " | FPS: " + display.getCurrentFPS());

		Profiler.logSamples();
		
		Logger.flush();
	}

	public void update(float delta) {
		Profiler.beginMultiSample("Frame Time");
		Profiler.beginMultiSample("Update Time");

		if (Mouse.isGrabbed()) {
			camera.rotate(Vector3f.Up(), Mouse.getDX() * 0.28f);
			camera.rotate(camera.getRight(), -Mouse.getDY() * 0.28f);
		}

		Mouse.setGrabbed(Mouse.isButtonDown(0));

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

		//		entity_Torus.setMomentum(new Transform3D(Quaternion.AxisAngle(Vector3f.Right(), (float) Math.cos(Math.toRadians(angle * 2.0f)) * -1.8f * delta)));
		//		entity_SphereOrbitter1.setMomentum(new Transform3D(Vector3f.Up().rotate(Vector3f.Right(), angle * 2).scale(0.1f * delta)));
		//		entity_SphereOrbitter2.setMomentum(new Transform3D(Vector3f.Down().rotate(Vector3f.Right(), -angle * 2).scale(0.1f * delta)));

		entity_Torus.setTransform(new Transform3D(new Vector3f(15.0f, 0.0f, 0.0f), Quaternion.AxisAngle(Vector3f.Right(), (float) Math.cos(Math.toRadians(angle - 35.0f)) * 60.0f), new Vector3f(
				1.5f)));
		entity_SphereOrbitter1.setTransform(new Transform3D(new Vector3f(15.0f, (float) Math.cos(Math.toRadians(-angle - 90)) * 2.0f + (float) Math.sin(Math.toRadians(-angle - 90)) * 2.0f, //
				(float) Math.cos(Math.toRadians(-angle - 90)) * 2.0f - (float) Math.sin(Math.toRadians(-angle - 90)) * 2.0f - 2.75f), new Vector3f(0.75f)));
		entity_SphereOrbitter2.setTransform(new Transform3D(new Vector3f(15.0f, (float) Math.cos(Math.toRadians(angle)) * 2.0f + (float) Math.sin(Math.toRadians(angle)) * 2.0f, //
				(float) Math.cos(Math.toRadians(angle)) * 2.0f - (float) Math.sin(Math.toRadians(angle)) * 2.0f + 2.75f), new Vector3f(0.75f)));

		entity_Torus.update(delta);
		entity_SphereOrbitter1.update(delta);
		entity_SphereOrbitter2.update(delta);

		Profiler.endSample("Update Time");

		angle -= delta;
	}

	private float angle = 0;

	public void render() {
		Profiler.beginMultiSample("Render Time");

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		texture_Checker.bind();

		shader.bind();

		shader.setUniform("FOXTAIL_VIEW_MATRIX", camera.getViewMatrix());

		shader.setUniform("in_AmbientLight", new Vector3f(1.0f, 0.95f, 0.8f).scale(0.1f));

		Vector3f direction = Vector3f.Down().rotate(Vector3f.Right(), -45.0f).rotate(Vector3f.Up(), (angle / 10.0f));

		//		shader.setUniform("in_DirectionalLight.base.color", new Vector3f(1.0f, 0.975f, 0.95f));
		shader.setUniform("in_DirectionalLight.base.color", new Vector3f(0.8f, 0.8f, 1.0f));
		shader.setUniform("in_DirectionalLight.base.intensity", 1.2f);
		shader.setUniform("in_DirectionalLight.direction", direction);

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

		Matrix4f viewProjectionMatrix = camera.getViewProjectionMatrix();

		for (int i = 0; i < entity_Spheres.length; i++) {
			entity_Spheres[i].render(viewProjectionMatrix, shader);
		}

		entity_Cube1.render(viewProjectionMatrix, shader);
		entity_Cube2.render(viewProjectionMatrix, shader);

		entity_Torus.render(viewProjectionMatrix, shader);
		entity_SphereOrbitter1.render(viewProjectionMatrix, shader);
		entity_SphereOrbitter2.render(viewProjectionMatrix, shader);

		texture_Blank.bind();

		entity_Bunny.render(viewProjectionMatrix, shader);
		entity_Dragon.render(viewProjectionMatrix, shader);

		entity_Cube3.render(viewProjectionMatrix, shader);

		Profiler.endSample("Render Time");
		Profiler.endSample("Frame Time");
	}
}
