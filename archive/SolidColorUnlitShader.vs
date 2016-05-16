#version 330

uniform mat4 FOXTAIL_MVP_MATRIX;

layout(location = 0) in vec3 in_VertexPosition;

void main() {
	gl_Position = FOXTAIL_MVP_MATRIX * vec4(in_VertexPosition, 1.0);
}