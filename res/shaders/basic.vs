#version 120

attribute vec3 positionAttrib;

void main() {
	gl_Position = vec4(positionAttrib, 1.0);
}