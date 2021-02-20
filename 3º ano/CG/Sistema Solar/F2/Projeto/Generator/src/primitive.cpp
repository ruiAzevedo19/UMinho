#include "../headers/primitive.h"

Primitive::Primitive(){}

vector<Vertice*> Primitive::getVertices(){
    return vertices;
}

void Primitive::addVertice(Vertice *v){
    vertices.push_back(v);
}
