package laraifox.foxtail.testing;

import laraifox.foxtail.AssetLoader;
import laraifox.foxtail.core.GameObject;
import laraifox.foxtail.core.IGameManager;
import laraifox.foxtail.core.InputHandler;
import laraifox.foxtail.core.Logger;
import laraifox.foxtail.core.OpenGLDisplay;
import laraifox.foxtail.core.Profiler;
import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.core.math.Vector4f;
import laraifox.foxtail.rendering.Camera;
import laraifox.foxtail.rendering.Material;
import laraifox.foxtail.rendering.RenderComponent;
import laraifox.foxtail.rendering.RenderingEngine;
import laraifox.foxtail.rendering.Shader;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class ComponentTesting implements IGameManager {
	private OpenGLDisplay display;

	private Camera camera;
	private Shader shader;

	private GameObject sphere1;
	private GameObject sphere2;

	private Vector3f velocity_Camera;

	public void initialize(OpenGLDisplay display) {
		Shader.logUnrecognizedUniformCalls = true;

		this.display = display;

		// PhysicsEngine.initialize();
		RenderingEngine.initialize();

		this.camera = new Camera(Matrix4f.Projection(70.0f, Display.getWidth(), Display.getHeight(), 0.01f, 100.0f));
		this.velocity_Camera = new Vector3f();

		RenderingEngine.setActiveCamera(camera);

		this.sphere1 = new GameObject(new Transform3D(new Vector3f(-1.0f, 0.0f, 5.0f)));
		// sphere1.addC\omponent(new PhysicsComponent(new Vector3f(1.0f, 0.0f, 0.0f), null));
		sphere1.addComponent(new RenderComponent(new Material().setBaseColor(new Vector4f(1, 0.5f, 0, 1)), AssetLoader.loadModel("res/models/Sphere.obj"), false));

		this.sphere2 = new GameObject(new Transform3D(new Vector3f(1.0f, 0.0f, 5.0f)));
		// sphere2.addComponent(new PhysicsComponent(new Vector3f(-1.0f, 0.0f, 0.0f), null));
		sphere2.addComponent(new RenderComponent(new Material().setBaseColor(new Vector4f(0, 0.5f, 1, 1)), AssetLoader.loadModel("res/models/Sphere.obj"), false));
	}

	public void cleanUp() {

	}

	public void tick() {
		Display.setTitle(display.getTitle() + " | FPS: " + display.getCurrentFPS());

		Profiler.logSamples();
		Logger.flush();
	}

	public void update(float delta) {
		InputHandler.update();

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

		// PhysicsEngine.simulate(1.0f / 60.0f);

		Profiler.endSample("Update Time");
	}

	public void render() {
		Profiler.beginMultiSample("Render Time");

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		RenderingEngine.render(1.0f);

		Profiler.endSample("Render Time");
		Profiler.endSample("Frame Time");
	}
}
