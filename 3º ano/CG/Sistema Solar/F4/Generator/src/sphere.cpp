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
    float nx1,nx2,nx3,nx4,ny1,ny2,ny3,ny4,nz1,nz2,nz3,nz4;

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
    
                nx1 = sin(alpha) * cos(beta);
                ny1 = sin(beta);
                nz1 = cos(alpha) * cos(beta);
                
                nx2 = sin(alpha + dx) * cos(beta);
                ny2 = sin(beta);
                nz2 = cos(alpha + dx) * cos(beta);

                nx3 = sin(alpha) * cos(beta + dy);
                ny3 = sin(beta + dy);
                nz3 = cos(alpha) * cos(beta + dy);

                nx4 = sin(alpha + dx) * cos(beta + dy);
                ny4 = sin(beta + dy);
                nz4 = cos(alpha + dx) * cos(beta + dy);
    
                addVertice(new Vertice(x1,y1,z1));
                addVertice(new Vertice(x2,y2,z2));
                addVertice(new Vertice(x3,y3,z3));

                addVertice(new Vertice(x2,y2,z2));
                addVertice(new Vertice(x4,y4,z4));
                addVertice(new Vertice(x3,y3,z3));

                addNormal(new Vertice(nx1,ny1,nz1));
                addNormal(new Vertice(nx2,ny2,nz2));
                addNormal(new Vertice(nx3,ny3,nz3));

                addNormal(new Vertice(nx2,ny2,nz2));
                addNormal(new Vertice(nx4,ny4,nz4));
                addNormal(new Vertice(nx3,ny3,nz3));

                addTexture(new Vertice((float)j/slices,(stacks - (float)i - 1.0)/stacks,0.0f));
                addTexture(new Vertice((float)(j+1)/slices,(stacks - (float)i - 1.0)/stacks,0.0f));
                addTexture(new Vertice((float)j/slices,(stacks - (float)i)/stacks,0.0f));

                addTexture(new Vertice((float)(j+1)/slices,(stacks - (float)i - 1.0)/stacks,0.0f));
                addTexture(new Vertice((float)(j+1)/slices,(stacks - (float)i)/stacks,0.0f));
                addTexture(new Vertice((float)j/slices,(stacks - (float)i)/stacks,0.0f));
            }
    }
}
