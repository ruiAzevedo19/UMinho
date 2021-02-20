#ifndef _DIRECTION_LIGHT_H_
#define _DIRECTION_LIGHT_H_

#include "light.h"
#include "trio.h"

class Direction_Light : public Light {
    private:
        Trio* pos;
        Trio* diff;
        Trio* spec;
        Trio* ambi;
        float constant;
        float linear;
        float quadratic;
    public:
        Direction_Light(Trio* pos, Trio* diff, Trio* spec, Trio* ambi, float constant, float linear, float quadratic);
        void execute(void);
};

#endif
