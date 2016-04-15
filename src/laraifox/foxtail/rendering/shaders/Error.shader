Shader "SolidColorLit" {
	GLSLVertex:
		#version 330

		#pragma include "includes/StandardVariables.h"

		layout(location = 0) in vec3 in_VertexPosition;

		void main() {
			gl_Position = FOXTAIL_MVP_MATRIX * vec4(in_VertexPosition, 1.0);
		}
	GLSLFragment:
		#version 330

		layout(location = 0) out vec4 out_FragColor;

		void main() {
			out_FragColor = vec4(1.0, 0.0, 1.0, 1.0);
		}	
	GLSLEnd:
}
	
Fallback "lagacy/Error.shader"