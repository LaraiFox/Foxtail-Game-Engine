package laraifox.foxtail.rendering.lighting;

import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.rendering.Shader;

public class BaseLight {
	private Vector3f color;
	private float intensity;

	public BaseLight(Vector3f color, float intensity) {
		this.color = color;
		this.intensity = intensity;
	}

	public void bind(Shader shader) {
		this.bind(shader, "");
	}

	public void bind(Shader shader, String uniformName) {
		shader.setUniform(uniformName + ".color", color);
		shader.setUniform(uniformName + ".intensity", intensity);
	}
}
