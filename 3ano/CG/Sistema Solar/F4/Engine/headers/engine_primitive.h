#ifndef _ENGINE_PRIMITIVE_H_
#define _ENGINE_PRIMITIVE_H_

#include "../../Generator/headers/vertice.h"
#include "transformation.h"
#include <vector>

class Engine_Primitive{
    private:
        vector<Vertice*> vertices, normals, textures;
        vector<Transformation*> transformations;
        float diffR, diffG, diffB, emisR, emisG, emisB, ambiR, ambiG, ambiB, specR, specG, specB;
        string texture_name;
        int texID;

    public:
        Engine_Primitive(void);
        Engine_Primitive(vector<Vertice*> vertices, vector<Transformation*> transformations);
        vector<Vertice*> getVertices();
        vector<Vertice*> getNormals();
        vector<Vertice*> getTextures();
        vector<Transformation*> getTransformations();
        void setTransformations(vector<Transformation*> t);
        void addVertice(Vertice *v);
        void addNormal(Vertice *v);
        void addTexture(Vertice *v);
        void addTransformation(Transformation *t);
        void setDiffuseComponent(float diffR, float diffG, float diffB);
        void setEmisiveComponent(float emisR, float emisG, float emisB);
        void setAmbientComponent(float ambiR, float ambiG, float ambiB);
        void setSpecularComponent(float specR, float specG, float specB);
        void setTexture(string texture_name);
        string getTexture();
        void setTexID(int texID);
        int getTexID();
};

#endif
