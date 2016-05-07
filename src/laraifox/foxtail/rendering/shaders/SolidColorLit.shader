Shader "SolidColorLit" {
	GLSLVertex
		#version 330

		#pragma include "/includes/Standard.h"

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
	GLSLFragment
		#version 330

		uniform vec4 in_BaseColor;
		uniform float in_Reflectivity;
		uniform float in_Roughness;

		in vec3 pass_ToCameraVector;
		in vec3 pass_ToLightVector;
		in vec3 pass_VertexNormal;

		layout(location = 0) out vec4 out_FragColor;

		const float AMBIENT_LIGHT_LEVEL = 0.1;
		
		vec4 calcLight(vec3 vertexNormal, vec3 lightDirection, vec3 cameraDirection, vec3 baseColor, float specularIntensity, float specularPower) {
			float diffuseFactor = dot(vertexNormal, -lightDirection);
			
			vec4 diffuseColor = vec4(0,0,0,0);
			vec4 specularColor = vec4(0,0,0,0);
			
			if(diffuseFactor > 0)
			{
				const float baseIntensity = 1.0;
				
				diffuseColor = vec4(baseColor, 1.0) * baseIntensity * max(diffuseFactor, AMBIENT_LIGHT_LEVEL);
				
				// vec3 reflectDirection = normalize(reflect(lightDirection, vertexNormal));
				vec3 halfDirection = normalize(cameraDirection - lightDirection);
				
				float specularFactor = dot(halfDirection, vertexNormal);
				// float specularFactor = dot(cameraDirection, reflectDirection);
				specularFactor = pow(specularFactor, specularPower);
				
				if(specularFactor > 0)
				{
					specularColor = vec4(baseColor, 1.0) * specularIntensity * specularFactor;
				}
			}
			
			return diffuseColor + specularColor;
		}

		void main() {
			// vec3 reflectedLightDirection = normalize(normalize(pass_ToCameraVector) - normalize(pass_ToLightVector));
			
			// float diffuseLight = max(dot(pass_VertexNormal, pass_ToLightVector), AMBIENT_LIGHT_LEVEL);
			
			// float testdot = max(dot(pass_ToLightVector, pass_VertexNormal), 0.0);
			
			// vec3 reflectedLightDirection = reflect(-pass_ToLightVector, pass_VertexNormal);
			// float specularLight = max(dot(reflectedLightDirection, pass_ToCameraVector), 0.0);
			// specularLight = pow(specularLight, in_Roughness);
			// vec3 specularColor = specularLight * in_Reflectivity * vec3(1.0, 0.5, 0.5);

			out_FragColor = calcLight(pass_VertexNormal, -pass_ToLightVector, pass_ToCameraVector, in_BaseColor.xyz, in_Reflectivity, in_Roughness); 
			//vec4(in_BaseColor.xyz * diffuseLight, in_BaseColor.w) + vec4(specularColor, 1.0);
		}	
	GLSLEnd
}
	
Fallback ""