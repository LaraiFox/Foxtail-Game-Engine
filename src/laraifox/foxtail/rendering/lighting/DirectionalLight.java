package laraifox.foxtail.rendering.lighting;

import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.rendering.Shader;

public class DirectionalLight {
	private BaseLight baseLight;
	private Vector3f direction;

	public DirectionalLight(Vector3f color, float intensity, Vector3f direction) {
		this(new BaseLight(color, intensity), direction);
	}

	public DirectionalLight(BaseLight baseLight, Vector3f direction) {
		this.baseLight = baseLight;
		this.direction = direction;
	}

	public void bind(Shader shader) {
		this.bind(shader, "");
	}

	public void bind(Shader shader, String uniformName) {
		baseLight.bind(shader, uniformName + ".baseLight");

		shader.setUniform(uniformName + ".direction", direction);
	}
}
