#include "../headers/translation.h"

Translation::Translation(float x, float y, float z){
    this->x = x;
    this->y = y;
    this->z = z;
}

float Translation::getX(){
    return x;
}

float Translation::getY(){
    return y;
}

float Translation::getZ(){
    return z;
}

void Translation::execute(void){
    glTranslatef(this->x, this->y, this->z);
}
