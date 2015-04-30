#version 120

varying vec3 v_position;

void main() {
	gl_FragColor = vec4(v_position.xy + 0.5, 1.0, 1.0);
}