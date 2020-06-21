#ifndef _BEZIER_H_
#define _BEZIER_H_ 

#include <vector>
#include "vertice.h"

using namespace std;

class BezierPatch{
    public:
        void readBezierFile(int tessellation, string input, string output);
        void bezierCurve(vector<Vertice> *vertices, vector<int> patch, vector<Vertice*> points, float u, float v, float interval);
        Vertice calculateVertices(vector<int> patch, vector<Vertice*>vertices, float u, float v);
};

#endif 
