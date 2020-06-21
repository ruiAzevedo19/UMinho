#include "../headers/scale.h"

Scale::Scale(float x, float y, float z){
    this->x = x;
    this->y = y;
    this->z = z;
}

float Scale::getX(){
    return x;
}

float Scale::getY(){
    return y;
}

float Scale::getZ(){
    return z;
}

void Scale::execute(void){
    glScalef(this->x, this->y, this->z);
}
