package laraifox.foxtail.testing.shadersandbox;

import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.rendering.Shader;
import laraifox.foxtail.rendering.models.Model;

public class Entity {
	private Material material;
	private Model model;
	private Transform3D transform;
	private Transform3D momentum;

	public Entity(Material material, Model model, Transform3D transform, Transform3D momentum) {
		this.material = material;
		this.model = model;
		this.transform = transform;
		this.momentum = momentum;
	}

	public void update(float delta) {
		transform.transform(momentum);
	}

	public void render(Matrix4f viewProjectionMatrix, Shader shader) {
		shader.bind();

		shader.setUniform("FOXTAIL_MODEL_MATRIX", transform.getTransformationMatrix());
		shader.setUniform("FOXTAIL_MVP_MATRIX", viewProjectionMatrix.multiply(transform.getTransformationMatrix()));

		shader.setUniform("in_BaseColor", material.getColor());
		shader.setUniform("in_SpecularIntensity", material.getSpecularIntensity());
		shader.setUniform("in_SpecularExponent", material.getSpecularExponent());

		model.render();
	}

	public void setMomentum(Transform3D momentum) {
		this.momentum = momentum;
	}
}
