

#include <stdio.h>
#include <stdlib.h>

#define _USE_MATH_DEFINES
#include <math.h>
#include <vector>

#include <IL/il.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

#define ANG2RAD M_PI/180.0

#define COWBOYS 8
#define RAIO_COWBOYS 5
#define INDIOS 16
#define RAIO_INDIOS 25
#define ARVORES 1000
#define STEP_COWBOY 1.0f
#define STEP_INDIO 0.5f

/* fps variables */
int time1, time1base, frame = 0;
float fps = 0;
char title[50];

float step = 0.0;

float height = 2.0f;
float x = 0.0f;
float z = 0.0f;


float camX = 00, camY = 30, camZ = 40;
int startX, startY, tracking = 0;

int alpha = 0, beta = 45, r = 50;

unsigned int texture;
int imageWidth;
unsigned char *imageData;

// vectors to store vertex data temporarily
std::vector<float> position, normal, texCoord;
GLuint buffers[3];

void changeSize(int w, int h) {

	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width).
	if(h == 0)
		h = 1;

	// compute window's aspect ratio
	float ratio = w * 1.0 / h;

	// Reset the coordinate system before modifying
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	// Set the viewport to be the entire window
    glViewport(0, 0, w, h);

	// Set the correct perspective
	gluPerspective(45,ratio,1,1000);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}


float h(int i, int j) {

	return(imageData[i*imageWidth + j] * 0.2f);
}


float h2(float x, float z) {


	int x1, x2, z1, z2;
	float xGrid, zGrid;
	float fracX, fracZ, ha, hb;

	xGrid = x + imageWidth / 2;
	zGrid = z + imageWidth / 2;

	x1 = (int)floor(xGrid);
	x2 = x1 + 1;
	fracX = xGrid - x1;

	z1 = (int)floor(zGrid);
	z2 = z1 + 1;
	fracZ = zGrid - z1;

	ha = h(x2, z1) * fracX + (1 - fracX) * h(x1, z1);
	hb = h(x2, z2) * fracX + (1 - fracX) * h(x1, z2);

	return(hb * fracZ + (ha * (1 - fracZ)));
}

// res = b - a
void subtract(float *a, float *b, float *res) {

	res[0] = b[0] - a[0];
	res[1] = b[1] - a[1];
	res[2] = b[2] - a[2];
}

void cross(float *a, float *b, float *res) {

	res[0] = a[1]*b[2] - a[2]*b[1];
	res[1] = a[2]*b[0] - a[0]*b[2];
	res[2] = a[0]*b[1] - a[1]*b[0];
}


void normalize(float *a) {

	float l = sqrt(a[0]*a[0] + a[1] * a[1] + a[2] * a[2]);
	a[0] = a[0]/l;
	a[1] = a[1]/l;
	a[2] = a[2]/l;
}


void computeNormal(int i, int j) {

	// fill the normal vector with the normal for vertex at grid location (i,j)
    float v1[3],v2[3],res[3];
    float p1[3],p2[3],p3[3],p4[3];

	// calculate p1 with the same formula given in the slides
    p1[0]=i;
    p1[1]=h(i,j-1);
    p1[2]=j-1;

	// calculate p2 with the same formula given in the slides
    p2[0]=i;
    p2[1]=h(i,j+1);
    p2[2]=j+1;
	
	// v1 = p2 - p1
    subtract(p2,p1,v1);

	// calculate p3 with the same formula given in the slides
    p3[0]=i-1;
    p3[1]=h(i-1,j);
    p3[2]=j;

	// calculate p4 with the same formula given in the slides
    p4[0]=i+1;
    p4[1]=h(i+1,j);
    p4[2]=j;

	// v2 = p4 - p3
    subtract(p4,p3,v2);

	// produto cruzado
    cross(v1,v2,res);

	// normalizar o vetor resultante do produto escalar
    normalize(res);

    normal.push_back(res[0]);
    normal.push_back(res[1]);
    normal.push_back(res[2]);

}

void prepareTerrain() {

	for (int i = 1; i < imageWidth - 2; i++) {
		for (int j = 1; j < imageWidth - 1; j++) {

			computeNormal(i + 1, j);

			// fill texCoord vector with the texture coordinates for grid location (i+1,j)
            texCoord.push_back(i+1);
            texCoord.push_back(j);

			position.push_back(i - imageWidth*0.5f + 1);
			position.push_back(h(i + 1, j));
			position.push_back(j - imageWidth*0.5f);

			computeNormal(i, j);

			// fill texCoord vector with the texture coordinates for grid location (i,j)
			texCoord.push_back(i);
			texCoord.push_back(j);

			position.push_back(i - imageWidth*0.5f);
			position.push_back(h(i, j));
			position.push_back(j - imageWidth*0.5f);
		}
	}
	glGenBuffers(3, buffers);

	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
	glBufferData(GL_ARRAY_BUFFER, position.size() * sizeof(float), &(position[0]), GL_STATIC_DRAW);

	glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
	glBufferData(GL_ARRAY_BUFFER, normal.size() * sizeof(float), &(normal[0]), GL_STATIC_DRAW);

	glBindBuffer(GL_ARRAY_BUFFER, buffers[2]);
	glBufferData(GL_ARRAY_BUFFER, texCoord.size() * sizeof(float), &(texCoord[0]), GL_STATIC_DRAW);

	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_NORMAL_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);
}


void renderTerrain() {

	float white[4] = { 1.0f, 1.0f, 1.0f, 1.0f };
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, white);

	glBindTexture(GL_TEXTURE_2D, texture);
	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
	glVertexPointer(3, GL_FLOAT, 0, 0);

	glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
	glNormalPointer(GL_FLOAT, 0, 0);

	glBindBuffer(GL_ARRAY_BUFFER, buffers[2]);
	glTexCoordPointer(2, GL_FLOAT, 0, 0);

	for (int i = 1; i < imageWidth - 2; i++) {
		glDrawArrays(GL_TRIANGLE_STRIP, (imageWidth - 2) * 2 * i, (imageWidth - 2) * 2);
	}
	glBindTexture(GL_TEXTURE_2D, 0);

}


void drawTree() {

	glPushMatrix();
	glRotatef(-90, 1.0f, 0.0f, 0.0f);
	float brown[4] = { 1.0f,1.0f, 0.5f, 1.0f };
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, brown);
	glutSolidCone(0.25f, 4, 5, 1);

	float green[4] = { 0.0f, 0.5f + rand() * 0.5f / RAND_MAX,0.0f, 1.0f };
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, green);
	glTranslatef(0.0f, 0.0f, 2.0f);
	glutSolidCone(2.0f, 5.0f, 5.0f, 1.0f);
	glPopMatrix();
}


void placeTrees() {

	float r = 35.0;
	float alpha;
	float rr;
	float x,z;

	srand(31457);
	int arvores = 0;

	while (arvores < ARVORES) {

		rr = rand() * 150.0/ RAND_MAX;
		alpha = rand() * 6.28 / RAND_MAX;

		x = cos(alpha) * (rr + r);
		z = sin(alpha) * (rr + r);

		if (fabs(x) < 100 && fabs(z) < 100) {

			glPushMatrix();
			glTranslatef(x, h2(x, z),z);
			drawTree();
			glPopMatrix();
			arvores++;
		}
	}
}


void drawDonut() {

	float purple[4] = { 1.0f,0.0f,1.0f, 1.0f };
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, purple);
	glPushMatrix();
	glTranslatef(0.0,0.5,0.0);
	glutSolidTorus(0.5,1.25,8,16);
	glPopMatrix();
}


void drawIndios() {

	float angulo;
	float red[4] = { 1.0f,0.0f,0.0f, 1.0f };
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, red);
	for (int i = 0; i < INDIOS; i++) {

		angulo = i * 360.0/INDIOS + step * STEP_INDIO;
		glPushMatrix();
		glRotatef(angulo,0.0,1.0,0.0);
		glTranslatef(0.0,0.0,RAIO_INDIOS);
		glutSolidTeapot(1);
		glPopMatrix();
	}
}


void drawCowboys() {

	float angulo;
	float blue[4] = { 0.0f, 0.0f, 1.0f, 1.0f };
	glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, blue);

	for (int i = 0; i < COWBOYS; i++) {

		angulo = i * 360.0/COWBOYS + step * STEP_COWBOY;
		glPushMatrix();
		glRotatef(-angulo,0.0,1.0,0.0);
		glTranslatef(RAIO_COWBOYS,0.0,0.0);
		glutSolidTeapot(1);
		glPopMatrix();
	}
}


void drawScene() {

	renderTerrain();
	placeTrees();
	drawDonut();
	glPushMatrix();
	// move teapots up so that they are placed on top of the ground plane
	glTranslatef(0.0,1.0,0.0);
	drawCowboys();
	drawIndios();
	glPopMatrix();
}


void renderScene(void) {
	time1 = glutGet(GLUT_ELAPSED_TIME);

	frame++;
	if (time1 - time1base > 1000) {
		fps = frame * 1000.0 / (time1 - time1base);
		time1base = time1;
		frame = 0;

		sprintf(title, "%.2f", fps);

		glutSetWindowTitle(title);
		//printf("%s\n", title);
	}

	float pos[4] = {-1.0, 1.0, 1.0, 0.0};

	glClearColor(0.0f,0.0f,0.0f,0.0f);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glLoadIdentity();
	gluLookAt(camX, camY, camZ,
		      0.0,0.0,0.0,
			  0.0f,1.0f,0.0f);

	float lpos[4] = { 1,1,1,0 };
	glLightfv(GL_LIGHT0, GL_POSITION, lpos);

	drawScene();
	step++;

// End of frame
	glutSwapBuffers();
}



// write keyboard processing function

void processKeys(unsigned char key, int xx, int yy) {

}



void processMouseButtons(int button, int state, int xx, int yy) {

	if (state == GLUT_DOWN)  {
		startX = xx;
		startY = yy;
		if (button == GLUT_LEFT_BUTTON)
			tracking = 1;
		else if (button == GLUT_RIGHT_BUTTON)
			tracking = 2;
		else
			tracking = 0;
	}
	else if (state == GLUT_UP) {
		if (tracking == 1) {
			alpha += (xx - startX);
			beta += (yy - startY);
		}
		else if (tracking == 2) {

			r -= yy - startY;
			if (r < 3)
				r = 3.0;
		}
		tracking = 0;
	}
}


void processMouseMotion(int xx, int yy) {

	int deltaX, deltaY;
	int alphaAux, betaAux;
	int rAux;

	if (!tracking)
		return;

	deltaX = xx - startX;
	deltaY = yy - startY;

	if (tracking == 1) {


		alphaAux = alpha + deltaX;
		betaAux = beta + deltaY;

		if (betaAux > 85.0)
			betaAux = 85.0;
		else if (betaAux < -85.0)
			betaAux = -85.0;

		rAux = r;
	}
	else if (tracking == 2) {

		alphaAux = alpha;
		betaAux = beta;
		rAux = r - deltaY;
		if (rAux < 3)
			rAux = 3;
	}
	camX = rAux * sin(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
	camZ = rAux * cos(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
	camY = rAux * 							     sin(betaAux * 3.14 / 180.0);
}


void loadTexture() {

	unsigned int t, tw, th;
	unsigned char *texData;
	ilGenImages(1, &t);
	ilBindImage(t);
	ilLoadImage((ILstring)"relva.jpg");
	tw = ilGetInteger(IL_IMAGE_WIDTH);
	th = ilGetInteger(IL_IMAGE_HEIGHT);
	ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
	texData = ilGetData();

	glGenTextures(1, &texture);

	glBindTexture(GL_TEXTURE_2D, texture);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	
	/*
	* Other possible filters for mipmapping
	*/
	//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
	//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
	//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST);
	//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
	glGenerateMipmap(GL_TEXTURE_2D);
}


void init() {

	alpha = 0.0;
	beta = 0.0;

	unsigned int ima[1];

	ilInit();
	ilGenImages(1, ima);
	ilBindImage(ima[0]);
	ilLoadImage((ILstring)"terreno.jpg");
	ilConvertImage(IL_LUMINANCE, IL_UNSIGNED_BYTE);

	imageWidth = ilGetInteger(IL_IMAGE_HEIGHT);
	imageData = ilGetData();
	printf("%d\n", imageWidth);

	prepareTerrain();

	loadTexture();

	glEnable(GL_TEXTURE_2D);

	glEnable(GL_DEPTH_TEST);
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);

}


int main(int argc, char **argv) {

// init GLUT and window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(100,100);
	glutInitWindowSize(320,320);
	glutCreateWindow("CG@DI-UM");


// callback registration
	glutDisplayFunc(renderScene);
	glutIdleFunc(renderScene);
	glutReshapeFunc(changeSize);

	glutKeyboardFunc(processKeys);
	glutMouseFunc(processMouseButtons);
	glutMotionFunc(processMouseMotion);

#ifndef __APPLE__
	glewInit();
#endif

	init();

// GLUT's main cicle
	glutMainLoop();

	return 0;
}

