Shader "StandardComponent" {
	GLSLVertex
		#version 330

		#pragma include "/includes/Standard.inc"
		
		uniform mat4 FOXTAIL_MATRIX_MODEL;
		uniform mat4 FOXTAIL_MATRIX_VIEW;
		uniform mat4 FOXTAIL_MATRIX_PROJECTION;

		uniform mat4 FOXTAIL_MATRIX_MVP;
		uniform mat4 FOXTAIL_MATRIX_I_MODEL;
		uniform mat4 FOXTAIL_MATRIX_MV;
		uniform mat4 FOXTAIL_MATRIX_T_MV;
		uniform mat4 FOXTAIL_MATRIX_IT_MV;
		uniform mat4 FOXTAIL_MATRIX_VP;

		uniform mat4 FOXTAIL_MATRIX_TEXTURE0;
		uniform mat4 FOXTAIL_MATRIX_TEXTURE1;
		uniform mat4 FOXTAIL_MATRIX_TEXTURE2;
		uniform mat4 FOXTAIL_MATRIX_TEXTURE3;

		uniform vec3 FOXTAIL_CAMERA_POSITION;

		layout(location = 0) in vec3 in_VertexPosition;
		layout(location = 1) in vec2 in_VertexTexCoord;
		layout(location = 2) in vec3 in_VertexNormal;
		
		out vec3 pass_ToCameraVector;
		
		out vec3 pass_VertexPosition;
		out vec2 pass_VertexTexCoord;
		out vec3 pass_VertexNormal;

		void main() {
			vec3 worldVertexPosition = (FOXTAIL_MATRIX_MODEL * vec4(in_VertexPosition, 1.0)).xyz;
			
			pass_ToCameraVector = normalize((inverse(FOXTAIL_MATRIX_VIEW) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldVertexPosition);
			
			pass_VertexPosition = worldVertexPosition;
			pass_VertexTexCoord = in_VertexTexCoord;
			pass_VertexNormal = (FOXTAIL_MATRIX_MODEL * vec4(in_VertexNormal, 0.0)).xyz;
		
			gl_Position = FOXTAIL_MATRIX_MVP * vec4(in_VertexPosition, 1.0);
		}
	GLSLFragment
		#version 330

		#pragma include "/includes/Material.inc"
		
		struct Material {
			vec4 baseColor;
			vec4 subsurfaceColor;
			vec4 emissiveColor;
			
			sampler2D diffuseTexture;
			sampler2D emissiveTexture;
			sampler2D normalTexture;
			
			sampler2D metalicTexture;
			sampler2D specularTexture;
			sampler2D roughnessTexture;
			
			sampler2D opacityTexture;
			sampler2D refrationTexture;
		};

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

		struct SpotLight
		{
			PointLight pointLight;
			vec3 direction;
			float cutoff;
		};

		uniform Material FOXTAIL_MATERIAL;
				
		#define MAX_POINT_LIGHTS 8
		#define MAX_SPOT_LIGHTS 8

		uniform BaseLight in_AmbientLight;
		uniform DirectionalLight in_DirectionalLight;

		uniform PointLight[MAX_POINT_LIGHTS] in_PointLights;
		uniform SpotLight[MAX_POINT_LIGHTS] in_SpotLights;
		

		vec4 calculateLight(BaseLight light, vec3 lightDirection, vec3 toCameraVector, vec3 surfaceNormal) {
			float diffuseFactor = dot(surfaceNormal, -lightDirection);
			
			vec4 diffuseColor = vec4(0,0,0,0);
			vec4 specularColor = vec4(0,0,0,0);
			
			if(diffuseFactor > 0) {
				diffuseColor = vec4(light.color, 1.0) * light.intensity * diffuseFactor;
				
				vec3 reflectedVector = normalize(reflect(lightDirection, surfaceNormal));
				float specularFactor = dot(reflectedVector, toCameraVector);
				specularFactor = pow(specularFactor, in_SpecularExponent);
				
				if(specularFactor > 0) {
					specularColor = vec4(light.color, 1.0) * in_SpecularIntensity * specularFactor;
				}
			}
			
			
			return diffuseColor + specularColor;
		}

		vec4 calculateDirectionalLight(DirectionalLight light, vec3 toCameraVector, vec3 surfaceNormal) {
			return calculateLight(light.base, light.direction, toCameraVector, surfaceNormal);
		}

		vec4 calculatePointLight(PointLight light, vec3 toCameraVector, vec3 surfacePosition, vec3 surfaceNormal) {
			vec3 lightDirection = surfacePosition - light.position;
			float distanceToPoint = length(lightDirection);
			
			if(distanceToPoint > light.range)
				return vec4(0,0,0,0);
			
			lightDirection = normalize(lightDirection);
			
			vec4 color = calculateLight(light.base, lightDirection, toCameraVector, surfaceNormal);
			
			float attenuation = light.attenuation.constant +
				light.attenuation.linear * distanceToPoint +
				light.attenuation.exponent * distanceToPoint * distanceToPoint +
				0.0001;
			
			return color / attenuation;
		}

		vec4 calculateSpotLight(SpotLight light, vec3 toCameraVector, vec3 surfacePosition, vec3 surfaceNormal)
		{
			vec3 lightDirection = normalize(surfacePosition - light.pointLight.position);
			float spotFactor = dot(lightDirection, light.direction);
			
			vec4 color = vec4(0,0,0,0);
			
			if(spotFactor > light.cutoff) {
				color = calculatePointLight(light.pointLight, toCameraVector, surfacePosition, surfaceNormal) *
						(1.0 - (1.0 - spotFactor) / (1.0 - light.cutoff));
			}
			
			return color;
		}
		
		void main() {
			vec4 totalLight = vec4(0.0, 0.0, 0.0, 0.0);
		
			vec4 color = FOXTAIL_MATERIAL.baseColor;
			
			vec4 textureColor = texture(FOXTAIL_MATERIAL.diffuseTexture, pass_VertexTexCoord);
		
			if (textureColor != vec4(0.0, 0.0, 0.0, 0.0)) {
				color *= textureColor;
			}
			
			vec3 surfaceNormal = normalize(pass_VertexNormal);
			totalLight += calculateDirectionalLight(in_DirectionalLight, pass_ToCameraVector, surfaceNormal);
			for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
				if (in_PointLights[i].base.intensity > 0.0) {
					totalLight += calculatePointLight(in_PointLights[i], pass_ToCameraVector, pass_VertexPosition, surfaceNormal);
				}
			}
			for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
				if (in_SpotLights[i].pointLight.base.intensity > 0.0) {
					totalLight += calculateSpotLight(in_SpotLights[i], pass_ToCameraVector, pass_VertexPosition, surfaceNormal);
				}
			}
			totalLight = max(totalLight, vec4(in_AmbientLight.color * in_AmbientLight.intensity, 1.0));
			
			out_FragColor = color * totalLight;
		}	
	GLSLEnd
}
	
Fallback ""