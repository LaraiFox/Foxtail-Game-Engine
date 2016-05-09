package laraifox.foxtail.rendering;

import java.util.Map;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class DefaultRenderer3D extends Renderer {
	private Shader shader;

	public DefaultRenderer3D() {
		this.shader = new Shader("src/laraifox/foxtail/rendering/shaders/TexturedPhong.shader");
	}

	@Override
	public void initialize() {

	}

	public void cleanUp() {
		shader.cleanUp();
	}

	@Override
	public void render(Camera camera, Map<Integer, RenderBatch> renderBatchMap, float delta) {
		shader.bind();

		shader.setUniform("FOXTAIL_MATRIX_VIEW", RenderingEngine.getCameraViewMatrix());
		shader.setUniform("FOXTAIL_MATRIX_PROJECTION", RenderingEngine.getCameraProjectionMatrix());

		shader.setUniform("FOXTAIL_MATRIX_VP", RenderingEngine.getCameraViewProjectionMatrix());

		shader.setUniform("FOXTAIL_CAMERA_POSITION", RenderingEngine.getCameraPosition());

		for (Integer meshID : renderBatchMap.keySet()) {
			GL30.glBindVertexArray(meshID);

			RenderBatch batch = renderBatchMap.get(meshID);
			for (RenderComponent component : batch.getStaticComponents()) {
				component.render(shader, delta);
			}

			for (RenderComponent component : batch.getDynamicComponents()) {
				component.render(shader, delta);
			}
		}

		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);

		GL30.glBindVertexArray(0);
	}

}
