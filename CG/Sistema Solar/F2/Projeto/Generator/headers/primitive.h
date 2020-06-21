#ifndef _PRIMITIVE_H_
#define _PRIMITIVE_H_

#include "vertice.h"
#include <vector>

class Primitive{
    private:
        vector<Vertice*> vertices;

    public:
        Primitive();
        vector<Vertice*> getVertices();
        void addVertice(Vertice *v);
        virtual void genVertices() = 0;
};

#endif
