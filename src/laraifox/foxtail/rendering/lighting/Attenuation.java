package laraifox.foxtail.rendering.lighting;

import laraifox.foxtail.rendering.Shader;

public class Attenuation {
	private float constant;
	private float linear;
	private float exponent;
	
	public Attenuation(float constant, float linear, float exponent) {
		this.constant = constant;
		this.linear = linear;
		this.exponent = exponent;
	}

	public void bind(Shader shader) {
		this.bind(shader, "");
	}

	public void bind(Shader shader, String uniformName) {
		shader.setUniform(uniformName + ".constant", constant);
		shader.setUniform(uniformName + ".linear", linear);
		shader.setUniform(uniformName + ".exponent", exponent);
	}
}
