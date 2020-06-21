#ifndef _VERTICE_H_
#define _VERTICE_H_

#include <string>

using namespace std;

class Vertice{
    private:
        float x;
        float y;
        float z;

    public:
        Vertice(void);
        Vertice(float x, float y, float z);
        /* getters */
        float getX(void);
        float getY(void);
        float getZ(void);
        /* setters */
        void setX(float x);
        void setY(float y);
        void setZ(float z);
        string toString();
};

#endif
