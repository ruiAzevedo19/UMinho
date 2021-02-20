#ifndef _PRIMITIVE_H_
#define _PRIMITIVE_H_

#include "vertex.h"
#include <vector>

class Primitive{
    private:
        vector<Vertex*> vertexes;

    public:
        Primitive();
        vector<Vertex*> getVertexes();
        void addVertex(Vertex *v);
        virtual void genVertex() = 0;
};

#endif
