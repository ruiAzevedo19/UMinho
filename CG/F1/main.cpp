#include <stdio.h>

#ifdef __APPLE__
#define GL_SILENCE_DEPRECATION 
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

void drawAxis(){
    glBegin(GL_LINES);
    glColor3f(1.0f, 0.0f, 0.0f);

    glVertex3f(-100.0f, 0.0f, 0.0f);
    glVertex3f( 100.0f, 0.0f, 0.0f);

    glVertex3f(0.0f, -100.0f, 0.0f);
    glVertex3f(0.0f, 100.0f, 0.0f);

    glVertex3f(0.0f, 0.0f, -100.0f);
    glVertex3f(0.0f, 0.0f,  100.0f);
    glEnd();
}

/* --- Primitives -------------------------------------------------------- */
// draw sphere in XoY
void sphere(float radius, int slices){
    float dx = (2 * M_PI) / (float)slices;
    float alpha;

    glBegin(GL_TRIANGLES);
    for(int i = 0; i < slices; i++){
        alpha = dx * i;
        glVertex3f(0,0,0);
        glVertex3f(radius * cos(alpha), radius * sin(alpha), 0);
        glVertex3f(radius * cos(alpha + dx), radius * sin(alpha + dx), 0);
    }
    glEnd();
}

// draw square in XoY
void square(float dim){
    float mid = dim / 2.0;

    glBegin(GL_TRIANGLES);

    // right triangle
    glVertex3f(mid,mid,0);
    glVertex3f(-mid,mid,0);
    glVertex3f(mid,-mid,0);
    // left triangle 
    glVertex3f(-mid,-mid,0);
    glVertex3f(mid,-mid,0);
    glVertex3f(-mid,mid,0);

    glEnd();
}

void house(float dimX, float dimY, float roof_height){
    float midx = dimX / 2.0;
    float midy = dimY / 2.0;

    glBegin(GL_TRIANGLES);
    // right triangle 
    glVertex3f(midx,midy,0);
    glVertex3f(-midx,midy,0);
    glVertex3f(midx,-midy,0);
    // left triangle 
    glVertex3f(-midx,-midy,0);
    glVertex3f(midx,-midy,0);
    glVertex3f(-midx,midy,0);
    //rooftop 
    glVertex3f(-midx,midy,0);
    glVertex3f(midx,midy,0);
    glVertex3f(0,midy + roof_height,0);

    glEnd();
}

/* --- Transformations --------------------------------------------------- */

void ex1(){
    glScaled(2,2,2);
    glTranslated(1,0,0);
    glScaled(0.5,0.5,0.5);
}

void ex2(){
    glRotated(45,0.0,0.0,1.0);
    glTranslated(2.0,0.0,0.0);
    glRotated(-45,0.0,0.0,1.0);
}

void ex3a(float alpha){
    printf("cos(%.2f) = %.2f\nsin(%.2f) = %.2f\n", alpha,sin(alpha),alpha,cos(alpha));
    glTranslated(cos(alpha),sin(alpha),0);
    glRotated(alpha,0,0,1);
}

void ex3b(float alpha){
    glRotated(alpha,0,0,1);
    glTranslated(1,0,0);
}

/* ----------------------------------------------------------------------- */ 
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

void renderScene(void) {
	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	// set the camera
	glLoadIdentity();
	gluLookAt(0.0f, 0.0f, 10.0f,
		      0.0f, 0.0f, 0.0f,
		      0.0f, 1.0f, 0.0f);

    // draw axis 
    drawAxis();
    // only for reference
    glPolygonMode(GL_FRONT , GL_POINT);
    sphere(1,50);
    sphere(2,50);
    sphere(3,50);

    // Transformations 
    // ex1();
    // ex2();
     ex3a(45);
    // ex3b(45);

    // primitives 
    glColor3f(1.0f, 0.5f, 0.0f);
    glPolygonMode(GL_FRONT , GL_LINE);
    //sphere(1,50);
    //square(1);
    house(0.25,0.25,0.25);

    // End of frame
	glutSwapBuffers();
}

int main(int argc, char **argv) {
    // init GLUT and the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(100,100);
	glutInitWindowSize(800,800);
	glutCreateWindow("CG@DI-UM");
    // Required callback registry 
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
    //  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	//glEnable(GL_CULL_FACE);
    // enter GLUT's main cycle
	glutMainLoop();
	
	return 1;
}
