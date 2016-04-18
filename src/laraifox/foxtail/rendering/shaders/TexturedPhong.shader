Shader "TexturedPhong" {
	GLSLVertex
		#version 330

		#pragma include "/includes/StandardVariables.h"
		
		uniform mat4 FOXTAIL_MODEL_MATRIX;
		uniform mat4 FOXTAIL_VIEW_MATRIX;
		uniform mat4 FOXTAIL_MVP_MATRIX;

		layout(location = 0) in vec3 in_VertexPosition;
		layout(location = 1) in vec2 in_VertexTexCoord;
		layout(location = 2) in vec3 in_VertexNormal;
		
		out vec3 pass_ToCameraVector;
		
		out vec3 pass_VertexPosition;
		out vec2 pass_VertexTexCoord;
		out vec3 pass_VertexNormal;

		void main() {
			vec3 worldVertexPosition = (FOXTAIL_MODEL_MATRIX * vec4(in_VertexPosition, 1.0)).xyz;
			
			pass_ToCameraVector = normalize((inverse(FOXTAIL_VIEW_MATRIX) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldVertexPosition);
			
			pass_VertexPosition = worldVertexPosition;
			pass_VertexTexCoord = in_VertexTexCoord;
			pass_VertexNormal = (FOXTAIL_MODEL_MATRIX * vec4(in_VertexNormal, 0.0)).xyz;
		
			gl_Position = FOXTAIL_MVP_MATRIX * vec4(in_VertexPosition, 1.0);
		}
	GLSLFragment
		#version 330
		
		struct BaseLight {
			vec3 color;
			float intensity;
		};
		
		struct DirectionalLight {
			BaseLight base;
			vec3 direction;
		};

		struct Attenuation
		{
			float constant;
			float linear;
			float exponent;
		};

		struct PointLight
		{
			BaseLight base;
			Attenuation attenuation;
			vec3 position;
			float range;
		};
		
		#define MAX_POINT_LIGHTS 8
		
		uniform DirectionalLight in_DirectionalLight;
		uniform PointLight in_PointLights0;
		uniform PointLight in_PointLights1;
		uniform PointLight in_PointLights2;
		uniform PointLight in_PointLights3;
		uniform PointLight in_PointLights4;
		uniform PointLight in_PointLights5;
		uniform PointLight in_PointLights6;
		uniform PointLight in_PointLights7;
		
		uniform vec4 in_BaseColor;
		uniform vec3 in_AmbientLight;
		uniform sampler2D in_Diffuse;
		
		uniform float in_SpecularIntensity;
		uniform float in_SpecularExponent;
		
		in vec3 pass_ToCameraVector;
		
		in vec3 pass_VertexPosition;
		in vec2 pass_VertexTexCoord;
		in vec3 pass_VertexNormal;

		layout(location = 0) out vec4 out_FragColor;

		vec4 calculateLight(BaseLight light, vec3 lightDirection, vec3 surfaceNormal) {
			float diffuseFactor = dot(surfaceNormal, -lightDirection);
			
			vec4 diffuseColor = vec4(0,0,0,0);
			vec4 specularColor = vec4(0,0,0,0);
			
			if(diffuseFactor > 0) {
				diffuseColor = vec4(light.color, 1.0) * light.intensity * diffuseFactor;
				
				// vec3 halfDirection = normalize(pass_ToCameraVector - lightDirection);
				// float specularFactor = dot(halfDirection, surfaceNormal);
				vec3 reflectedVector = normalize(reflect(lightDirection, surfaceNormal));
				float specularFactor = dot(reflectedVector, pass_ToCameraVector);
				specularFactor = pow(specularFactor, in_SpecularExponent);
				
				if(specularFactor > 0) {
					specularColor = vec4(light.color, 1.0) * in_SpecularIntensity * specularFactor;
				}
			}
			
			
			return diffuseColor + specularColor;
		}

		vec4 calculateDirectionalLight(DirectionalLight light, vec3 surfaceNormal) {
			return calculateLight(light.base, light.direction, surfaceNormal);
		}

		vec4 calculatePointLight(PointLight pointLight, vec3 normal)
		{
			vec3 lightDirection = pass_VertexPosition - pointLight.position;
			float distanceToPoint = length(lightDirection);
			
			if(distanceToPoint > pointLight.range)
				return vec4(0,0,0,0);
			
			lightDirection = normalize(lightDirection);
			
			vec4 color = calculateLight(pointLight.base, lightDirection, normal);
			
			float attenuation = pointLight.attenuation.constant +
								 pointLight.attenuation.linear * distanceToPoint +
								 pointLight.attenuation.exponent * distanceToPoint * distanceToPoint +
								 0.0001;
								 
			return color / attenuation;
		}
		
		void main() {
			vec4 totalLight = vec4(0.0, 0.0, 0.0, 0.0);
		
			vec4 color = in_BaseColor;
			
			vec4 textureColor = texture(in_Diffuse, pass_VertexTexCoord);
		
			if (textureColor != vec4(0.0, 0.0, 0.0, 0.0)) {
				color *= textureColor;
			}
			
			// PointLight tempPointLight1;
			// tempPointLight1.base.color = vec3(0.0, 0.1, 1.0);
			// tempPointLight1.base.intensity = 1.5;
			// tempPointLight1.attenuation.constant = 0.0;
			// tempPointLight1.attenuation.linear = 0.0;
			// tempPointLight1.attenuation.exponent = 0.2;
			// tempPointLight1.position = vec3(15.0, 3.0, 0.0);
			// tempPointLight1.range = 15.0;
			
			// PointLight tempPointLight2;
			// tempPointLight2.base.color = vec3(0.0, 0.1, 1.0);
			// tempPointLight2.base.intensity = 1.5;
			// tempPointLight2.attenuation.constant = 0.0;
			// tempPointLight2.attenuation.linear = 0.0;
			// tempPointLight2.attenuation.exponent = 0.2;
			// tempPointLight2.position = vec3(15.0, -3.0, 0.0);
			// tempPointLight2.range = 15.0;
			
			vec3 surfaceNormal = normalize(pass_VertexNormal);
			totalLight += calculateDirectionalLight(in_DirectionalLight, surfaceNormal);
			// totalLight += calculatePointLight(tempPointLight1, surfaceNormal);
			// totalLight += calculatePointLight(tempPointLight2, surfaceNormal);
			if (in_PointLights0.base.intensity > 0.0) 
				totalLight += calculatePointLight(in_PointLights0, surfaceNormal);
			if (in_PointLights1.base.intensity > 0.0) 
				totalLight += calculatePointLight(in_PointLights1, surfaceNormal);
			if (in_PointLights2.base.intensity > 0.0) 
				totalLight += calculatePointLight(in_PointLights2, surfaceNormal);
			if (in_PointLights3.base.intensity > 0.0) 
				totalLight += calculatePointLight(in_PointLights3, surfaceNormal);
			if (in_PointLights4.base.intensity > 0.0) 
				totalLight += calculatePointLight(in_PointLights4, surfaceNormal);
			if (in_PointLights5.base.intensity > 0.0) 
				totalLight += calculatePointLight(in_PointLights5, surfaceNormal);
			if (in_PointLights6.base.intensity > 0.0) 
				totalLight += calculatePointLight(in_PointLights6, surfaceNormal);
			if (in_PointLights7.base.intensity > 0.0) 
				totalLight += calculatePointLight(in_PointLights7, surfaceNormal);
			// for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
				// if (in_PointLights[i].base.intensity > 0.0) {
					// totalLight += calculatePointLight(in_PointLights[i], surfaceNormal);
				// }
			// }
			totalLight = max(totalLight, vec4(in_AmbientLight, 1.0));
			
			out_FragColor = color * totalLight;
		}	
	GLSLEnd
}
	
Fallback ""