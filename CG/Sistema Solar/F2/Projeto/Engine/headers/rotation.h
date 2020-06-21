#ifndef _ROTATION_H_
#define _ROTATION_H_

#include "transformation.h"

class Rotation : public Transformation{
    private:
        float angle;
        float x;
        float y;
        float z;
    public:
        Rotation(float angle, float x, float y, float z);
        float getAngle();
        float getX();
        float getY();
        float getZ();
        void execute(void);
};

#endif
