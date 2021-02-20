#ifndef _BOX_H_
#define _BOX_H_

#include "primitive.h"

class Box : public Primitive{
    private: 
        float dimX;
        float dimY;
        float dimZ;
        float cx;
        float cy;
        float cz;
        float dx;
        float dy;
        float dz;
        int division;

    public:
        Box(float dimX, float dimY, float dimZ, int division);
        void genVertices();
        void drawXY();
        void drawYZ();
        void drawXZ();
};

#endif

