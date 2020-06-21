#ifndef _LIGHT_H_
#define _LIGHT_H_

#ifdef __APPLE__
#include <GLUT/glut.h>
#include <GL/glew.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

class Light{
    public:
        virtual void execute(void) = 0;
};

#endif

