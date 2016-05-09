#pragma include "/LightingStructs.inc"
		
#define MAX_POINT_LIGHTS 8
#define MAX_SPOT_LIGHTS 8

uniform BaseLight in_AmbientLight;
uniform DirectionalLight in_DirectionalLight;

uniform PointLight[MAX_POINT_LIGHTS] in_PointLights;
uniform SpotLight[MAX_POINT_LIGHTS] in_SpotLights;

#pragma include "/LightingFunctions.inc"