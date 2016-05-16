#version 330

uniform vec4 in_Color;

layout(location = 0) out vec4 out_FragColor;

void main() {
	out_FragColor = in_Color;
}