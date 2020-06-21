#ifndef _ROTATION_H_
#define _ROTATION_H_

#include "transformation.h"

class Rotation : public Transformation{
    private:
        float angle;
        float x;
        float y;
        float z;
        float time;
    public:
        Rotation(float angle, float x, float y, float z, float time);
        float getAngle();
        float getX();
        float getY();
        float getZ();
        float getTime();
        void execute(void);
};

#endif
