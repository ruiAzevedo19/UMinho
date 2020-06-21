#include "../headers/box.h"
#include <iostream>
#include <string>
#include <sstream>

using namespace std;

Box::Box(float dimX, float dimY, float dimZ, int division){
    this->dimX = dimX;
    this->dimY = dimY;
    this->dimZ = dimZ;

    this->cx = dimX / 2;
    this->cy = dimY / 2;
    this->cz = dimZ / 2;

    this->dx = dimX / division;
    this->dy = dimY / division;
    this->dz = dimZ / division;

    this->division = division;

    genVertex();
}

void Box::drawXY(){
    float px = -cx;
    float py;
    float pz = cz;

    for(int i = 0; i < division; i++){
        py = -cy;
        for(int j = 0; j < division; j++){
            /* face frontal */
            addVertex(new Vertex(px,py,pz));
            addVertex(new Vertex(px+dx,py,pz));
            addVertex(new Vertex(px,py+dy,pz));

            addVertex(new Vertex(px+dx,py,pz));
            addVertex(new Vertex(px+dx,py+dy,pz));
            addVertex(new Vertex(px,py+dy,pz));

            /* face traseira */
            addVertex(new Vertex(px,py,pz-dimZ));
            addVertex(new Vertex(px,py+dy,pz-dimZ));
            addVertex(new Vertex(px+dx,py,pz-dimZ));

            addVertex(new Vertex(px+dx,py,pz-dimZ));
            addVertex(new Vertex(px,py+dy,pz-dimZ));
            addVertex(new Vertex(px+dx,py+dy,pz-dimZ));

            py += dy;
        }
        px += dx;
    }
}

void Box::drawYZ(){
    float px = -cx;
    float py;
    float pz = cz;

    for(int i = 0; i < division; i++){
        py = -cy;
        for(int j = 0; j < division; j++){
            /* face esquerda */
            addVertex(new Vertex(px,py,pz));
            addVertex(new Vertex(px,py+dy,pz));
            addVertex(new Vertex(px,py,pz-dz));

            addVertex(new Vertex(px,py+dy,pz));
            addVertex(new Vertex(px,py+dy,pz-dz));
            addVertex(new Vertex(px,py,pz-dz));

            /* face direita */
            addVertex(new Vertex(px+dimX,py,pz));
            addVertex(new Vertex(px+dimX,py,pz-dz));
            addVertex(new Vertex(px+dimX,py+dy,pz));

            addVertex(new Vertex(px+dimX,py+dy,pz));
            addVertex(new Vertex(px+dimX,py,pz-dz));
            addVertex(new Vertex(px+dimX,py+dy,pz-dz));

            py += dy;
        }
        pz -= dz;
    }
}

void Box::drawXZ(){
    float px = -cx;
    float py;
    float pz = cz;

    for(int i = 0; i < division; i++){
        pz = cz;
        for(int j = 0; j < division; j++){
            /* face esquerda */
            addVertex(new Vertex(px,py,pz));
            addVertex(new Vertex(px,py,pz-dz));
            addVertex(new Vertex(px+dx,py,pz-dz));

            addVertex(new Vertex(px,py,pz));
            addVertex(new Vertex(px+dx,py,pz-dz));
            addVertex(new Vertex(px+dx,py,pz));

            /* face direita */
            addVertex(new Vertex(px,py+dimY,pz));
            addVertex(new Vertex(px+dx,py+dimY,pz-dz));
            addVertex(new Vertex(px,py+dimY,pz-dz));

            addVertex(new Vertex(px,py+dimY,pz));
            addVertex(new Vertex(px+dx,py+dimY,pz));
            addVertex(new Vertex(px+dx,py+dimY,pz-dz));

            pz -= dz;
        }
        px += dx;
    }
}

void Box::genVertex(){
    drawXY();
    drawYZ();
    drawXZ();
}
