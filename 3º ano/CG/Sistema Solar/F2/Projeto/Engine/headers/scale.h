#ifndef _SCALE_H_
#define _SCALE_H_

#include "transformation.h"

class Scale : public Transformation{
    private:
        float x;
        float y;
        float z;
    public:
        Scale(float x, float y, float z);
        float getX();
        float getY();
        float getZ();
        void execute(void);
};

#endif
