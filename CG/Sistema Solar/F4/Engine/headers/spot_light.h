#ifndef _SPOT_LIGHT_H_
#define _SPOT_LIGHT_H_

#include "light.h"
#include "trio.h"

class Spot_Light : public Light {
    private:
        Trio* pos;
        Trio* diff;
        Trio* spec;
        Trio* ambi;
        float constant;
        float linear;
        float quadratic;
        Trio* spot;
        float cutoff;
        float exponent;
    public:
        Spot_Light(Trio* pos, Trio* diff, Trio* spec, Trio* ambi, float constant, float linear, float quadratic, Trio* spot, float cutoff, float exponent);
        void execute(void);
};

#endif
