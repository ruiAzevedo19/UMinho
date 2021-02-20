#ifndef _CONE_H_
#define _CONE_H_

#include "primitive.h"

class Cone : public Primitive{
    private:
        float radius;
        float height;
        int slices;
        int stacks;

    public:
        Cone(float radius, float height, int slices, int stacks);
        void drawButtom();
        void drawFaces();
        void genVertices();
};

#endif

