#include "../headers/cone.h"
#include <math.h>

Cone::Cone(float radius, float height, int slices, int stacks){
    this->radius = radius;
    this->height = height;
    this->slices = slices;
    this->stacks = stacks;

    genVertex();
}

void Cone::drawButtom(){
    float dx = (float)(2 * M_PI) / (float)slices;
    float alpha = 0;

    for(int i = 0; i < slices; i++){
        addVertex(new Vertex(radius * sin(alpha),0,radius * cos(alpha)));
        addVertex(new Vertex(0,0,0));
        addVertex(new Vertex(radius * sin(alpha + dx),0,radius * cos(alpha+dx)));
        alpha += dx;
    }
}

void Cone::drawFaces(){
    float h = height / stacks;
    float hi,hs;
    float ri, rs;
    float alpha;
    float dx = (float)(2 * M_PI) / (float)slices;
    float x1,x2,x3,x4,z1,z2,z3,z4;

    for(int i = 0; i < stacks; i++){
        hi = h * (float)i;
        hs = h * (float)(i+1);
        ri = radius - ((radius * hi) / height);
        rs = radius - ((radius * hs) / height);
        for(int j = 0; j < slices; j++){
            alpha = dx * j;
            
            x1 = ri * sin(alpha);
            x2 = ri * sin(alpha + dx);
            x3 = rs * sin(alpha);
            x4 = rs * sin(alpha + dx);

            z1 = ri * cos(alpha);
            z2 = ri * cos(alpha + dx);
            z3 = rs * cos(alpha);
            z4 = rs * cos(alpha + dx);

            addVertex(new Vertex(x1,hi,z1));
            addVertex(new Vertex(x2,hi,z2));
            addVertex(new Vertex(x3,hs,z3));

            addVertex(new Vertex(x2,hi,z2));
            addVertex(new Vertex(x4,hs,z4));
            addVertex(new Vertex(x3,hs,z3));
        }
    }
}

void Cone::genVertex(){
    drawButtom();
    drawFaces();
}
