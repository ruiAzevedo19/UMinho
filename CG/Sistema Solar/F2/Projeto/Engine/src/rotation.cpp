#include "../headers/rotation.h"

Rotation::Rotation(float angle, float x, float y, float z){
    this->angle = angle;
    this->x = x;
    this->y = y;
    this->z = z;
}

float Rotation::getAngle(){
    return angle;
}

float Rotation::getX(){
    return x;
}

float Rotation::getY(){
    return y;
}

float Rotation::getZ(){
    return z;
}

void Rotation::execute(void){
    glRotatef(this->angle, this->x, this->y, this->z);
}
