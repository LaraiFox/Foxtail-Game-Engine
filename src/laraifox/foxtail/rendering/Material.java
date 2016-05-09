package laraifox.foxtail.rendering;

import laraifox.foxtail.core.math.Vector4f;

public class Material {
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
		this.baseColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.emissiveColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.subsurfaceColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

		this.diffuseTexture = new Texture2D(new Vector4f(1));
		this.emissiveTexture = new Texture2D();
		this.normalTexture = new Texture2D();

		this.metalicTexture = new Texture2D();
		this.specularTexture = new Texture2D();
		this.roughnessTexture = new Texture2D();

		this.opacityTexture = new Texture2D();
		this.refrationTexture = new Texture2D();
	}

	public void bind(Shader shader) {
		shader.setUniform("FOXTAIL_MATERIAL.baseColor", baseColor);
		shader.setUniform("FOXTAIL_MATERIAL.subsurfaceColor", subsurfaceColor);
		shader.setUniform("FOXTAIL_MATERIAL.emissiveColor", emissiveColor);

		shader.setUniform("FOXTAIL_MATERIAL.diffuseTexture", 0);
		shader.setUniform("FOXTAIL_MATERIAL.emissiveTexture", 1);
		shader.setUniform("FOXTAIL_MATERIAL.normalTexture", 2);

		shader.setUniform("FOXTAIL_MATERIAL.metalicTexture", 3);
		shader.setUniform("FOXTAIL_MATERIAL.specularTexture", 4);
		shader.setUniform("FOXTAIL_MATERIAL.roughnessTexture", 5);

		shader.setUniform("FOXTAIL_MATERIAL.opacityTexture", 6);
		shader.setUniform("FOXTAIL_MATERIAL.refrationTexture", 7);

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

	public Material setDiffuseTexture(Texture2D diffuseTexture) {
		this.diffuseTexture = diffuseTexture;
		return this;
	}

	public Material setEmissiveTexture(Texture2D emissiveTexture) {
		this.emissiveTexture = emissiveTexture;
		return this;
	}

	public Material setNormalTexture(Texture2D normalTexture) {
		this.normalTexture = normalTexture;
		return this;
	}

	public Material setMetalicTexture(Texture2D metalicTexture) {
		this.metalicTexture = metalicTexture;
		return this;
	}

	public Material setSpecularTexture(Texture2D specularTexture) {
		this.specularTexture = specularTexture;
		return this;
	}

	public Material setRoughnessTexture(Texture2D roughnessTexture) {
		this.roughnessTexture = roughnessTexture;
		return this;
	}

	public Material setOpacityTexture(Texture2D opacityTexture) {
		this.opacityTexture = opacityTexture;
		return this;
	}
}
