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
	private float reflectivity;
	private float roughness;

	public Material(Vector4f color, float reflectivity, float roughness) {
		this.color = color;
		this.reflectivity = reflectivity;
		this.roughness = roughness;
	}

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public float getRoughness() {
		return roughness;
	}

	public void setRoughness(float roughness) {
		this.roughness = roughness;
	}
}
