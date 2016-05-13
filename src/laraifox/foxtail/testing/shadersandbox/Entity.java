package laraifox.foxtail.testing.shadersandbox;

import laraifox.foxtail.core.Transform3D;
import laraifox.foxtail.core.math.Matrix4f;
import laraifox.foxtail.rendering.Shader;
import laraifox.foxtail.rendering.models.Mesh;

public class Entity {
	private Material material;
	private Mesh mesh;
	private Transform3D transform;
	private Transform3D momentum;

	public Entity(Material material, Mesh model, Transform3D transform, Transform3D momentum) {
		this.material = material;
		this.mesh = model;
		this.transform = transform;
		this.momentum = momentum;
	}

	public void update(float delta) {
		transform.transform(momentum);
	}

	public void render(Matrix4f viewProjectionMatrix, Shader shader) {
		shader.bind();

		shader.setUniform("FOXTAIL_MATRIX_MODEL", transform.getTransformationMatrix());
		shader.setUniform("FOXTAIL_MATRIX_MVP", Matrix4f.multiply(viewProjectionMatrix, transform.getTransformationMatrix()));

		shader.setUniform("in_BaseColor", material.getColor());
		shader.setUniform("in_SpecularIntensity", material.getSpecularIntensity());
		shader.setUniform("in_SpecularExponent", material.getSpecularExponent());

		mesh.render();
	}

	public void setMomentum(Transform3D momentum) {
		this.momentum = momentum;
	}

	public void setTransform(Transform3D transform) {
		this.transform = transform;
	}

	public int getMeshSize() {
		return mesh.getByteCount();
	}

	public int getMeshID() {
		return mesh.getID();
	}

	public Material getMaterial() {
		return material;
	}

	public Transform3D getTransform() {
		return transform;
	}

	public int getMeshVertexCount() {
		return mesh.count;
	}
}
