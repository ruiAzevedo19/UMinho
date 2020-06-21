#ifndef _ENGINE_PRIMITIVE_H_
#define _ENGINE_PRIMITIVE_H_

#include "../../Generator/headers/vertice.h"
#include "transformation.h"
#include <vector>

class Engine_Primitive{
    private:
        vector<Vertice*> vertices;
        vector<Transformation*> transformations;

    public:
        Engine_Primitive(void);
        Engine_Primitive(vector<Vertice*> vertices, vector<Transformation*> transformations);
        vector<Vertice*> getVertices();
        vector<Transformation*> getTransformations();
        void setTransformations(vector<Transformation*> t);
        void addVertice(Vertice *v);
        void addTransformation(Transformation *t);
};

#endif
