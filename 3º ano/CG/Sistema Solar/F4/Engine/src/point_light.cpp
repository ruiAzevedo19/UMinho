#include "../headers/point_light.h"

Point_Light::Point_Light(Trio* pos, Trio* diff, Trio* spec, Trio* ambi, float constant, float linear, float quadratic){
    this->pos = pos;
    this->diff = diff;
    this->spec = spec;
    this->ambi = ambi;
	this->constant = constant;
	this->linear = linear;
	this->quadratic = quadratic;
}

void Point_Light::execute(void) {
}
