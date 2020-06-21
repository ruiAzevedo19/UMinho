#include "../headers/rotation.h"

Rotation::Rotation(float angle, float x, float y, float z, float time){
    this->angle = angle;
    this->x = x;
    this->y = y;
    this->z = z;
    this->time = time;
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

float Rotation::getTime(){
    return time;
}

void Rotation::execute(void){
    float r, gr;
    if (time != 0) {
        r = glutGet(GLUT_ELAPSED_TIME) % (int)(time * 1000);
        gr = (r * 360) / (time * 1000);
        glRotatef(this->angle+gr, this->x, this->y, this->z);
    } else glRotatef(this->angle, this->x, this->y, this->z);
}

