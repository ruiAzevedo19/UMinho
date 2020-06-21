#ifndef _SPHERE_H_
#define _SPHERE_H_

#include "primitive.h"
#include <math.h>
#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif
#ifndef M_PI_2
#define M_PI_2 1.57079632679489661923
#endif

class Sphere : public Primitive{
    private:
        float radius;
        int slices;
        int stacks;

    public:
        Sphere(float radius, int slices, int stacks);
        void genVertices();
};

#endif

