#version 120

attribute vec3 positionAttrib;

const float DENSITY = 0.07 / 10;
const float GRADIENT = 1.5;

uniform mat4 ml_matrix;
uniform mat4 vw_matrix;
uniform mat4 pr_matrix;

varying vec3 v_position;
varying float v_visibility;

void main() {
	vec4 worldPosition = ml_matrix * vec4(positionAttrib, 1.0);
	vec4 viewWorldPosition = vw_matrix * worldPosition;
	
	v_position = worldPosition.xyz;
	
	float distance = length(viewWorldPosition.xyz);
	v_visibility = clamp(exp(-pow(distance * DENSITY, GRADIENT)), 0.0, 1.0);
	
	gl_Position = pr_matrix * viewWorldPosition;
}