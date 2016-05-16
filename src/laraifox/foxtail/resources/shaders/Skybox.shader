Program "Skybox" {
	GLSLVertex
		#version 330

		#pragma include "\include\FoxtailMatrices.inc"
		#pragma include "\include\FoxtailCamera.inc"
			
		layout(location = 0) in vec3 in_VertexPosition;
		
		out vec3 pass_VertexTexCoord;

		void main() {
			pass_VertexTexCoord = in_VertexPosition;
					
			gl_Position = FOXTAIL_MATRIX_VP * vec4(in_VertexPosition + FOXTAIL_CAMERA_POSITION, 1.0);
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
	
Alternate ""