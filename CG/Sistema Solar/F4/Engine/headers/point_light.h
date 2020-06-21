#ifndef _POINT_LIGHT_H_
#define _POINT_LIGHT_H_

#include "light.h"
#include "trio.h"

class Point_Light : public Light {
    private:
        Trio* pos;
        Trio* diff;
        Trio* spec;
        Trio* ambi;
        float constant;
        float linear;
        float quadratic;
    public:
        Point_Light(Trio* pos, Trio* diff, Trio* spec, Trio* ambi, float constant, float linear, float quadratic);
        void execute(void);
};

#endif
