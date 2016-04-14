#version 330

uniform mat4 FOXTAIL_MODEL_MATRIX;
uniform mat4 FOXTAIL_VIEW_MATRIX;
uniform mat4 FOXTAIL_MVP_MATRIX;

layout(location = 0) in vec3 in_VertexPosition;
layout(location = 2) in vec3 in_VertexNormal;

out vec3 pass_ToCameraVector;
out vec3 pass_ToLightVector;
out vec3 pass_VertexNormal;

const vec3 LIGHT_POSITION = vec3(0.0, 0.0, 1.0);

void main() {
	vec3 worldVertexPosition = (FOXTAIL_MODEL_MATRIX * vec4(in_VertexPosition, 1.0)).xyz;

	pass_ToCameraVector = normalize((inverse(FOXTAIL_VIEW_MATRIX) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldVertexPosition);
	pass_ToLightVector = normalize(LIGHT_POSITION - worldVertexPosition);
	pass_VertexNormal = normalize((FOXTAIL_MODEL_MATRIX * vec4(in_VertexNormal, 0.0)).xyz);

	gl_Position = FOXTAIL_MVP_MATRIX * vec4(in_VertexPosition, 1.0);
}