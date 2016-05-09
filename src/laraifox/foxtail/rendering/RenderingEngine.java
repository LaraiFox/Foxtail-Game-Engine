package laraifox.foxtail.rendering;

import java.util.HashMap;
import java.util.Map;

import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.core.math.Vector3f;

public class RenderingEngine {
	private static final Map<Integer, RenderBatch> RENDER_BATCH_MAP = new HashMap<Integer, RenderBatch>();

	private static Renderer renderer;

	private static Camera activeCamera;
	private static Matrix4f cameraViewMatrix;
	private static Matrix4f cameraViewProjectionMatrix;

	private RenderingEngine() {

	}

	public static void initialize() {
		RenderingEngine.initialize(new DefaultRenderer3D());
	}

	public static void initialize(Renderer renderer) {
		renderer.initialize();
		RenderingEngine.renderer = renderer;
	}

	public static void addRenderComponent(RenderComponent component) {
		Integer componentMeshID = component.getMeshID();
		RenderBatch renderBatch = RENDER_BATCH_MAP.get(componentMeshID);
		if (renderBatch == null) {
			renderBatch = new RenderBatch();
			RENDER_BATCH_MAP.put(componentMeshID, renderBatch);
		}
		renderBatch.addRenderComponent(component);
	}

	public static void removeRenderComponent(RenderComponent component) {
		int componentMeshID = component.getMeshID();
		RenderBatch renderBatch = RENDER_BATCH_MAP.get(componentMeshID);
		if (renderBatch != null) {
			if (renderBatch.removeRenderComponent(component)) {
				RENDER_BATCH_MAP.remove(componentMeshID);
			}
		}
	}

	public static void render(float delta) {
		RenderingEngine.updateCameraMatrices();
		RenderingEngine.renderer.render(activeCamera, RENDER_BATCH_MAP, delta);
	}

	public static void setActiveCamera(Camera camera) {
		RenderingEngine.activeCamera = camera;
		RenderingEngine.updateCameraMatrices();
	}

	public static void updateCameraMatrices() {
		RenderingEngine.cameraViewMatrix = RenderingEngine.activeCamera.getViewMatrix();
		RenderingEngine.cameraViewProjectionMatrix = RenderingEngine.activeCamera.getViewProjectionMatrix();
	}

	public static Vector3f getCameraPosition() {
		return RenderingEngine.activeCamera.getPosition();
	}

	public static Matrix4f getCameraViewMatrix() {
		return cameraViewMatrix;
	}

	public static Matrix4f getCameraProjectionMatrix() {
		return activeCamera.getProjectionMatrix();
	}

	public static Matrix4f getCameraViewProjectionMatrix() {
		return cameraViewProjectionMatrix;
	}
}
