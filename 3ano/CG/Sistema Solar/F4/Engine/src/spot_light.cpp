#include "../headers/spot_light.h"

Spot_Light::Spot_Light(Trio* pos, Trio* diff, Trio* spec, Trio* ambi, float constant, float linear, float quadratic, Trio* spot, float cutoff, float exponent){
    this->pos = pos;
    this->diff = diff;
    this->spec = spec;
    this->ambi = ambi;
	this->constant = constant;
	this->linear = linear;
	this->quadratic = quadratic;
	this->spot = spot;
	this->cutoff = cutoff;
	this->exponent = exponent;
}

void Spot_Light::execute(void) {
}
