#ifdef __APPLE__
#include <GLUT/glut.h>
#include <GL/glew.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

#include <iostream>
#include "../headers/parser.h"

std::vector<Engine_Primitive*> allPrimitives;
std::vector<float> vertices;
GLuint* buffers;
vector<int> show;
int time1, time1base, frame = 0;
float fps = 0;
char title[50];

// Path to database
#define PATH_TO_DB "../../db/"

float alpha = M_PI / 4, beta = 0.62;
float r = sqrt(75);
float dx = 0, dy = 0, dz = 0;
float lx, ly, lz;

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
void drawVerticesOp(void){
    int count = 0;
	glColor3f(red, green, blue);
    glGenBuffers(allPrimitives.size(), buffers);
	for (int i = 0; i < allPrimitives.size(); i++) {
	    if(show.at(i)){
            /* The vertices of the corresponding primitive */
            std::vector<Vertice*> pVertices = allPrimitives.at(i)->getVertices();
            for(int j = 0; j < pVertices.size(); j++){
                vertices.push_back(pVertices.at(j)->getX());
                vertices.push_back(pVertices.at(j)->getY());
                vertices.push_back(pVertices.at(j)->getZ());
            }
            float* arr = &vertices[0];
            glBindBuffer(GL_ARRAY_BUFFER, buffers[i]);
            glBufferData(GL_ARRAY_BUFFER, pVertices.size()*3*sizeof(float), &arr[0], GL_STATIC_DRAW);
            glVertexPointer(3, GL_FLOAT, 0, 0);

            /* Apply the transformations */
            vector<Transformation*> t = allPrimitives.at(i)->getTransformations();
            glPushMatrix();
            for(int k = 0; k < t.size(); k++){
                t.at(k)->execute();
            }
            glDrawArrays(GL_TRIANGLES, 0, vertices.size());
            glPopMatrix();
            vertices.clear();
        }
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
        //printf("%s\n",title);
    }

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	float px = 30 * sin(alpha) * cos(beta);
	float py = 30 * sin(beta);
	float pz = 30 * cos(alpha) * cos(beta);
	gluLookAt(px,py,pz,
		  px + lx,py + ly,pz + lz,
		  0.0f,1.0f,0.0f);

	drawAxis();

    glPolygonMode(GL_FRONT, mode);
    for(int i = 0; i < allPrimitives.size(); i++){
	    glBindBuffer(GL_ARRAY_BUFFER, buffers[i]);
    }

    glEnableClientState(GL_VERTEX_ARRAY);
    drawVerticesOp();
    glDisableClientState(GL_VERTEX_ARRAY);

	// End of frame
	glutSwapBuffers();
}


void processKeys(unsigned char c, int xx, int yy) {
	switch(c){
		case 'a' : alpha -= 0.1;
			   break;
		case 'd' : alpha += 0.1;
			   break;
		case 'w' : if (beta <= M_PI/2)
				   beta += 0.1;
			   break;
		case 's' : if (beta >= - M_PI/2)
				   beta -= 0.1;
			   break;
        case '1' : show.at(0) = show.at(0) ? 0 : 1;
	               break;
        case '2' : show.at(1) = show.at(1) ? 0 : 1;
                   break;
        case '3' : show.at(2) = show.at(2) ? 0 : 1;
                   break;
        case '4' : show.at(3) = show.at(3) ? 0 : 1;
                   break;
	}
}

void processSpecialKeys(int key, int xx, int yy) {
// put code to process special keys in here

}

void idleFun(){
	glutPostRedisplay();
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
    allPrimitives = readXML("mix.xml", PATH_TO_DB);
    buffers = (GLuint*) malloc(sizeof(GLuint) * allPrimitives.size());
    for(int i = 0; i < allPrimitives.size(); i++){
        /* Allows to hide some primitives */
        show.push_back(1);
    }
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
	glutSpecialFunc(processSpecialKeys);
	menu = glutCreateMenu(processMenuEvents);

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
	glutAttachMenu(GLUT_RIGHT_BUTTON);

    glewInit();

//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

// enter GLUT's main cycle
	glutMainLoop();

	return 1;
}
