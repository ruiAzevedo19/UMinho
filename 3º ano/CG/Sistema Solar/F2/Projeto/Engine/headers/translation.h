#ifndef _TRANSLATION_H_
#define _TRANSLATION_H_

#include "transformation.h"

class Translation : public Transformation{
    private:
        float x;
        float y;
        float z;
    public:
        Translation(float x, float y, float z);
        float getX();
        float getY();
        float getZ();
        void execute(void);
};

#endif
