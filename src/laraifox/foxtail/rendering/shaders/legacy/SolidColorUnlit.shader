Shader "LegacySolidColorUnit" {
	GLSLVertex
		#version 120

		#pragma include "/../includes/StandardVariables.h"

		uniform mat4 FOXTAIL_MVP_MATRIX;

		attribute vec3 in_VertexPosition;

		void main() {
			gl_Position = FOXTAIL_MVP_MATRIX * vec4(in_VertexPosition, 1.0);
		}
	GLSLFragment
		#version 120

		uniform vec4 in_BaseColor;

		void main() {
			gl_FragColor = in_BaseColor;
		}	
	GLSLEnd
}
	
Fallback ""