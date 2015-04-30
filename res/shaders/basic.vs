#version 120

attribute vec3 positionAttrib;

varying vec3 v_position;

uniform mat4 ml_matrix;
uniform mat4 vw_matrix;
uniform mat4 pr_matrix;

void main() {
	vec4 worldPosition = ml_matrix * vec4(positionAttrib, 1.0);
	
	gl_Position = vw_matrix * pr_matrix * ml_matrix * vec4(positionAttrib, 1.0);
	
	v_position = worldPosition.xyz;
}