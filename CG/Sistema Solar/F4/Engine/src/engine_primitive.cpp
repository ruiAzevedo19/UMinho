#include "../headers/engine_primitive.h"

Engine_Primitive::Engine_Primitive(){
    ;
}

Engine_Primitive::Engine_Primitive(vector<Vertice*> vertices, vector<Transformation*> transformations){
    this->vertices = vertices;
    this->normals = normals;
    this->transformations = transformations;
}

vector<Vertice*> Engine_Primitive::getVertices(){
    return vertices;
}

vector<Vertice*> Engine_Primitive::getNormals(){
    return normals;
}

vector<Vertice*> Engine_Primitive::getTextures(){
    return textures;
}

vector<Transformation*> Engine_Primitive::getTransformations(){
    return transformations;
}

void Engine_Primitive::setTransformations(vector<Transformation*> t){
    transformations = t;
}

void Engine_Primitive::addVertice(Vertice *v){
    vertices.push_back(v);
}

void Engine_Primitive::addNormal(Vertice *v){
    normals.push_back(v);
}

void Engine_Primitive::addTexture(Vertice *v){
    textures.push_back(v);
}

void Engine_Primitive::addTransformation(Transformation *t){
    transformations.push_back(t);
}

void Engine_Primitive::setDiffuseComponent(float diffR, float diffG, float diffB){
    this->diffR = diffR;
    this->diffG = diffG;
    this->diffB = diffB;
}

void Engine_Primitive::setEmisiveComponent(float emisR, float emisG, float emisB){
    this->emisR = emisR;
    this->emisG = emisG;
    this->emisB = emisB;
}

void Engine_Primitive::setAmbientComponent(float ambiR, float ambiG, float ambiB){
    this->ambiR = ambiR;
    this->ambiG = ambiG;
    this->ambiB = ambiB;
}

void Engine_Primitive::setSpecularComponent(float specR, float specG, float specB){
    this->specR = specR;
    this->specG = specG;
    this->specB = specB;
}

void Engine_Primitive::setTexture(string texture_name){
    this->texture_name = texture_name;
}

int Engine_Primitive::getTexID() {
    return this->texID;
}

void Engine_Primitive::setTexID(int texID) {
    this->texID = texID;
}

string Engine_Primitive::getTexture() {
    return this->texture_name;
}
