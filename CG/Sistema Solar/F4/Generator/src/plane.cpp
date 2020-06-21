#include "../headers/plane.h"

Plane::Plane(){
    dimX = dimZ = 0;
}

Plane::Plane(float dimX, float dimZ){
    this->dimX = dimX;
    this->dimZ = dimZ;

    genVertices();
}

float Plane::getDimX(){
    return dimX;
}

float Plane::getDimZ(){
    return dimZ;
}

void Plane::genVertices(){
    float x = dimX / 2;
    float z = dimZ / 2;

    /* triangulo da esquerda*/
    addVertice(new Vertice(x,0,z));
    addVertice(new Vertice(-x,0,-z));
    addVertice(new Vertice(-x,0,z));

    /* triangulo da direita */  
    addVertice(new Vertice(x,0,z));
    addVertice(new Vertice(x,0,-z));
    addVertice(new Vertice(-x,0,-z));

    /* genNormals */ 
    addNormal(new Vertice(0.0f,1.0f,0.0f));
    addNormal(new Vertice(0.0f,1.0f,0.0f));
    addNormal(new Vertice(0.0f,1.0f,0.0f));

    addNormal(new Vertice(0.0f,1.0f,0.0f));
    addNormal(new Vertice(0.0f,1.0f,0.0f));
    addNormal(new Vertice(0.0f,1.0f,0.0f));

    /* genTextures */ 
    addTexture(new Vertice(1.0f,0.0f,0.0f));
    addTexture(new Vertice(0.0f,1.0f,0.0f));
    addTexture(new Vertice(0.0f,0.0f,0.0f));

    addTexture(new Vertice(1.0f,0.0f,0.0f));
    addTexture(new Vertice(1.0f,1.0f,0.0f));
    addTexture(new Vertice(0.0f,1.0f,0.0f));
}
