#include "../headers/primitive.h"

Primitive::Primitive(){}

vector<Vertex*> Primitive::getVertexes(){
    return vertexes;
}

void Primitive::addVertex(Vertex *v){
    vertexes.push_back(v);
}
