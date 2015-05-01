#version 120

uniform vec3 fogColor;

varying vec3 v_position;
varying float v_visibility;

void main() {
	vec4 positionColor = vec4(v_position.xy + 0.5, 1.0, 1.0);
	
	gl_FragColor = mix(vec4(fogColor, 1.0f), positionColor, v_visibility);
}