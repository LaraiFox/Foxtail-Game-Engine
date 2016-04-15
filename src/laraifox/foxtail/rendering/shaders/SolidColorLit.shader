Shader "SolidColorLit" {
	GLSLVertex:
		#version 330

		#pragma include "includes/StandardVariables.h"

		uniform mat4 FOXTAIL_MODEL_MATRIX;
		uniform mat4 FOXTAIL_VIEW_MATRIX;
		uniform mat4 FOXTAIL_MVP_MATRIX;

		layout(location = 0) in vec3 in_VertexPosition;
		layout(location = 2) in vec3 in_VertexNormal;

		out vec3 pass_ToCameraVector;
		out vec3 pass_ToLightVector;
		out vec3 pass_VertexNormal;

		const vec3 LIGHT_POSITION = vec3(0.0, 0.0, 1.0);

		void main() {
			vec3 worldVertexPosition = (FOXTAIL_MODEL_MATRIX * vec4(in_VertexPosition, 1.0)).xyz;

			pass_ToCameraVector = normalize((inverse(FOXTAIL_VIEW_MATRIX) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldVertexPosition);
			pass_ToLightVector = normalize(LIGHT_POSITION - worldVertexPosition);
			pass_VertexNormal = normalize((FOXTAIL_MODEL_MATRIX * vec4(in_VertexNormal, 0.0)).xyz);

			gl_Position = FOXTAIL_MVP_MATRIX * vec4(in_VertexPosition, 1.0);
		}
	GLSLFragment:
		#version 330

		uniform vec4 in_Color;
		uniform float in_Reflectivity;
		uniform float in_Roughness;

		in vec3 pass_ToCameraVector;
		in vec3 pass_ToLightVector;
		in vec3 pass_VertexNormal;

		layout(location = 0) out vec4 out_FragColor;

		const float AMBIENT_LIGHT_LEVEL = 0.1;

		void main() {
			vec3 reflectedLightDirection = normalize(reflect(-pass_ToLightVector, pass_VertexNormal));
			
			float diffuseLight = max(dot(pass_VertexNormal, pass_ToLightVector), AMBIENT_LIGHT_LEVEL);
			float specularLight = max(dot(reflectedLightDirection, pass_ToCameraVector), 0.0);
			specularLight = pow(specularLight, in_Roughness);
			vec3 specularColor = specularLight * in_Reflectivity * vec3(1.0, 0.5, 0.5);

			out_FragColor = in_Color * diffuseLight + vec4(specularColor, 1.0);
		}	
	GLSLEnd:
}
	
Fallback "lagacy/SolidColorLit.shader"