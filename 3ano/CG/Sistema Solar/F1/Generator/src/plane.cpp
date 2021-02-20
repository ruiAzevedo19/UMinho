#include "../headers/plane.h"

Plane::Plane(){
    dimX = dimZ = 0;
}

Plane::Plane(float dimX, float dimZ){
    this->dimX = dimX;
    this->dimZ = dimZ;

    genVertex();
}

float Plane::getDimX(){
    return dimX;
}

float Plane::getDimZ(){
    return dimZ;
}

void Plane::genVertex(){
    float x = dimX / 2;
    float z = dimZ / 2;

    /* triangulo da esquerda*/
    addVertex(new Vertex(x,0,z));
    addVertex(new Vertex(-x,0,-z));
    addVertex(new Vertex(-x,0,z));

    /* triangulo da direita */  
    addVertex(new Vertex(x,0,z));
    addVertex(new Vertex(x,0,-z));
    addVertex(new Vertex(-x,0,-z));
}
