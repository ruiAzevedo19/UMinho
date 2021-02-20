#include "../headers/vertice.h"

Vertice::Vertice(){
    x = 0;
    y = 0;
    z = 0;
}

Vertice::Vertice(float x, float y, float z){
    this->x = x;
    this->y = y;
    this->z = z;
}

/* getters */
float Vertice::getX(){
    return x;
}

float Vertice::getY(){
    return y;
}

float Vertice::getZ(){
    return z;
}

/* setters */
void Vertice::setX(float x){
    this->x = x;
}

void Vertice::setY(float y){
    this->y = y;
}

void Vertice::setZ(float z){
    this->z = z;
}

/* devolve a representação textual dum vértice */
string Vertice::toString(){
    string s = to_string(x) + " " + to_string(y) + " " + to_string(z) + " ";
    return s;
}

