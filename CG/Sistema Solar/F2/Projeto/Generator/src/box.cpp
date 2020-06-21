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

    genVertices();
}

void Box::drawXY(){
    float px = -cx;
    float py;
    float pz = cz;

    for(int i = 0; i < division; i++){
        py = -cy;
        for(int j = 0; j < division; j++){
            /* face frontal */
            addVertice(new Vertice(px,py,pz));
            addVertice(new Vertice(px+dx,py,pz));
            addVertice(new Vertice(px,py+dy,pz));

            addVertice(new Vertice(px+dx,py,pz));
            addVertice(new Vertice(px+dx,py+dy,pz));
            addVertice(new Vertice(px,py+dy,pz));

            /* face traseira */
            addVertice(new Vertice(px,py,pz-dimZ));
            addVertice(new Vertice(px,py+dy,pz-dimZ));
            addVertice(new Vertice(px+dx,py,pz-dimZ));

            addVertice(new Vertice(px+dx,py,pz-dimZ));
            addVertice(new Vertice(px,py+dy,pz-dimZ));
            addVertice(new Vertice(px+dx,py+dy,pz-dimZ));

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
            addVertice(new Vertice(px,py,pz));
            addVertice(new Vertice(px,py+dy,pz));
            addVertice(new Vertice(px,py,pz-dz));

            addVertice(new Vertice(px,py+dy,pz));
            addVertice(new Vertice(px,py+dy,pz-dz));
            addVertice(new Vertice(px,py,pz-dz));

            /* face direita */
            addVertice(new Vertice(px+dimX,py,pz));
            addVertice(new Vertice(px+dimX,py,pz-dz));
            addVertice(new Vertice(px+dimX,py+dy,pz));

            addVertice(new Vertice(px+dimX,py+dy,pz));
            addVertice(new Vertice(px+dimX,py,pz-dz));
            addVertice(new Vertice(px+dimX,py+dy,pz-dz));

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
            addVertice(new Vertice(px,py,pz));
            addVertice(new Vertice(px,py,pz-dz));
            addVertice(new Vertice(px+dx,py,pz-dz));

            addVertice(new Vertice(px,py,pz));
            addVertice(new Vertice(px+dx,py,pz-dz));
            addVertice(new Vertice(px+dx,py,pz));

            /* face direita */
            addVertice(new Vertice(px,py+dimY,pz));
            addVertice(new Vertice(px+dx,py+dimY,pz-dz));
            addVertice(new Vertice(px,py+dimY,pz-dz));

            addVertice(new Vertice(px,py+dimY,pz));
            addVertice(new Vertice(px+dx,py+dimY,pz));
            addVertice(new Vertice(px+dx,py+dimY,pz-dz));

            pz -= dz;
        }
        px += dx;
    }
}

void Box::genVertices(){
    drawXY();
    drawYZ();
    drawXZ();
}
