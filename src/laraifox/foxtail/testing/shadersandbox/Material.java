package laraifox.foxtail.testing.shadersandbox;

import laraifox.foxtail.core.math.Vector4f;

public class Material {
	// private Vector4f baseColor;
	// private Vector4f emissiveColor;
	//
	// private Texture2D diffuseTexture;
	// private Texture2D emissiveTexture;
	// private Texture2D normalTexture;
	//
	// private float metalic;
	// private float specular;
	// private float roughness;
	//
	// private float opacity;
	// private float opacitySubSurfaceMaterial;
	// private float opacityMask;
	
	private Vector4f color;
	private float specularIntensity;
	private float specularExponent;

	public Material(Vector4f color, float specularIntensity, float specularExponent) {
		this.color = color;
		this.specularIntensity = specularIntensity;
		this.specularExponent = specularExponent;
	}

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}

	public float getSpecularIntensity() {
		return specularIntensity;
	}

	public void setSpecularIntensity(float specularIntensity) {
		this.specularIntensity = specularIntensity;
	}

	public float getSpecularExponent() {
		return specularExponent;
	}

	public void setSpecularExponent(float specularExponent) {
		this.specularExponent = specularExponent;
	}
}
