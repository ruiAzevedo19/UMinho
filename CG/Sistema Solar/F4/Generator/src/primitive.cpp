#include "../headers/primitive.h"

Primitive::Primitive(){}

vector<Vertice*> Primitive::getVertices(){
    return vertices;
}

vector<Vertice*> Primitive::getNormals(){
    return normals;
}

vector<Vertice*> Primitive::getTextures(){
    return textures;
}

void Primitive::addVertice(Vertice *v){
    vertices.push_back(v);
}

void Primitive::addNormal(Vertice* v){
    normals.push_back(v);
}

void Primitive::addTexture(Vertice *v){
    textures.push_back(v);
}
