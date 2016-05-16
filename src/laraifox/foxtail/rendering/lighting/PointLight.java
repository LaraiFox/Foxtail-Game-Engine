package laraifox.foxtail.rendering.lighting;

import laraifox.foxtail.core.math.Vector3f;
import laraifox.foxtail.rendering.Shader;

public class PointLight {
	private BaseLight baseLight;
	private Attenuation attenuation;
	private Vector3f position;
	private float range;

	public PointLight(Vector3f color, float intensity, float constant, float linear, float exponent, Vector3f position, float range) {
		this(new BaseLight(color, intensity), new Attenuation(constant, linear, exponent), position, range);
	}

	public PointLight(BaseLight baseLight, Attenuation attenuation, Vector3f position, float range) {
		this.baseLight = baseLight;
		this.attenuation = attenuation;
		this.position = position;
		this.range = range;
	}

	public void bind(Shader shader) {
		this.bind(shader, "");
	}

	public void bind(Shader shader, String uniformName) {
		baseLight.bind(shader, uniformName + ".baseLight");
		attenuation.bind(shader, uniformName + ".attenuation");

		shader.setUniform(uniformName + ".position", position);
		shader.setUniform(uniformName + ".range", range);
	}
}
