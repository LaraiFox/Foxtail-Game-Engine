Shader "TexturedPhong" {
	GLSLVertex
		#version 330

		#pragma include "/includes/Standard.inc"
		
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

		#pragma include "/includes/Material.inc"
		
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
		uniform PointLight[MAX_POINT_LIGHTS] in_PointLights;
		
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
			
			vec3 surfaceNormal = normalize(pass_VertexNormal);
			totalLight += calculateDirectionalLight(in_DirectionalLight, surfaceNormal);
			for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
				if (in_PointLights[i].base.intensity > 0.0) {
					totalLight += calculatePointLight(in_PointLights[i], surfaceNormal);
				}
			}
			totalLight = max(totalLight, vec4(in_AmbientLight, 1.0));
			
			out_FragColor = color * totalLight;
		}	
	GLSLEnd
}
	
Fallback ""