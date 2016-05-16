package laraifox.foxtail.rendering;

import laraifox.foxtail.core.math.Vector4f;

public class Material {
	public static final String FOXTAIL_DEFAULT_UNIFORM_NAME = new String("FOXTAIL_MATERIAL");

	private Vector4f baseColor;
	private Vector4f subsurfaceColor;
	private Vector4f emissiveColor;

	private Texture2D diffuseTexture;
	private Texture2D emissiveTexture;
	private Texture2D normalTexture;

	private Texture2D metalicTexture;
	private Texture2D specularTexture;
	private Texture2D roughnessTexture;

	private Texture2D opacityTexture;
	private Texture2D refrationTexture;

	public Material() {
		this(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),//
				new Texture2D(new Vector4f(1.0f)), new Texture2D(), new Texture2D(),//
				new Texture2D(), new Texture2D(), new Texture2D(), //
				new Texture2D(), new Texture2D());
	}

	public Material(Vector4f baseColor, Vector4f subsurfaceColor, Vector4f emissiveColor, Texture2D diffuseTexture, Texture2D emissiveTexture, Texture2D normalTexture, Texture2D metalicTexture,
			Texture2D specularTexture, Texture2D roughnessTexture, Texture2D opacityTexture, Texture2D refrationTexture) {
		this.baseColor = baseColor;
		this.subsurfaceColor = subsurfaceColor;
		this.emissiveColor = emissiveColor;
		this.diffuseTexture = diffuseTexture;
		this.emissiveTexture = emissiveTexture;
		this.normalTexture = normalTexture;
		this.metalicTexture = metalicTexture;
		this.specularTexture = specularTexture;
		this.roughnessTexture = roughnessTexture;
		this.opacityTexture = opacityTexture;
		this.refrationTexture = refrationTexture;
	}

	public void bind(Shader shader) {
		this.bind(shader, FOXTAIL_DEFAULT_UNIFORM_NAME);
	}

	public void bind(Shader shader, String uniformName) {
		shader.setUniform(uniformName + ".baseColor", baseColor);
		shader.setUniform(uniformName + ".subsurfaceColor", subsurfaceColor);
		shader.setUniform(uniformName + ".emissiveColor", emissiveColor);

		shader.setUniform(uniformName + ".diffuseTexture", 0);
		shader.setUniform(uniformName + ".emissiveTexture", 1);
		shader.setUniform(uniformName + ".normalTexture", 2);

		shader.setUniform(uniformName + ".metalicTexture", 3);
		shader.setUniform(uniformName + ".specularTexture", 4);
		shader.setUniform(uniformName + ".roughnessTexture", 5);

		shader.setUniform(uniformName + ".opacityTexture", 6);
		shader.setUniform(uniformName + ".refrationTexture", 7);

		diffuseTexture.bind(0);
		emissiveTexture.bind(1);
		normalTexture.bind(2);

		metalicTexture.bind(3);
		specularTexture.bind(4);
		roughnessTexture.bind(5);

		opacityTexture.bind(6);
		refrationTexture.bind(7);
	}

	public Vector4f getBaseColor() {
		return baseColor;
	}

	public Vector4f getSubsurfaceColor() {
		return subsurfaceColor;
	}

	public Vector4f getEmissiveColor() {
		return emissiveColor;
	}

	public Texture2D getDiffuseTexture() {
		return diffuseTexture;
	}

	public Texture2D getEmissiveTexture() {
		return emissiveTexture;
	}

	public Texture2D getNormalTexture() {
		return normalTexture;
	}

	public Texture2D getMetalicTexture() {
		return metalicTexture;
	}

	public Texture2D getSpecularTexture() {
		return specularTexture;
	}

	public Texture2D getRoughnessTexture() {
		return roughnessTexture;
	}

	public Texture2D getOpacityTexture() {
		return opacityTexture;
	}

	public Material setBaseColor(Vector4f baseColor) {
		this.baseColor = baseColor;
		return this;
	}

	public Material setSubsurfaceColor(Vector4f subsurfaceColor) {
		this.subsurfaceColor = subsurfaceColor;
		return this;
	}

	public Material setEmissiveColor(Vector4f emissiveColor) {
		this.emissiveColor = emissiveColor;
		return this;
	}

	public Material setDiffuseTexture(Vector4f diffuseColor) {
		this.diffuseTexture = new Texture2D(diffuseColor);
		return this;
	}

	public Material setDiffuseTexture(Texture2D diffuseTexture) {
		this.diffuseTexture = diffuseTexture;
		return this;
	}

	public Material setEmissiveTexture(Vector4f emissiveColor) {
		this.emissiveTexture = new Texture2D(emissiveColor);
		return this;
	}

	public Material setEmissiveTexture(Texture2D emissiveTexture) {
		this.emissiveTexture = emissiveTexture;
		return this;
	}

	public Material setNormalTexture(Vector4f normalColor) {
		this.normalTexture = new Texture2D(normalColor);
		return this;
	}

	public Material setNormalTexture(Texture2D normalTexture) {
		this.normalTexture = normalTexture;
		return this;
	}

	public Material setMetalicTexture(Vector4f metalicColor) {
		this.metalicTexture = new Texture2D(metalicColor);
		return this;
	}

	public Material setMetalicTexture(Texture2D metalicTexture) {
		this.metalicTexture = metalicTexture;
		return this;
	}

	public Material setSpecularTexture(Vector4f specularColor) {
		this.specularTexture = new Texture2D(specularColor);
		return this;
	}

	public Material setSpecularTexture(Texture2D specularTexture) {
		this.specularTexture = specularTexture;
		return this;
	}

	public Material setRoughnessTexture(Vector4f roughnessColor) {
		this.roughnessTexture = new Texture2D(roughnessColor);
		return this;
	}

	public Material setRoughnessTexture(Texture2D roughnessTexture) {
		this.roughnessTexture = roughnessTexture;
		return this;
	}

	public Material setOpacityTexture(Vector4f opacityColor) {
		this.opacityTexture = new Texture2D(opacityColor);
		return this;
	}

	public Material setOpacityTexture(Texture2D opacityTexture) {
		this.opacityTexture = opacityTexture;
		return this;
	}
}
