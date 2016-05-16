Program "PostProcessingError" {
	GLSLVertex
		#version 330

		#pragma include "\..\include\FoxtailMatrices.inc"

		layout(location = 0) in vec3 in_VertexPosition;
		layout(location = 1) in vec2 in_VertexTexCoord;
		
		out vec2 pass_VertexTexCoord;

		void main() {
			pass_VertexTexCoord = in_VertexTexCoord;
		
			gl_Position = vec4(in_VertexPosition.xy, 0.0, 1.0);
		}
	GLSLFragment
		#version 330
		
		uniform sampler2D colorTexture;
		
		in vec2 pass_VertexTexCoord;

		layout(location = 0) out vec4 out_FragColor;

		void main() {
			out_FragColor = vec4(1.0, 0.0, 1.0, 1.0);
		}	
	GLSLEnd
}
	
Alternate ""