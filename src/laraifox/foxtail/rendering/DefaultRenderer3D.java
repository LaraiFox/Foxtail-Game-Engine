package laraifox.foxtail.rendering;

import java.util.Map;

import laraifox.foxtail.core.ResourceManager;
import laraifox.foxtail.core.math.Matrix4f;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class DefaultRenderer3D extends Renderer {
	private Shader shader;

	public DefaultRenderer3D() {
		this.shader = new Shader(ResourceManager.getFoxtailResourcePath("shaders/StandardComponent.shader"));
	}

	@Override
	public void initialize() {

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
				Matrix4f FOXTAIL_MATRIX_MODEL = component.getGameObject().getInterpolatedTransform(delta).getTransformationMatrix();
				Matrix4f FOXTAIL_MATRIX_I_MODEL = Matrix4f.inverse(FOXTAIL_MATRIX_MODEL);
				Matrix4f FOXTAIL_MATRIX_MV = Matrix4f.multiply(RenderingEngine.getCameraViewMatrix(), FOXTAIL_MATRIX_MODEL);
				Matrix4f FOXTAIL_MATRIX_T_MV = Matrix4f.transpose(FOXTAIL_MATRIX_MV);
				Matrix4f FOXTAIL_MATRIX_IT_MV = Matrix4f.inverse(FOXTAIL_MATRIX_T_MV);
				Matrix4f FOXTAIL_MATRIX_MVP = Matrix4f.multiply(RenderingEngine.getCameraViewProjectionMatrix(), FOXTAIL_MATRIX_MODEL);

				shader.setUniform("FOXTAIL_MATRIX_MODEL", FOXTAIL_MATRIX_MODEL);

				shader.setUniform("FOXTAIL_MATRIX_MVP", FOXTAIL_MATRIX_MVP);
				shader.setUniform("FOXTAIL_MATRIX_I_MODEL", FOXTAIL_MATRIX_I_MODEL);
				shader.setUniform("FOXTAIL_MATRIX_MV", FOXTAIL_MATRIX_MV);
				shader.setUniform("FOXTAIL_MATRIX_T_MV", FOXTAIL_MATRIX_T_MV);
				shader.setUniform("FOXTAIL_MATRIX_IT_MV", FOXTAIL_MATRIX_IT_MV);

				GL20.glEnableVertexAttribArray(0);
				GL20.glEnableVertexAttribArray(1);
				GL20.glEnableVertexAttribArray(2);
				GL20.glEnableVertexAttribArray(3);

				component.getMaterial().bind(shader);

				component.getMesh().draw();
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
