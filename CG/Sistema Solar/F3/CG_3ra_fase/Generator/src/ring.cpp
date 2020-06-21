#include "../headers/ring.h"
#include <math.h>

Ring::Ring(int slices, float radius, float ring){
    this->slices = slices;
    this->radius = radius;
    this->ring = ring;

    genVertices();
}

void Ring::genVertices(){
    float dx;
    float alpha = 2 * M_PI / slices;
    for(int i = 0; i < slices; i++){
        dx = alpha * i;
        addVertice(new Vertice(radius * sin(dx), 0.0f, radius * cos(dx)));
        addVertice(new Vertice(radius * sin(dx + alpha), 0.0f, radius * cos(dx + alpha)));
        addVertice(new Vertice((radius - ring) * sin(dx), 0.0f, (radius - ring) * cos(dx)));

        addVertice(new Vertice((radius - ring) * sin(dx), 0.0f, (radius - ring) * cos(dx)));
        addVertice(new Vertice(radius * sin(dx + alpha), 0.0f, radius * cos(dx + alpha)));
        addVertice(new Vertice((radius - ring) * sin(dx + alpha), 0.0f, (radius - ring) * cos(dx + alpha)));
    }
}
