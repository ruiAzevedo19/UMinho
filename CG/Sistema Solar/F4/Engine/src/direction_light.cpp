#include "../headers/direction_light.h"

Direction_Light::Direction_Light(Trio* pos, Trio* diff, Trio* spec, Trio* ambi, float constant, float linear, float quadratic){
    this->pos = pos;
    this->diff = diff;
    this->spec = spec;
    this->ambi = ambi;
	this->constant = constant;
	this->linear = linear;
	this->quadratic = quadratic;
}

void Direction_Light::execute(void) {
}
