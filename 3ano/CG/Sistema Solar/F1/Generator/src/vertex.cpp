#include "../headers/vertex.h"

Vertex::Vertex(){
    x = 0;
    y = 0;
    z = 0;
}

Vertex::Vertex(float x, float y, float z){
    this->x = x;
    this->y = y;
    this->z = z;
}

/* getters */
float Vertex::getX(){
    return x;
}

float Vertex::getY(){
    return y;
}

float Vertex::getZ(){
    return z;
}

/* setters */
void Vertex::setX(float x){
    this->x = x;
}

void Vertex::setY(float y){
    this->y = y;
}

void Vertex::setZ(float z){
    this->z = z;
}

/* devolve a representação textual dum vértice */
string Vertex::toString(){
    string s = to_string(x) + " " + to_string(y) + " " + to_string(z) + " ";
    return s;
}

