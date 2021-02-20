#include "../headers/bezier.h"
#include <cstdio>
#include <iostream>
#include <sstream>
#include <string.h>
#include <vector> 

using namespace std;

std::vector<Vertice*> textures;
std::vector<Vertice*> normals;

void mulMatrixVector(float* m, float* v, float* res){
    for(int j = 0; j < 4; ++j){
        res[j] = 0;
        for(int k = 0; k < 4; ++k)
            res[j] += v[k] * m[j * 4 + k];
    }
}

void cross(float* a, float* b, float* res){
    res[0] = a[1] * b[2] - a[2] * b[1];
    res[1] = a[2] * b[0] - a[0] * b[2];
    res[2] = a[0] * b[1] - a[1] * b[0];
}

Vertice BezierPatch::calculateVertices(vector<int> patch, vector<Vertice*> vertices, float u, float v){
    #define C(i)    vertices[patch[i]]

    float MV[4];

    float px[4];
    float py[4];
    float pz[4];

    float mx[4];
    float my[4];
    float mz[4];

    float bezierMatrix[4][4] = { { -1.0f ,  3.0f , -3.0f , 1.0f },
                                 {  3.0f , -6.0f ,  3.0f , 0.0f },
                                 { -3.0f ,  3.0f ,  0.0f , 0.0f },
                                 {  1.0f ,  0.0f ,  0.0f , 0.0f } };

    float U[4] = { u * u * u, u * u, u, 1};
    float V[4] = { v * v * v, v * v, v, 1};

    float Px[4][4] = { { C(0)->getX() , C(1)->getX() , C(2)->getX() , C(3)->getX() },
                       { C(4)->getX() , C(5)->getX() , C(6)->getX() , C(7)->getX() },
                       { C(8)->getX() , C(9)->getX() , C(10)->getX(), C(11)->getX()},
                       { C(12)->getX(), C(13)->getX(), C(14)->getX(), C(15)->getX()} }; 
    float Py[4][4] = { { C(0)->getY() , C(1)->getY() , C(2)->getY() , C(3)->getY() },
                       { C(4)->getY() , C(5)->getY() , C(6)->getY() , C(7)->getY() },
                       { C(8)->getY() , C(9)->getY() , C(10)->getY(), C(11)->getY()},
                       { C(12)->getY(), C(13)->getY(), C(14)->getY(), C(15)->getY()} }; 
    float Pz[4][4] = { { C(0)->getZ() , C(1)->getZ() , C(2)->getZ() , C(3)->getZ() },
                       { C(4)->getZ() , C(5)->getZ() , C(6)->getZ() , C(7)->getZ() },
                       { C(8)->getZ() , C(9)->getZ() , C(10)->getZ(), C(11)->getZ()},
                       { C(12)->getZ(), C(13)->getZ(), C(14)->getZ(), C(15)->getZ()} }; 

    mulMatrixVector((float*)bezierMatrix,V,MV);
    mulMatrixVector((float*)Px,MV,px);
    mulMatrixVector((float*)Py,MV,py);
    mulMatrixVector((float*)Pz,MV,pz);

    mulMatrixVector((float*)bezierMatrix,px,mx);
    mulMatrixVector((float*)bezierMatrix,py,my);
    mulMatrixVector((float*)bezierMatrix,pz,mz);

    float x = U[0] * mx[0] + U[1] * mx[1] + U[2] * mx[2] + U[3] * mx[3];
    float y = U[0] * my[0] + U[1] * my[1] + U[2] * my[2] + U[3] * my[3];
    float z = U[0] * mz[0] + U[1] * mz[1] + U[2] * mz[2] + U[3] * mz[3];

    float normal[3];
    cross(U,V,normal);

    normals.push_back(new Vertice(normal[0],normal[1],normal[2]));

    #undef C

 	return Vertice(x, y, z);
}

void BezierPatch::bezierCurve(std::vector<Vertice> *vertices, std::vector<int> patch, std::vector<Vertice*> points,
	float u, float v, float interval) {

	Vertice p1 = calculateVertices(patch, points, u, v);
	Vertice p2 = calculateVertices(patch, points, u, v + interval);
	Vertice p3 = calculateVertices(patch, points, u + interval, v);
	Vertice p4 = calculateVertices(patch, points, u + interval, v + interval);

	vertices->push_back(p1);
	vertices->push_back(p4);
	vertices->push_back(p2);

	vertices->push_back(p4);
	vertices->push_back(p1);
	vertices->push_back(p3);

    textures.push_back(new Vertice(1.0f - u, 1.0f - v, 0.0f));
    textures.push_back(new Vertice(1.0f - u - interval, 1.0f - v - interval, 0.0f));
    textures.push_back(new Vertice(1.0f - u, 1.0f - v - interval,0.0f));

    textures.push_back(new Vertice(1.0f - u - interval, 1.0f - v - interval, 0.0f));
    textures.push_back(new Vertice(1.0f - u, 1.0f - v, 0.0f));
    textures.push_back(new Vertice(1.0f - u - interval, 1.0f - v, 0.0f));
}

void BezierPatch::readBezierFile(int tessellation, string input, string output){
	FILE *in;
	FILE *out;
	char path[100];

	strcpy(path, "../db/patches/");
	strcat(path, input.c_str());

	in = fopen(path, "r");
	if( !in ) 
        return;

	char line[512];

    /* a primeira linha do ficheiro corresponde ao numero de patches que a primitiva tem */
	fgets(line, 511, in);
	int nPatches = atoi(line);
    /* pacthes é um vector de vectores, onde cada elemento contém o conjunto de indices do respetivo patch */
	vector<vector<int>> patches;
	int index;
    /* para cada patch são adicionados os seus indices */ 
	for (int i = 0; i < nPatches; i++) {
		fgets(line, 511, in);
		stringstream strstream(line);
		patches.push_back(vector<int>());

		while (strstream >> index) {
			patches[i].push_back(index);
			if (strstream.peek() == ',')
				strstream.ignore();
		}
	}

    /* lê o número de pontos de controlo */
	fgets(line, 511, in);
	int nVertices = atoi(line);

	vector<Vertice*> controlVertices;
	float x, y, z;
    /* para cada linha são retiradas as coordenadas x, y e z dos pontos de controlo */
	for (int i = 0; i < nVertices; i++) {
		fgets(line, 1023, in);
		stringstream strstream(line);
		strstream >> x;

		if (strstream.peek() == ',') 
			strstream.ignore();

		strstream >> y;
		if (strstream.peek() == ',')
			strstream.ignore();

		strstream >> z;
		if (strstream.peek() == ',')
			strstream.ignore();

		controlVertices.push_back(new Vertice(x, y, z));
	}
	fclose(in);

    /* neste vector são guardados os pontos resultantes do cálculo dos pontos de bezier */
	std::vector<Vertice> results;
    float u, v, interval;
	/* ciclo que gera os pontos */
	for (int i = 0; i < nPatches; i++) {
		u = 0.0;
		v = 0.0;
		interval = (float) 1.0 / tessellation;

		for (int j = 0; j < tessellation; j++) {
			for (int k = 0; k < tessellation; k++) {
				bezierCurve(&results, patches[i], controlVertices, u, v, interval);
				v += interval;
			}
			u += interval;
			v = 0.0;
		}
	}
    /* os pontos são guardados num ficheiro .3d */
	int size = results.size();

	strcpy(path, "../../db/");
	strcat(path, output.c_str());

	out = fopen(path, "w");
	if ( !out ) 
        return;


	size += 1;

    fprintf(out,"Pontos\n");
	fprintf(out, "%d\n", size);
	for (int i = 1; i < size; i++)
		fprintf(out, "%f %f %f\n", results[i].getX(), results[i].getY(), results[i].getZ());

    fprintf(out,"Normais\n");
    fprintf(out, "%d\n",(int)normals.size());
    for(int i = 0; i < normals.size(); i++)
        fprintf(out,"%f %f %f\n", normals[i]->getX(), normals[i]->getY(), normals[i]->getZ());

    fprintf(out,"Texturas\n");
    fprintf(out, "%d\n",(int)textures.size());
    for(int i = 0; i < textures.size(); i++)
        fprintf(out,"%f %f %f\n", textures[i]->getX(), textures[i]->getY(), textures[i]->getZ());

	fclose(out);

}
