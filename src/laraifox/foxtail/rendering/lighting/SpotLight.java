package laraifox.foxtail.rendering.lighting;

import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.rendering.Shader;

public class SpotLight {
	private PointLight pointLight;
	private Vector3f direction;
	private float cutoff;

	public SpotLight(Vector3f color, float intensity, float constant, float linear, float exponent, Vector3f position, float range, Vector3f direction, float cutoff) {
		this(new PointLight(color, intensity, constant, linear, exponent, position, range), direction, cutoff);
	}

	public SpotLight(PointLight pointLight, Vector3f direction, float cutoff) {
		this.pointLight = pointLight;
		this.direction = direction;
		this.cutoff = cutoff;
	}

	public void bind(Shader shader) {
		this.bind(shader, "");
	}

	public void bind(Shader shader, String uniformName) {
		pointLight.bind(shader, uniformName + ".pointLight");

		shader.setUniform(uniformName + ".direction", direction);
		shader.setUniform(uniformName + ".cutoff", cutoff);
	}
}
