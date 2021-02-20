#include "../headers/plane.h"
#include "../headers/primitive.h"
#include "../headers/vertex.h"
#include "../headers/box.h"
#include "../headers/cone.h"
#include "../headers/sphere.h"

#include <iostream>
#include <fstream>
#include <vector>
#include <cstring>

using namespace std;

Primitive *p = NULL;
vector<Vertex*> v;

void writeFile(string file_name){
    bool suc = true;
    fstream(file);
    file.open("../../db/" + file_name, fstream::out);

    if( file.is_open() ){
        file << to_string(v.size()) << endl;
        for (vector<Vertex*>::iterator it = v.begin() ; it != v.end(); ++it){
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
    v = p->getVertexes();
}

void genBox(float dimX, float dimY, float dimZ, int division){
    p = new Box(dimX,dimY,dimZ,division);
    v = p->getVertexes();
}

void genSphere(float radius, int slices, int stacks){
    p = new Sphere(radius, slices, stacks);
    v = p->getVertexes();
}

void genCone(float radius, float height, int slices, int stacks){
    p = new Cone(radius,height,slices,stacks);
    v = p->getVertexes();
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
    return 0;
}
