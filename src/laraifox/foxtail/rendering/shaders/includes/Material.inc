struct Material {
	vec4 baseColor;
	vec4 subsurfaceColor;
	vec4 emissiveColor;
	
	sampler2D diffuseTexture;
	sampler2D emissiveTexture;
	sampler2D normalTexture;
	
	sampler2D metalicTexture;
	sampler2D specularTexture;
	sampler2D roughnessTexture;
	
	sampler2D opacityTexture;
	sampler2D refrationTexture;
};

uniform Material FOXTAIL_MATERIAL;