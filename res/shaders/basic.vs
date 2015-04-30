#version 120

attribute vec3 positionAttrib;

uniform mat4 ml_matrix;
uniform mat4 vw_matrix;
uniform mat4 pr_matrix;

void main() {
	gl_Position = pr_matrix * vw_matrix * ml_matrix * vec4(positionAttrib, 1.0);
}