#include <stdlib.h>
#include <stdio.h>
#include <vector>
#include <string>
#include <math.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

#include <IL/il.h>



#define _PI_ 3.14159

float alfa = 0.0f, beta = 0.70f, raio = 5.0f;
float camX, camY, camZ;

GLuint vertexCount, vertices, normals, texCoord, indices, indCount, 
	texIDCylinder, texIDFloor;

int timebase = 0, frame = 0;


void converte() {

	camX = raio * cos(beta) * sin(alfa);
	camY = raio * sin(beta);
	camZ = raio * cos(beta) * cos(alfa);
}


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
	gluPerspective(45,ratio,0.1,100);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}



void prepareCylinder(float altura, float raio, int lados) {

	float *v,*n,*t;

	v = (float *)malloc(sizeof(float) * 4 * 3 * 3 * lados);
	n = (float *)malloc(sizeof(float) * 4 * 3 * 3 * lados);
	t = (float *)malloc(sizeof(float) * 4 * 3 * 2 * lados);



	int vertex = 0;
	float delta = 2.0f * _PI_ / lados;

	for (int i = 0; i < lados; ++i) {
		// topo
		// ponto central
		n[vertex*3 + 0] = 0.0f; 
		n[vertex*3 + 1] = 1.0f;
		n[vertex*3 + 2] = 0.0f;
		v[vertex*3 + 0] = 0.0f; 
		v[vertex*3 + 1] = altura/2.0f;
		v[vertex*3 + 2] = 0.0f;
        t[vertex*2 + 0]=0.4375;
        t[vertex*2 + 1]=0.1875;

		vertex++;
		n[vertex*3 + 0] = 0;
		n[vertex*3 + 1] = 1.0f;
		n[vertex*3 + 2] = 0;
		v[vertex*3 + 0] = raio * sin( i * delta);
		v[vertex*3 + 1] = altura/2.0f;
		v[vertex*3 + 2] = raio * cos( i * delta);
        t[vertex*2 + 0]=0.4375+0.1875*sin(i*delta);
        t[vertex*2 + 1]=0.1875+0.1875*cos(i*delta);;


		vertex++;
		n[vertex*3 + 0] = 0;
		n[vertex*3 + 1] = 1.0f;
		n[vertex*3 + 2] = 0;
		v[vertex*3 + 0] = raio * sin( (i+1) * delta);
		v[vertex*3 + 1] = altura/2.0f;
		v[vertex*3 + 2] = raio * cos( (i+1) * delta);
        t[vertex*2 + 0]=0.4375+0.1875*sin((i+1)*delta);
        t[vertex*2 + 1]=0.1875+0.1875*cos((i+1)*delta);;
        

		// corpo

        vertex++;
        n[vertex*3 + 0] = sin( (i+1) * delta);
        n[vertex*3 + 1] = 0.0f;
        n[vertex*3 + 2] = cos( (i+1) * delta);
        v[vertex*3 + 0] = raio * sin( (i+1) * delta);
        v[vertex*3 + 1] = altura/2.0f;
        v[vertex*3 + 2] = raio * cos( (i+1) * delta);
        t[vertex*2 + 0] = (i+1)/(float)lados; 
        t[vertex*2 + 1] = 1;

        vertex++;
        n[vertex*3 + 0] = sin( i * delta);
        n[vertex*3 + 1] = 0.0f;
        n[vertex*3 + 2] = cos( i * delta);
        v[vertex*3 + 0] = raio * sin( i * delta);
        v[vertex*3 + 1] = altura/2.0f;
        v[vertex*3 + 2] = raio * cos( i * delta);
        t[vertex*2 + 0] = i/(float)lados; 
        t[vertex*2 + 1] = 1;

        vertex++;
        n[vertex*3 + 0] = sin( i * delta);
        n[vertex*3 + 1] = 0.0f;
        n[vertex*3 + 2] = cos( i * delta);
        v[vertex*3 + 0] = raio * sin( i * delta);
        v[vertex*3 + 1] = -altura/2.0f;
        v[vertex*3 + 2] = raio * cos( i * delta);
        t[vertex*2 + 0] = i/(float)lados; 
        t[vertex*2 + 1] = 0.375;

		vertex++;
		n[vertex*3 + 0] = sin( (i+1) * delta);
		n[vertex*3 + 1] = 0.0f;
		n[vertex*3 + 2] = cos( (i+1) * delta);
		v[vertex*3 + 0] = raio * sin( (i+1) * delta);
		v[vertex*3 + 1] = -altura/2.0f;
		v[vertex*3 + 2] = raio * cos( (i+1) * delta);
        t[vertex*2 + 0]=(float)(i+1)/(float)lados;
        t[vertex*2 + 1]=0.375;

		vertex++;
		n[vertex*3 + 0] = sin( (i+1) * delta);
		n[vertex*3 + 1] = 0.0f;
		n[vertex*3 + 2] = cos( (i+1) * delta);
		v[vertex*3 + 0] = raio * sin( (i+1) * delta);
		v[vertex*3 + 1] = altura/2.0f;
		v[vertex*3 + 2] = raio * cos( (i+1) * delta);
        t[vertex*2 + 0]=(float)(i+1)/(float)lados;
        t[vertex*2 + 1]=1;

		vertex++;
		n[vertex*3 + 0] = sin( i * delta);
		n[vertex*3 + 1] = 0.0f;
		n[vertex*3 + 2] = cos( i * delta);
		v[vertex*3 + 0] = raio * sin( i * delta);
		v[vertex*3 + 1] = -altura/2.0f;
		v[vertex*3 + 2] = raio * cos( i * delta);
        t[vertex*2 + 0]=(float)i/(float)lados;
        t[vertex*2 + 1]=0.375;

		//base
		vertex++;
		n[vertex*3 + 0] = 0.0f; 
		n[vertex*3 + 1] = -1.0f;
		n[vertex*3 + 2] = 0.0f;
		v[vertex*3 + 0] = 0.0f; 
		v[vertex*3 + 1] = -altura/2.0f;
		v[vertex*3 + 2] = 0.0f;
        t[vertex*2 + 0]=0.8125;
        t[vertex*2 + 1]=0.1875;


		vertex++;
		n[vertex*3 + 0] = 0.0f; 
		n[vertex*3 + 1] = -1.0f;
		n[vertex*3 + 2] = 0.0f;
		v[vertex*3 + 0] = raio * sin( (i+1) * delta);
		v[vertex*3 + 1] = -altura/2.0f;
		v[vertex*3 + 2] = raio * cos( (i+1) * delta);;
        t[vertex*2 + 0]=0.8125+0.1875*sin((i+1)*delta);
        t[vertex*2 + 1]=0.1875+0.1875*cos((i+1)*delta);;


		vertex++;
		n[vertex*3 + 0] = 0.0f; 
		n[vertex*3 + 1] = -1.0f;
		n[vertex*3 + 2] = 0.0f;
		v[vertex*3 + 0] = raio * sin( i * delta);
		v[vertex*3 + 1] = -altura/2.0f;
		v[vertex*3 + 2] = raio * cos( i * delta);
        t[vertex*2 + 0]=0.8125+0.1875*sin(i*delta);
        t[vertex*2 + 1]=0.1875+0.1875*cos(i*delta);;


		vertex++;
	}

	vertexCount = vertex;

	glGenBuffers(1, &vertices);
	glBindBuffer(GL_ARRAY_BUFFER,vertices);
	glBufferData(GL_ARRAY_BUFFER, sizeof(float) * vertexCount * 3, v,GL_STATIC_DRAW);
	glGenBuffers(1, &normals);
	glBindBuffer(GL_ARRAY_BUFFER,normals);
	glBufferData(GL_ARRAY_BUFFER, sizeof(float) * vertexCount * 3, n,GL_STATIC_DRAW);
    glGenBuffers(1, &texCoord);
    glBindBuffer(GL_ARRAY_BUFFER, texCoord);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * vertexCount * 2, t, GL_STATIC_DRAW);
    

	free(v); free(n);

}


void drawFloor(float xmin, float xmax, float zmin, float zmax) {

	// bind the slot
	glBindTexture(GL_TEXTURE_2D, texIDFloor);

	glBegin(GL_QUADS);
		glNormal3f(0, 1, 0);
		glTexCoord2f(1, 0);
		glVertex3f(xmax, 0, zmin);
		glTexCoord2f(0, 0);
		glVertex3f(xmin, 0, zmin);
		glTexCoord2f(0, 1);
		glVertex3f(xmin, 0, zmax);
		glTexCoord2f(1, 1);
		glVertex3f(xmax, 0, zmax);
	glEnd();

	glBindTexture(GL_TEXTURE_2D, 0);
}


void drawCylinder() {
		
	glBindBuffer(GL_ARRAY_BUFFER,vertices);
	glVertexPointer(3,GL_FLOAT,0,0);

	glBindBuffer(GL_ARRAY_BUFFER,normals);
	glNormalPointer(GL_FLOAT,0,0);

	// Definir a semântica na altura de desenho
	glBindBuffer(GL_ARRAY_BUFFER, texCoord);
	glTexCoordPointer(2, GL_FLOAT, 0, 0);	
	glBindTexture(GL_TEXTURE_2D, texIDCylinder);

	glDrawArrays(GL_TRIANGLES, 0, vertexCount);
	glBindTexture(GL_TEXTURE_2D, 0);
}


int lados=16;
int mode = 1;

void renderScene(void) {

	float pos[4] = {1.0, 1.0, 1.0, 0.0};
	float fps;
	int timet;
	char s[64];

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glLoadIdentity();

	gluLookAt(camX,camY,camZ, 
		      0.0,0.0,0.0,
			  0.0f,1.0f,0.0f);

	glLightfv(GL_LIGHT0, GL_POSITION, pos);

	float white[4] = { 1,1,1,1 };
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, white);

	drawFloor(-5, 5, -5, 5);
	glTranslatef(0, 1, -1);
	drawCylinder();
	glTranslatef(0, 0, 2);
	drawCylinder();

	frame++;
	timet=glutGet(GLUT_ELAPSED_TIME); 
	if (timet - timebase > 1000) { 
		fps = frame*1000.0/(timet-timebase); 
		timebase = timet; 
		frame = 0; 
		sprintf(s, "FPS: %f6.2", fps);
		glutSetWindowTitle(s);
	} 

// End of frame
	glutSwapBuffers();
}

// escrever função de processamento do teclado

void processKeys(int key, int xx, int yy) 
{
	switch(key) {
	
		case GLUT_KEY_RIGHT: 
						alfa -=0.1; break;

		case GLUT_KEY_LEFT: 
						alfa += 0.1; break;

		case GLUT_KEY_UP : 
						beta += 0.1f;
						if (beta > 1.5f)
							beta = 1.5f;
						break;

		case GLUT_KEY_DOWN: 
						beta -= 0.1f; 
						if (beta < -1.5f)
							beta = -1.5f;
						break;

		case GLUT_KEY_PAGE_DOWN : raio -= 0.1f; 
			if (raio < 0.1f)
				raio = 0.1f;
			break;

		case GLUT_KEY_PAGE_UP: raio += 0.1f; break;

		case GLUT_KEY_F1: mode = !mode;
			printf("mode: %d\n", mode);
			break;

	}
	converte();
	glutPostRedisplay();

}



void initGL() {

// alguns settings para OpenGL
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	converte();
	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_NORMAL_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);

	glClearColor(0, 0, 0, 0);

	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);

	glEnable(GL_TEXTURE_2D);
	prepareCylinder(2,1,lados);
}


int loadTexture(std::string s) {

	unsigned int t,tw,th;
	unsigned char *texData;
	unsigned int texID;

	ilInit();
	ilEnable(IL_ORIGIN_SET);
	ilOriginFunc(IL_ORIGIN_LOWER_LEFT);
	ilGenImages(1,&t);
	ilBindImage(t);
	ilLoadImage((ILstring)s.c_str());
	tw = ilGetInteger(IL_IMAGE_WIDTH);
	th = ilGetInteger(IL_IMAGE_HEIGHT);
	ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
	texData = ilGetData();

	glGenTextures(1,&texID);
	
	glBindTexture(GL_TEXTURE_2D,texID);
	glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_WRAP_S,		GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_WRAP_T,		GL_REPEAT);

	glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_MAG_FILTER,   	GL_LINEAR_MIPMAP_LINEAR);
	glTexParameteri(GL_TEXTURE_2D,	GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
	glGenerateMipmap(GL_TEXTURE_2D);

	glBindTexture(GL_TEXTURE_2D, 0);

	return texID;

}


int main(int argc, char **argv) {

// inicializa��o
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(100,100);
	glutInitWindowSize(320,320);
	glutCreateWindow("CG@DI-UM");
		
// registo de fun��es 
	glutDisplayFunc(renderScene);
	glutIdleFunc(renderScene);
	glutReshapeFunc(changeSize);

// registo da fun��es do teclado e rato
	glutSpecialFunc(processKeys);

#ifndef __APPLE__
// init GLEW
	glewInit();
#endif

	initGL();
	texIDCylinder = loadTexture("Oil_Drum001h.jpg");
	texIDFloor = loadTexture("Concrete.jpg");
// entrar no ciclo do GLUT 
	glutMainLoop();
	
	return 1;
}

