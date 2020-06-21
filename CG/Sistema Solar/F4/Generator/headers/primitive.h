#ifndef _PRIMITIVE_H_
#define _PRIMITIVE_H_

#include "vertice.h"
#include <vector>

class Primitive{
    private:
        vector<Vertice*> vertices;
        vector<Vertice*> normals;
        vector<Vertice*> textures;

    public:
        Primitive();
        vector<Vertice*> getVertices();
        vector<Vertice*> getNormals();
        vector<Vertice*> getTextures();
        void addVertice(Vertice* v);
        void addNormal(Vertice* v);
        void addTexture(Vertice* v);
        virtual void genVertices() = 0;
};

#endif
