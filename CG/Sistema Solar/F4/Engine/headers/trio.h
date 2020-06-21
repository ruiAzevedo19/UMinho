#ifndef _TRIO_H_
#define _TRIO_H_

#include <string>

using namespace std;

class Trio{
    private:
        float x;
        float y;
        float z;

    public:
        Trio(void);
        Trio(float x, float y, float z);
        /* getters */
        float getX(void);
        float getY(void);
        float getZ(void);
        /* setters */
        void setX(float x);
        void setY(float y);
        void setZ(float z);
};

#endif
