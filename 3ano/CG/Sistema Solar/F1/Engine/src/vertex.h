#ifndef _VERTEX_H_
#define _VERTEX_H_

#include <string>

using namespace std;

class Vertex{
    private:
        float x;
        float y;
        float z;

    public:
        Vertex(void);
        Vertex(float x, float y, float z);
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
