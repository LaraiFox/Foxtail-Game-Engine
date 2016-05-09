package laraifox.foxtail.rendering;

import org.lwjgl.opengl.GL20;

import laraifox.foxtail.core.GameComponent;
import laraifox.foxtail.core.GameObject;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.rendering.models.Mesh;

public class RenderComponent extends GameComponent {
	protected Material material;
	protected Mesh mesh;

	protected boolean isStatic;

	public RenderComponent(Material material, Mesh mesh, boolean isStatic) {
		this.material = material;
		this.mesh = mesh;

		this.isStatic = isStatic;
	}

	@Override
	public void onComponentAdded(GameObject owner) {
		super.onComponentAdded(owner);
		RenderingEngine.addRenderComponent(this);
	}

	public void render(Shader shader, float delta) {
		Matrix4f FOXTAIL_MATRIX_MODEL = owner.getInterpolatedTransform(delta).getTransformationMatrix();
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

		material.bind(shader);

		mesh.draw();
	}

	public boolean isStatic() {
		return isStatic;
	}

	public int getMeshID() {
		return mesh.getID();
	}
}
