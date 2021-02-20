#include "../headers/engine_primitive.h"

Engine_Primitive::Engine_Primitive(){
    ;
}

Engine_Primitive::Engine_Primitive(vector<Vertice*> vertices, vector<Transformation*> transformations){
    this->vertices = vertices;
    this->transformations = transformations;
}

vector<Vertice*> Engine_Primitive::getVertices(){
    return vertices;
}

vector<Transformation*> Engine_Primitive::getTransformations(){
    return transformations;
}

void Engine_Primitive::setTransformations(vector<Transformation*> t){
    transformations = t;
}

void Engine_Primitive::addVertice(Vertice *v){
    vertices.push_back(v);
}

void Engine_Primitive::addTransformation(Transformation *t){
    transformations.push_back(t);
}
