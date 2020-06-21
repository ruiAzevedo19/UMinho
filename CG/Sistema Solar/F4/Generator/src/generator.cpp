#include "../headers/plane.h"
#include "../headers/primitive.h"
#include "../headers/vertice.h"
#include "../headers/box.h"
#include "../headers/cone.h"
#include "../headers/sphere.h"
#include "../headers/ring.h"
#include "../headers/bezier.h"

#include <iostream>
#include <fstream>
#include <vector>
#include <cstring>

using namespace std;

Primitive *p = NULL;
vector<Vertice*> v;
vector<Vertice*> n;
vector<Vertice*> t;


void writeFile(string file_name){
    bool suc = true;
    fstream(file);
    file.open("../../db/" + file_name, fstream::out);

    if( file.is_open() ){
        file << "Pontos" << endl;
        file << to_string(v.size()) << endl;
        for (vector<Vertice*>::iterator it = v.begin() ; it != v.end(); ++it){
            file << (*it)->toString() << endl;
            cout << (*it)->toString() << endl;
        }
        file << "Normais" << endl;
        file << to_string(n.size()) << endl;
        for (vector<Vertice*>::iterator it = n.begin() ; it != n.end(); ++it){
            file << (*it)->toString() << endl;
            cout << (*it)->toString() << endl;
        }
        file << "Texturas" << endl;
        file << to_string(t.size()) << endl;
        for (vector<Vertice*>::iterator it = t.begin() ; it != t.end(); ++it){
            file << (*it)->toString() << endl;
            cout << (*it)->toString() << endl;
        }
        file.close();
    }
    else
        cout << "Error opening file " + file_name << endl;
}

void genPlane(float dimX, float dimZ){
    p = new Plane(dimX,dimZ);
    v = p->getVertices();
}

void genBox(float dimX, float dimY, float dimZ, int division){
    p = new Box(dimX,dimY,dimZ,division);
    v = p->getVertices();
    n = p->getNormals();
    t = p->getTextures();
}

void genSphere(float radius, int slices, int stacks){
    p = new Sphere(radius, slices, stacks);
    v = p->getVertices();
    n = p->getNormals();
    t = p->getTextures();
}

void genCone(float radius, float height, int slices, int stacks){
    p = new Cone(radius,height,slices,stacks);
    v = p->getVertices();
    n = p->getNormals();
    t = p->getTextures();
}

void genRing(int slices, float radius, float ring){
    p = new Ring(slices,radius,ring);
    v = p->getVertices();
    n = p->getNormals();
    t = p->getTextures();
}

int main(int argc, char** argv){
    if( argc == 5 && !strcmp(argv[1],"plane") ){
        genPlane(stof(argv[2]),stof(argv[3]));
        writeFile(argv[4]);
    }
    if( argc == 7 && !strcmp(argv[1],"box") ){
        genBox(stof(argv[2]),stof(argv[3]),stof(argv[4]),atoi(argv[5]));
        writeFile(argv[6]);
    }
    if( argc == 6 && !strcmp(argv[1],"sphere") ){
        genSphere(stof(argv[2]), atoi(argv[3]), atoi(argv[4]));
        writeFile(argv[5]);
    }
    if( argc == 7 && !strcmp(argv[1],"cone") ){
        genCone(stof(argv[2]),stof(argv[3]),atoi(argv[4]),atoi(argv[5]));
        writeFile(argv[6]);
    }
    if( argc == 6 && !strcmp(argv[1],"ring") ){
        genRing(atoi(argv[2]),stof(argv[3]),stof(argv[4]));
        writeFile(argv[5]);
    }
    if( argc == 5 && !strcmp(argv[1],"bezier") ){
        int t = atoi(argv[2]);
        string in = argv[3];
        string out = argv[4];
        BezierPatch bp;
        bp.readBezierFile(t,in,out);
    }
    return 0;
}
