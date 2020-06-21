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
    float dif = (radius - ring) / radius;

    for(int i = 0; i < slices; i++){
        dx = alpha * i;
        addVertice(new Vertice(radius * sin(dx), 0.0f, radius * cos(dx)));
        addVertice(new Vertice(radius * sin(dx + alpha), 0.0f, radius * cos(dx + alpha)));
        addVertice(new Vertice((radius - ring) * sin(dx), 0.0f, (radius - ring) * cos(dx)));

        addVertice(new Vertice((radius - ring) * sin(dx), 0.0f, (radius - ring) * cos(dx)));
        addVertice(new Vertice(radius * sin(dx + alpha), 0.0f, radius * cos(dx + alpha)));
        addVertice(new Vertice((radius - ring) * sin(dx + alpha), 0.0f, (radius - ring) * cos(dx + alpha)));

        addNormal(new Vertice(0.0f,1.0f,0.0f));
        addNormal(new Vertice(0.0f,1.0f,0.0f));
        addNormal(new Vertice(0.0f,1.0f,0.0f));

        addNormal(new Vertice(0.0f,1.0f,0.0f));
        addNormal(new Vertice(0.0f,1.0f,0.0f));
        addNormal(new Vertice(0.0f,1.0f,0.0f));
        
        addTexture(new Vertice(0.5 * sin(alpha) + 0.5, 0.5 * cos(alpha) + 0.5, 0));
        addTexture(new Vertice(0.5 * sin(alpha + dx) + 0.5, 0.5 * cos(alpha + dx) + 0.5, 0));
        addTexture(new Vertice(dif * sin(alpha) + 0.5, dif * cos(alpha) + 0.5, 0));

        addTexture(new Vertice(dif * sin(alpha) + 0.5, dif * cos(alpha) + 0.5, 0));
        addTexture(new Vertice(0.5 * sin(alpha + dx) + 0.5, 0.5 * cos(alpha + dx) + 0.5, 0));
        addTexture(new Vertice(dif * sin(alpha + dx) + 0.5, dif * cos(alpha + dx) + 0.5, 0));
    }
}
