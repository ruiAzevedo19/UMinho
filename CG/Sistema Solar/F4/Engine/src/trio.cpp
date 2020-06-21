#include "../headers/trio.h"

Trio::Trio(){
    x = 0;
    y = 0;
    z = 0;
}

Trio::Trio(float x, float y, float z){
    this->x = x;
    this->y = y;
    this->z = z;
}

/* getters */
float Trio::getX(){
    return x;
}

float Trio::getY(){
    return y;
}

float Trio::getZ(){
    return z;
}

/* setters */
void Trio::setX(float x){
    this->x = x;
}

void Trio::setY(float y){
    this->y = y;
}

void Trio::setZ(float z){
    this->z = z;
}
