vec4 calculateLight(BaseLight light, vec3 lightDirection, vec3 toCameraVector, vec3 surfaceNormal) {
	float diffuseFactor = dot(surfaceNormal, -lightDirection);
	
	vec4 diffuseColor = vec4(0,0,0,0);
	vec4 specularColor = vec4(0,0,0,0);
	
	if(diffuseFactor > 0) {
		diffuseColor = vec4(light.color, 1.0) * light.intensity * diffuseFactor;
		
		vec3 reflectedVector = normalize(reflect(lightDirection, surfaceNormal));
		float specularFactor = dot(reflectedVector, pass_ToCameraVector);
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