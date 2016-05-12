Shader "Skybox" {
	GLSLVertex
		#version 330

		#pragma include "/includes/Standard.h"
	
		uniform mat4 FOXTAIL_VIEW_PROJECTION_MATRIX;
		
		uniform vec3 in_CameraPosition;

		layout(location = 0) in vec3 in_VertexPosition;
		
		out vec3 pass_VertexTexCoord;

		void main() {
			pass_VertexTexCoord = in_VertexPosition;
					
			gl_Position = FOXTAIL_VIEW_PROJECTION_MATRIX * vec4(in_VertexPosition + in_CameraPosition, 1.0);
		}
	GLSLFragment
		#version 330
		
		uniform samplerCube in_Diffuse;
		
		in vec3 pass_VertexTexCoord;

		layout(location = 0) out vec4 out_FragColor;
		
		void main() {
			out_FragColor = texture(in_Diffuse, pass_VertexTexCoord);
		}	
	GLSLEnd
}
	
Fallback ""