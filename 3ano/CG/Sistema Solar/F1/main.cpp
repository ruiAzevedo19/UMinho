#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

float alpha = M_PI / 4, beta = 0.62, radius = sqrt(75), dx = 1, dy = 1, dz = 1, k = 0;

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


void drawCylinder(float radius, float height, int slices) {
    glPolygonMode(GL_FRONT, GL_LINE);
    glBegin(GL_TRIANGLES);
    // put code to draw cylinder in here
    for(int i = 0; i < slices; i++){
        float local_alpha = i * ((2*M_PI)/slices);
        float local_beta = (i+1) * ((2*M_PI)/slices);

        // Face de cima
        glVertex3f(0.0f, height, 0.0f);
        glVertex3f(radius * sin(local_alpha), height, radius * cos(local_alpha));
        glVertex3f(radius * sin(local_beta), height, radius * cos(local_beta));

        // Face de baixo
        glVertex3f(0.0f, 0.0f, 0.0f);
        glVertex3f(radius * sin(local_beta), 0.0f, radius * cos(local_beta));
        glVertex3f(radius * sin(local_alpha), 0.0f, radius * cos(local_alpha));

        // Faces laterais
        glVertex3f(radius * sin(local_alpha), height, radius * cos(local_alpha));
        glVertex3f(radius * sin(local_alpha), 0.0f, radius * cos(local_alpha));
        glVertex3f(radius * sin(local_beta), height, radius * cos(local_beta));

        glVertex3f(radius * sin(local_beta), height, radius * cos(local_beta));
        glVertex3f(radius * sin(local_alpha), 0.0f, radius * cos(local_alpha));
        glVertex3f(radius * sin(local_beta), 0.0f, radius * cos(local_beta));
    }
    glEnd();
}


void renderScene(void) {
	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	float norm = sqrt(dx*dx + dy*dy + dz*dz);
	float px = radius*sin(alpha)*cos(beta);
	float py = radius*sin(beta);
	float pz = radius*cos(alpha)*cos(beta);
	gluLookAt(px, py, pz,
		      px+dx,py+dy,pz+dz,
			  0.0f,1.0f,0.0f);

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

    glColor3f(1.0f, 1.0f, 1.0f);
	drawCylinder(1,2,10);

	// End of frame
	glutSwapBuffers();
}

void processKeys(unsigned char c, int xx, int yy) {
// put code to process regular keys in here
    switch(c){
        case 'a': alpha -= 0.1;
                  break;
        case 'd': alpha += 0.1;
                  break;
        case 'w': if(beta < 1.5){
                      beta += 0.1;
                  }
                  break;
        case 's': if(beta > - 1.5){
                      beta -= 0.1;
                  }
                  break;
        case '+': if (radius > 0){
                      radius -= 0.1;
                  }
                  break;
        case '-': radius += 0.1;
                  break;
        case 'n': dz =- 0.1;
                  break;
        case 'm': dz += 0.1;
                  break;
    }
}


void processSpecialKeys(int key_code, int xx, int yy) {

// put code to process special keys in here
    if(key_code == GLUT_KEY_LEFT) dx =- 0.1;
    else if(key_code == GLUT_KEY_RIGHT) dx +- 0.1;
    else if(key_code == GLUT_KEY_UP) k += 10;
    else if(key_code == GLUT_KEY_DOWN) k -= 10;
}

void idleFunc(){
    glutPostRedisplay();
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
	glutIdleFunc(idleFunc);

// Callback registration for keyboard processing
	glutKeyboardFunc(processKeys);
	glutSpecialFunc(processSpecialKeys);

//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

// enter GLUT's main cycle
	glutMainLoop();

	return 1;
}
