#include <math.h>

#include <iostream>
#include "../headers/parser.h"

#ifdef __APPLE__
#include <GLUT/glut.h>
#include <GL/glew.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

#include <IL/il.h>

#define _USE_MATH_DEFINES
#ifndef M_PI
#define M_PI 3.14159265358979323846
#endif

/* Camara */
float camX = 00, camY = 30, camZ = 40;
int startX, startY, tracking = 0;

std::vector<Engine_Primitive*> allPrimitives;
std::vector<float> vertices;
std::vector<float> normals;
std::vector<float> textures;
GLuint* buffers, *textureIDs;
vector<int> show;
int time1, time1base, frame = 0;
float fps = 0;
char title[50];

// Path to database
#define PATH_TO_DB "../../db/"

float alpha = M_PI / 4, beta = 0.62;
float r = sqrt(75);
float dx = 0, dy = 0, dz = 0;

// Definição dos valores para o menu
#define RED 0
#define ORANGE 1
#define YELLOW 2
#define GREEN 3
#define CYAN 4
#define BLUE 5
#define PURPLE 6
#define PINK 7
#define WHITE 8

// A cor por defeito é branca (red = 1, green = 1, blue = 1)
float red = 1;
float green = 1;
float blue = 1;

// Por defeito o modo de desenho é wired
GLenum mode = GL_LINE;

/* Função que desenha todos os vértices */
void drawVertices(void){
    int count = 0;
	//glColor3f(red, green, blue);
	for (int i = 0; i < allPrimitives.size(); i++) {
        /* Apply the transformations */
        vector<Transformation*> t = allPrimitives.at(i)->getTransformations();
        glPushMatrix();
        for(int k = 0; k < t.size(); k++){
            t.at(k)->execute();
        }

		/* After transformations are applied, draw VBO's */
		glBindBuffer(GL_ARRAY_BUFFER, buffers[i * 3 + 0]);
		glVertexPointer(3, GL_FLOAT, 0, 0);

		/* Apply normals if there's any */
		if (allPrimitives.at(i)->getNormals().size() > 0) {
			glBindBuffer(GL_ARRAY_BUFFER, buffers[i * 3 + 1]);
			glNormalPointer(GL_FLOAT, 0, 0);
		}

		/* Apply textures if there are any */
		if (allPrimitives.at(i)->getTextures().size() > 0) {
			glBindBuffer(GL_ARRAY_BUFFER, buffers[i* 3 + 2]);
			glTexCoordPointer(2, GL_FLOAT, 0, 0); // 2 because it's the number of coordinates in textures
		}

		/* Bind the texture if there's one to bind */
		if (allPrimitives.at(i)->getTexture().compare("") != 0) {
			glBindTexture(GL_TEXTURE_2D, textureIDs[i]);
		}

        /* Draw the arrays */
        glDrawArrays(GL_TRIANGLES,0, allPrimitives.at(i)->getVertices().size()*3);

		/* Undo the texture binding */
		glBindTexture(GL_TEXTURE_2D, 0);

        glPopMatrix();
        vertices.clear();
	}
}

void changeSize(int w, int h) {
	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width).
	if(h == 0)
		h = 1;

	// compute window's aspect ratio
	float ratio = w * 1.0 / h;

	// Set the projection matrix as current
	glMatrixMode(GL_PROJECTION);
	// Load Identity Matrix
	glLoadIdentity();

	// Set the viewport to be the entire window
	glViewport(0, 0, w, h);

	// Set perspective
	gluPerspective(45.0f ,ratio, 1.0f ,1000.0f);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}
void drawAxis(){
	glBegin(GL_LINES);
	// X axis in red
	glColor3f(1.0f, 0.0f, 0.0f);
	glVertex3f(-100.0f, 0.0f, 0.0f);
	glVertex3f( 100.0f, 0.0f, 0.0f);
	// Y Axis in Green
	glColor3f(0.0f, 1.0f, 0.0f);
	glVertex3f(0.0f, -100.0f, 0.0f);
	glVertex3f(0.0f, 100.0f, 0.0f);
	// Z Axis in Blue
	glColor3f(0.0f, 0.0f, 1.0f);
	glVertex3f(0.0f, 0.0f, -100.0f);
	glVertex3f(0.0f, 0.0f,  100.0f);
	glEnd();
}

void renderScene(void) {
    time1 = glutGet(GLUT_ELAPSED_TIME);

    frame++;
    if(time1 - time1base > 1000){
        fps = frame*1000.0/(time1-time1base);
        time1base = time1;
        frame = 0;

        sprintf(title, "%.2f", fps);

        glutSetWindowTitle(title);
        printf("%s\n",title);
    }

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	gluLookAt(camX, camY, camZ,
		0.0, 0.0, 0.0,
		0.0f, 1.0f, 0.0f);

	//drawAxis();
	// Clear colors
	glColor3f(1.0f, 1.0f, 1.0f);

    //glPolygonMode(GL_FRONT, mode);
    drawVertices();

	// End of frame
	glutSwapBuffers();
}


// write keyboard processing function

void processKeys(unsigned char key, int xx, int yy) {

}



void processMouseButtons(int button, int state, int xx, int yy) {

	if (state == GLUT_DOWN) {
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
	camY = rAux * sin(betaAux * 3.14 / 180.0);
}

void idleFun(){
	glutPostRedisplay();
}


int loadTexture(std::string s) {
	unsigned int t, tw, th;
	unsigned char* texData;
	unsigned int texID;
	string path = PATH_TO_DB + s;

	ilInit();
	ilEnable(IL_ORIGIN_SET);
	ilOriginFunc(IL_ORIGIN_LOWER_LEFT);
	ilGenImages(1, &t);
	ilBindImage(t);
	printf("Loading texture from %s\n", path.c_str());
	ilLoadImage((ILstring)path.c_str());
	tw = ilGetInteger(IL_IMAGE_WIDTH);
	th = ilGetInteger(IL_IMAGE_HEIGHT);
	printf("Got a texture with size %d by %d\n", tw, th);
	ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
	texData = ilGetData();

	glGenTextures(1, &texID);

	glBindTexture(GL_TEXTURE_2D, texID);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
	glGenerateMipmap(GL_TEXTURE_2D);

	glBindTexture(GL_TEXTURE_2D, 0);

	return texID;
}

/* Gets the vbo's and textures ready to be drawn later */
void initVBOsNtexturesNnormals(){
	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_NORMAL_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);

	glPolygonMode(GL_FRONT, GL_FILL);
	glEnable(GL_NORMALIZE);
	glEnable(GL_DEPTH_TEST);
	//glEnable(GL_LIGHT0);
	glEnable(GL_TEXTURE_2D);
	glShadeModel(GL_SMOOTH);
    glGenBuffers(allPrimitives.size()*3,buffers);

    for(size_t i = 0; i < allPrimitives.size(); i++){
        std::vector<Vertice*> pVertices = allPrimitives.at(i)->getVertices();
		//printf("Processing primitive %d\n", i);
        for(int j = 0; j < pVertices.size(); j++){
            vertices.push_back(pVertices.at(j)->getX());
            vertices.push_back(pVertices.at(j)->getY());
            vertices.push_back(pVertices.at(j)->getZ());
        }
        float* arr = &vertices[0];
		std::vector<Vertice*> pNormals = allPrimitives.at(i)->getNormals();
		for (int j = 0; j < pNormals.size(); j++) {
			normals.push_back(pNormals.at(j)->getX());
			normals.push_back(pNormals.at(j)->getY());
			normals.push_back(pNormals.at(j)->getZ());
		}
		std::vector<Vertice*> pTextures = allPrimitives.at(i)->getTextures();
		for (int j = 0; j < pTextures.size(); j++) {
			textures.push_back(pTextures.at(j)->getX());
			textures.push_back(pTextures.at(j)->getY());
		}
		float* norm; 
		if(pNormals.size() > 0) norm = &normals[0];
		float* text;
		if(pTextures.size() > 0) text = &textures[0];
        glBindBuffer(GL_ARRAY_BUFFER, buffers[i * 3 + 0]);
        glBufferData(GL_ARRAY_BUFFER, pVertices.size()*3*sizeof(float), &arr[0], GL_STATIC_DRAW);
		if (pNormals.size() > 0) {
			glBindBuffer(GL_ARRAY_BUFFER, buffers[i * 3 + 1]);
			glBufferData(GL_ARRAY_BUFFER, sizeof(float) * 3 * pVertices.size(), &norm[0], GL_STATIC_DRAW);
		}
		if (pTextures.size() > 0) {
			glBindBuffer(GL_ARRAY_BUFFER, buffers[i * 3 + 2]);
			glBufferData(GL_ARRAY_BUFFER, sizeof(float) * 2 * pVertices.size(), &text[0], GL_STATIC_DRAW);
		}

		/* Associate the loaded texture ID to the primitive */
		string s = allPrimitives.at(i)->getTexture();
		if(s.compare("") != 0) textureIDs[i] = loadTexture(s);
        vertices.clear();
		normals.clear();
		textures.clear();
    }
}

/**
 * Função que trata das opções selecionadas no menu.
 */
void processMenuEvents(int option)
{
	if (option == GL_FILL) {

		// Preenche a primitiva
		mode = GL_FILL;
	}
	else if (option == GL_LINE) {

		// Desenha a primitiva em modo wired
		mode = GL_LINE;
	}
	else if (option == GL_POINT) {

		// Desenha apenas os vértices da primitiva
		mode = GL_POINT;
	}
	else if (option == RED) {
		red = 1;
		blue = 0;
		green = 0;
	}
	else if (option == ORANGE) {
		red = 1;
		blue = 0;
		green = 0.5;
	}
	else if (option == YELLOW) {
		red = 1;
		blue = 0;
		green = 1;
	}
	else if (option == GREEN) {
		red = 0;
		blue = 0;
		green = 1;
	}
	else if (option == CYAN) {
		red = 0;
		blue = 1;
		green = 1;
	}
	else if (option == BLUE) {
		red = 0;
		blue = 1;
		green = 0;
	}
	else if (option == PURPLE) {
		red = 0.5;
		blue = 1;
		green = 0;
	}
	else if (option == PINK) {
		red = 1;
		blue = 1;
		green = 0;
	}
	else if (option == WHITE) {
		red = 1;
		blue = 1;
		green = 1;
	}

	glutPostRedisplay();
}

int main(int argc, char **argv) {
    allPrimitives = readXML("solar_system.xml", PATH_TO_DB);
    buffers = (GLuint*) malloc(sizeof(GLuint) * allPrimitives.size() * 3);
	textureIDs = (GLuint*)malloc(sizeof(GLuint)* allPrimitives.size());
	int menu = 0;

// init GLUT and the window
	glutInit(&argc, argv);
	glEnableClientState(GL_VERTEX_ARRAY);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(100,100);
	glutInitWindowSize(800,800);
	glutCreateWindow("CG@DI-UM");

// Required callback registry
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	glutIdleFunc(idleFun);
// Callback registration for keyboard processing
	glutKeyboardFunc(processKeys);
	glutMouseFunc(processMouseButtons);
	glutMotionFunc(processMouseMotion);
	/*menu = glutCreateMenu(processMenuEvents);

	// Categorias do menu
	glutAddMenuEntry("GL_FILL", GL_FILL);
	glutAddMenuEntry("GL_LINE", GL_LINE);
	glutAddMenuEntry("GL_POINT", GL_POINT);
	glutAddMenuEntry("Red", RED);
	glutAddMenuEntry("Orange", ORANGE);
	glutAddMenuEntry("Yellow", YELLOW);
	glutAddMenuEntry("Green", GREEN);
	glutAddMenuEntry("Cyan", CYAN);
	glutAddMenuEntry("Blue", BLUE);
	glutAddMenuEntry("Purple", PURPLE);
	glutAddMenuEntry("Pink", PINK);
	glutAddMenuEntry("White", WHITE);

	// O menu é acionado sempre que se pressiona o botão direito do rato
	glutAttachMenu(GLUT_RIGHT_BUTTON);*/

    glewInit();

//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	initVBOsNtexturesNnormals();

// enter GLUT's main cycle
	glutMainLoop();

	return 1;
}
