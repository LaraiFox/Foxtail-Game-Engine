Program "TexturedUnlit2D" {
	GLSLVertex
		#version 330

		#pragma include "/include/FoxtailMatrices.inc"
	
		uniform mat4 FOXTAIL_PROJECTION_MATRIX;
		
		uniform vec2 in_Offset;

		layout(location = 0) in vec2 in_VertexPosition;
		layout(location = 1) in vec2 in_VertexTexCoord;
		
		out vec2 pass_VertexTexCoord;

		void main() {
			pass_VertexTexCoord = in_VertexTexCoord;
					
			gl_Position = FOXTAIL_PROJECTION_MATRIX * vec4(in_VertexPosition + in_Offset * vec2(2.0, -2.0), 0.0, 1.0);
		}
	GLSLFragment
		#version 330
		
		uniform sampler2D in_Diffuse;
		
		uniform vec3 in_BaseColor;
		
		in vec2 pass_VertexTexCoord;

		layout(location = 0) out vec4 out_FragColor;
		
		void main() {
			out_FragColor = texture(in_Diffuse, pass_VertexTexCoord) * vec4(in_BaseColor, 1.0);
		}	
	GLSLEnd
}
	
Alternate ""