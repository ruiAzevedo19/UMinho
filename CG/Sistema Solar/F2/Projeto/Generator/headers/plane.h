#ifndef _PLANE_H_
#define _PLANE_H_

#include "primitive.h"

class Plane : public Primitive{
    private:
        float dimX;
        float dimZ;

    public:
        Plane(void);
        Plane(float dimX, float dimZ);
        float getDimX();
        float getDimZ();
        void genVertices();
};

#endif

