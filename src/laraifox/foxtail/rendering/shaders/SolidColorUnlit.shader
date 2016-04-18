Shader "LegacySolidColorUnlit" {
	GLSLVertex
		#version 330
		
		#pragma include "/includes/StandardVariables.h"

		uniform mat4 FOXTAIL_MVP_MATRIX;

		layout(location = 0) in vec3 in_VertexPosition;

		void main() {
			gl_Position = FOXTAIL_MVP_MATRIX * vec4(in_VertexPosition, 1.0);
		}
	GLSLFragment
		#version 330

		uniform vec4 in_BaseColor;

		layout(location = 0) out vec4 out_FragColor;

		void main() {
			out_FragColor = in_BaseColor;
		}
	GLSLEnd
}
	
Fallback "/legacy/SolidColorUnlit.shader"