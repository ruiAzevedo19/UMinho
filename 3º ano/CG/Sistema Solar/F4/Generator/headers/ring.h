#ifndef _RING_H_
#define _RING_H_

#include "primitive.h"
#include <math.h>
#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif

class Ring : public Primitive{
    private:
        int slices;
        float radius;
        float ring;

    public:
        Ring(int slices, float radius, float ring);
        void genVertices();
};

#endif

