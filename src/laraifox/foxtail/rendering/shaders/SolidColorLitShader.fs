#version 330

uniform vec4 in_Color;
uniform float in_Reflectivity;
uniform float in_ShineDamper;

in vec3 pass_ToCameraVector;
in vec3 pass_ToLightVector;
in vec3 pass_VertexNormal;

layout(location = 0) out vec4 out_FragColor;

const float AMBIENT_LIGHT_LEVEL = 0.1;

void main() {
	vec3 reflectedLightDirection = normalize(reflect(-pass_ToLightVector, pass_VertexNormal));
	
	float diffuseLight = max(dot(pass_VertexNormal, pass_ToLightVector), AMBIENT_LIGHT_LEVEL);
	float specularLight = max(dot(reflectedLightDirection, pass_ToCameraVector), 0.0);
	specularLight = pow(specularLight, in_ShineDamper);
	vec3 specularColor = specularLight * in_Reflectivity * vec3(1.0, 0.5, 0.5);

	out_FragColor = in_Color * diffuseLight + vec4(specularColor, 1.0);
}