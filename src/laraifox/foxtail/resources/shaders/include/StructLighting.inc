struct BaseLight {
	vec3 color;
	float intensity;
};

struct DirectionalLight {
	BaseLight baseLight;
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
	BaseLight baseLight;
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