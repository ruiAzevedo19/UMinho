#ifndef _SPHERE_H_
#define _SPHERE_H_

#include "primitive.h"

class Sphere : public Primitive{
    private:
        float radius;
        int slices;
        int stacks;

    public:
        Sphere(float radius, int slices, int stacks);
        void genVertex();
};

#endif

