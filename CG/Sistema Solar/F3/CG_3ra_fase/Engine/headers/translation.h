#ifndef _TRANSLATION_H_
#define _TRANSLATION_H_

#include "../../Generator/headers/vertice.h"
#include "transformation.h"
#include <vector>

class Translation : public Transformation{
    private:
        float x;
        float y;
        float z;
        float time;
        vector<Vertice*> catmull;
    public:
        Translation(float x, float y, float z, float time, vector<Vertice*> v);
        float getX();
        float getY();
        float getZ();
        float getTime();
        void buildRotMatrix(float* x, float* y, float* z, float* m);
		void cross(float* a, float* b, float* res);
		void normalize(float* a);
		float length(float* v); 
		void multMatrixVector(float* m, float* v, float* res);
		void getCatmullRomPoint(float t, Vertice p0, Vertice p1, Vertice p2, Vertice p3, float* pos, float* deriv);
		void getGlobalCatmullRomPoint(float gt, float* pos, float* deriv, vector<Vertice*> v);
		void renderCatmullRomCurve();
		void execute(void);
};

#endif
