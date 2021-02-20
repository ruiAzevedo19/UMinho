#include "../headers/translation.h"
#include <math.h>

Translation::Translation(float x, float y, float z, float time, vector<Vertice*> v){
    this->x = x;
    this->y = y;
    this->z = z;
    this->time = time;
	this->catmull = v;
}

float Translation::getX(){
    return x;
}

float Translation::getY(){
    return y;
}

float Translation::getZ(){
    return z;
}

float Translation::getTime(){
    return time;
}

//-----------------------------------------------------------------------------------------------

void Translation::buildRotMatrix(float* x, float* y, float* z, float* m) {

	m[0] = x[0]; m[1] = x[1]; m[2] = x[2]; m[3] = 0;
	m[4] = y[0]; m[5] = y[1]; m[6] = y[2]; m[7] = 0;
	m[8] = z[0]; m[9] = z[1]; m[10] = z[2]; m[11] = 0;
	m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 1;
}


void Translation::cross(float* a, float* b, float* res) {

	res[0] = a[1] * b[2] - a[2] * b[1];
	res[1] = a[2] * b[0] - a[0] * b[2];
	res[2] = a[0] * b[1] - a[1] * b[0];
}


void Translation::normalize(float* a) {

	float l = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
	a[0] = a[0] / l;
	a[1] = a[1] / l;
	a[2] = a[2] / l;
}


float Translation::length(float* v) {

	float res = sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
	return res;

}

void Translation::multMatrixVector(float* m, float* v, float* res) {

	for (int j = 0; j < 4; ++j) {
		res[j] = 0;
		for (int k = 0; k < 4; ++k) {
			res[j] += v[k] * m[j * 4 + k];
		}
	}

}

//float p[point_count][3] = { {-1,-1,0},{-1,1,0},{1,1,0},{0,0,0},{1,-1,0} };

void Translation::getCatmullRomPoint(float t, Vertice p0, Vertice p1, Vertice p2, Vertice p3, float* pos, float* deriv) {

	// catmull-rom matrix
	float m[4][4] = { {-0.5f,  1.5f, -1.5f,  0.5f},
						{ 1.0f, -2.5f,  2.0f, -0.5f},
						{-0.5f,  0.0f,  0.5f,  0.0f},
						{ 0.0f,  1.0f,  0.0f,  0.0f} };

	// Compute A = M * P
	float a[4][3];

	for (int i = 0; i < 4; i++) {
		a[i][0] = m[i][0] * p0.getX() + m[i][1] * p1.getX() + m[i][2] * p2.getX() + m[i][3] * p3.getX();
		a[i][1] = m[i][0] * p0.getY() + m[i][1] * p1.getY() + m[i][2] * p2.getY() + m[i][3] * p3.getY();
		a[i][2] = m[i][0] * p0.getZ() + m[i][1] * p1.getZ() + m[i][2] * p2.getZ() + m[i][3] * p3.getZ();

	}

	// Compute pos = T * A
	for (int i = 0; i < 3; i++) pos[i] = powf(t, 3) * a[0][i] + powf(t, 2) * a[1][i] + t * a[2][i] + a[3][i];

	// compute deriv = T' * A
	for (int i = 0; i < 3; i++) deriv[i] = 3 * powf(t, 2) * a[0][i] + 2 * t * a[1][i] + a[2][i];
}


// given  global t, returns the point in the curve
void Translation::getGlobalCatmullRomPoint(float gt, float* pos, float* deriv, vector<Vertice*> v) {

	int point_count = v.size();
	float t = gt * point_count; // this is the real global t
	int index = floor(t);  // which segment
	t = t - index; // where within  the segment

	// indices store the points
	int indices[4];
	indices[0] = (index + point_count - 1) % point_count;
	indices[1] = (indices[0] + 1) % point_count;
	indices[2] = (indices[1] + 1) % point_count;
	indices[3] = (indices[2] + 1) % point_count;

	getCatmullRomPoint(t, *v[indices[0]], *v[indices[1]], *v[indices[2]], *v[indices[3]], pos, deriv);
}

void Translation::renderCatmullRomCurve() {
	float pos[3], deriv[3];

	// draw curve using line segments with GL_LINE_LOOP
	float tessellation_lvl = 0.01;

	glBegin(GL_LINE_LOOP);
	for (float gt = 0; gt < 1; gt += tessellation_lvl) {
		getGlobalCatmullRomPoint(gt, pos, deriv, catmull);
		glVertex3f(pos[0], pos[1], pos[2]);
	}
	glEnd();

}

void Translation::execute(void) {

	float te, gt;
	float deriv[3];
	static float y[3] = { 0.f,1.f,0.f };
	float pos[3], z[3], m[16];

	if (time != 0 && catmull.size() != 0) {
		te = glutGet(GLUT_ELAPSED_TIME) % (int)(time * 1000);
		gt = te / (time * 1000);
		//renderCatmullRomCurve();
		getGlobalCatmullRomPoint(gt, pos, deriv, catmull);
		glTranslatef(pos[0], pos[1], pos[2]);
		normalize(deriv);
		normalize(y);
		cross(deriv, y, z);
		cross(z, deriv, y);
		buildRotMatrix(deriv, y, z, m);
		glMultMatrixf(m);
	}else glTranslatef(this->x, this->y, this->z);
}




//-----------------------------------------------------------------------------------------------
