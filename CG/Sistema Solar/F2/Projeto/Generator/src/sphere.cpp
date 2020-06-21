#include "../headers/sphere.h"
#include <math.h>

Sphere::Sphere(float radius, int slices, int stacks){
    this->radius = radius;
    this->slices = slices;
    this->stacks = stacks;

    genVertices();
}

void Sphere::genVertices(){
    float alpha, beta;
    float dx = (float)(2 * M_PI) / (float)slices;
    float dy = M_PI / (float)stacks;
    float x1,x2,x3,x4,y1,y2,y3,y4,z1,z2,z3,z4;

    for(int i = 0; i < stacks; i++){
        beta = M_PI_2 - dy * (i+1);
        for(int j = 0; j < slices; j++){
                alpha = dx * j;
                x1 = radius * sin(alpha) * cos(beta);
                y1 = radius * sin(beta);
                z1 = radius * cos(alpha) * cos(beta);

                x2 = radius * sin(alpha + dx) * cos(beta);
                y2 = radius * sin(beta);
                z2 = radius * cos(alpha + dx) * cos(beta);

                x3 = radius * sin(alpha) * cos(beta + dy);
                y3 = radius * sin(beta + dy);
                z3 = radius * cos(alpha) * cos(beta + dy);

                x4 = radius * sin(alpha + dx) * cos(beta + dy);
                y4 = radius * sin(beta + dy);
                z4 = radius * cos(alpha + dx) * cos(beta + dy);

                addVertice(new Vertice(x1,y1,z1));
                addVertice(new Vertice(x2,y2,z2));
                addVertice(new Vertice(x3,y3,z3));

                addVertice(new Vertice(x2,y2,z2));
                addVertice(new Vertice(x4,y4,z4));
                addVertice(new Vertice(x3,y3,z3));
            }
    }
}
