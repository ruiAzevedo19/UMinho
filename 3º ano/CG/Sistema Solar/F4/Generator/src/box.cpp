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

            addNormal(new Vertice(0.0f, 0.0f, 1.0f));
            addNormal(new Vertice(0.0f, 0.0f, 1.0f));
            addNormal(new Vertice(0.0f, 0.0f, 1.0f));

            addNormal(new Vertice(0.0f, 0.0f, 1.0f));
            addNormal(new Vertice(0.0f, 0.0f, 1.0f));
            addNormal(new Vertice(0.0f, 0.0f, 1.0f));

            addTexture(new Vertice((float)i/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)i/division,(float)(j+1)/division,0.0f));

            addTexture(new Vertice((float)(i+1)/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)(j+1)/division,0.0f));
            addTexture(new Vertice((float)i/division,(float)(j+1)/division,0.0f));

            /* face traseira */
            addVertice(new Vertice(px,py,pz-dimZ));
            addVertice(new Vertice(px,py+dy,pz-dimZ));
            addVertice(new Vertice(px+dx,py,pz-dimZ));

            addVertice(new Vertice(px+dx,py,pz-dimZ));
            addVertice(new Vertice(px,py+dy,pz-dimZ));
            addVertice(new Vertice(px+dx,py+dy,pz-dimZ));

            addNormal(new Vertice(0.0f, 0.0f, -1.0f));
            addNormal(new Vertice(0.0f, 0.0f, -1.0f));
            addNormal(new Vertice(0.0f, 0.0f, -1.0f));

            addNormal(new Vertice(0.0f, 0.0f, -1.0f));
            addNormal(new Vertice(0.0f, 0.0f, -1.0f));
            addNormal(new Vertice(0.0f, 0.0f, -1.0f));

            addTexture(new Vertice((float)i/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)i/division,(float)(j+1)/division,0.0f));

            addTexture(new Vertice((float)(i+1)/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)(j+1)/division,0.0f));
            addTexture(new Vertice((float)i/division,(float)(j+1)/division,0.0f));
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

            addNormal(new Vertice(-1.0f, 0.0f, 0.0f));
            addNormal(new Vertice(-1.0f, 0.0f, 0.0f));
            addNormal(new Vertice(-1.0f, 0.0f, 0.0f));

            addNormal(new Vertice(-1.0f, 0.0f, 0.0f));
            addNormal(new Vertice(-1.0f, 0.0f, 0.0f));
            addNormal(new Vertice(-1.0f, 0.0f, 0.0f));

            addTexture(new Vertice((float)(division - i)/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)(division - i)/division,(float)(j+1)/division,0.0f));
            addTexture(new Vertice((float)(division - i - 1)/division,(float)j/division,0.0f));

            addTexture(new Vertice((float)(division - i)/division,(float)(j + 1)/division,0.0f));
            addTexture(new Vertice((float)(division - i - 1)/division,(float)(j+1)/division,0.0f));
            addTexture(new Vertice((float)(division - i - 1)/division,(float)j/division,0.0f));

            /* face direita */
            addVertice(new Vertice(px+dimX,py,pz));
            addVertice(new Vertice(px+dimX,py,pz-dz));
            addVertice(new Vertice(px+dimX,py+dy,pz));

            addVertice(new Vertice(px+dimX,py+dy,pz));
            addVertice(new Vertice(px+dimX,py,pz-dz));
            addVertice(new Vertice(px+dimX,py+dy,pz-dz));

            addNormal(new Vertice(1.0f, 0.0f, 0.0f));
            addNormal(new Vertice(1.0f, 0.0f, 0.0f));
            addNormal(new Vertice(1.0f, 0.0f, 0.0f));

            addNormal(new Vertice(1.0f, 0.0f, 0.0f));
            addNormal(new Vertice(1.0f, 0.0f, 0.0f));
            addNormal(new Vertice(1.0f, 0.0f, 0.0f));

            addTexture(new Vertice((float)i/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)i+1/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)i/division,(float)(j+1)/division,0.0f));

            addTexture(new Vertice((float)i/division,(float)(j + 1)/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)(j+1)/division,0.0f));

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

            addNormal(new Vertice(0.0f, -1.0f, 0.0f));
            addNormal(new Vertice(0.0f, -1.0f, 0.0f));
            addNormal(new Vertice(0.0f, -1.0f, 0.0f));

            addNormal(new Vertice(0.0f, -1.0f, 0.0f));
            addNormal(new Vertice(0.0f, -1.0f, 0.0f));
            addNormal(new Vertice(0.0f, -1.0f, 0.0f));

            addTexture(new Vertice((float)i/division,(float)(division - j)/division,0.0f));
            addTexture(new Vertice((float)i/division,(float)(division - j - 1)/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)(division - j - 1)/division,0.0f));

            addTexture(new Vertice((float)i/division,(float)(division - j)/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)(division - j - 1)/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)(division)/division,0.0f));

            /* face direita */
            addVertice(new Vertice(px,py+dimY,pz));
            addVertice(new Vertice(px+dx,py+dimY,pz-dz));
            addVertice(new Vertice(px,py+dimY,pz-dz));

            addVertice(new Vertice(px,py+dimY,pz));
            addVertice(new Vertice(px+dx,py+dimY,pz));
            addVertice(new Vertice(px+dx,py+dimY,pz-dz));

            addNormal(new Vertice(0.0f, 1.0f, 0.0f));
            addNormal(new Vertice(0.0f, 1.0f, 0.0f));
            addNormal(new Vertice(0.0f, 1.0f, 0.0f));

            addNormal(new Vertice(0.0f, 1.0f, 0.0f));
            addNormal(new Vertice(0.0f, 1.0f, 0.0f));
            addNormal(new Vertice(0.0f, 1.0f, 0.0f));

            addTexture(new Vertice((float)i/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)(j+1)/division,0.0f));
            addTexture(new Vertice((float)i/division,(float)(j+1)/division,0.0f));

            addTexture(new Vertice((float)i/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)j/division,0.0f));
            addTexture(new Vertice((float)(i+1)/division,(float)(j+1)/division,0.0f));

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
