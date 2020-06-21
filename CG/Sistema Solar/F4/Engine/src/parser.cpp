#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <sstream>
#include "../headers/parser.h"
#include "../headers/tinyxml2.h"

using namespace std;
using namespace tinyxml2;

vector<Engine_Primitive*> allPrimitivesLocal;
string globalPathToDb = "";

Engine_Primitive* readFile(string globalPathToDb, string file_name, int* size){
    ifstream file;
    string line;
    stringstream ss;
    int type = 0; // type 0 is points, type 1 is normals, type 2 is textures
    Engine_Primitive* p = new Engine_Primitive();

    float x,y,z;
    x = y = z = 0;

    file.open(globalPathToDb + file_name);
    if( file.is_open() ){
        getline(file, line);
        if (!line.compare("Pontos")) getline(file, line);
        ss.str("");
        (*size) = std::stoi(line);
        while( getline(file, line) ){
            if (!line.compare("Normais") || !line.compare("Texturas")) {
                type++;
                getline(file, line);
                ss.str("");
            }
            else {
                stringstream ss(line);
                ss >> x >> y >> z;
                if (type == 0) {
                    p->addVertice(new Vertice(x, y, z));
                }
                if (type == 1) {
                    p->addNormal(new Vertice(x, y, z));
                }
                if (type == 2) {
                    p->addTexture(new Vertice(x, y, z));
                }
                ss.str("");
            }
        }
        file.close();
    }
    else cout << "Error opening file " + file_name << "!" << std::endl;
    return p;
}

Vertice* parsePoint(const tinyxml2::XMLAttribute *attribute){
        float x = 0, y = 0, z = 0;
        while(attribute) {
            const char* value = attribute->Name();
            if(!strcmp(value, "X")) x = atof(attribute->Value());
            if(!strcmp(value, "Y")) y = atof(attribute->Value());
            if(!strcmp(value, "Z")) z = atof(attribute->Value());
            attribute = attribute->Next();
        }
        Vertice* v = new Vertice(x, y, z);
        return v;
}

Transformation* parseTranslation(const tinyxml2::XMLElement *element){
        const tinyxml2::XMLAttribute *attribute = element->FirstAttribute();
        float x = 0, y = 0, z = 0, time = 0;
        bool timeflag = false;
        vector<Vertice*> catmull_rom_vertices;
        while(attribute) {
            const char* value = attribute->Name();
            if(!strcmp(value, "X")) x = atof(attribute->Value());
            if(!strcmp(value, "Y")) y = atof(attribute->Value());
            if(!strcmp(value, "Z")) z = atof(attribute->Value());
            if(!strcmp(value, "time")) { time = atoi(attribute->Value()); timeflag = true; }
            attribute = attribute->Next();
        }
        /* If it is a Catmull-Rom cubic curve then read the points and assign them */
        if(timeflag){
            const XMLElement *points_element = element->FirstChildElement();
            while(points_element){
                catmull_rom_vertices.push_back(parsePoint(points_element->FirstAttribute()));
                points_element = points_element->NextSiblingElement();
            }
        }
        Transformation *t = new Translation(x, y, z, time, catmull_rom_vertices);
        return t;
}

Transformation* parseRotation(const tinyxml2::XMLAttribute *attribute){
        /*Time overrides angle*/
        float angle = 0, x = 0, y = 0, z = 0, time = 0;
        while(attribute) {
            const char* value = attribute->Name();
            if(!strcmp(value, "angle")) angle = atof(attribute->Value());
            if(!strcmp(value, "axisX")) x = atof(attribute->Value());
            if(!strcmp(value, "axisY")) y = atof(attribute->Value());
            if(!strcmp(value, "axisZ")) z = atof(attribute->Value());
            if(!strcmp(value, "time")) time = atoi(attribute->Value());
            attribute = attribute->Next();
        }
        Transformation *t = new Rotation(angle, x, y, z, time);
        return t;
}

Transformation* parseScaling(const tinyxml2::XMLAttribute *attribute){
    float x = 0, y = 0, z = 0;
    while(attribute) {
        const char* value = attribute->Name();
        if(!strcmp(value, "X")) x = atof(attribute->Value());
        if(!strcmp(value, "Y")) y = atof(attribute->Value());
        if(!strcmp(value, "Z")) z = atof(attribute->Value());
        attribute = attribute->Next();
    }
    Transformation *t = new Scale(x, y, z);
    return t;
}

/* Parses the components of a colored model and the texture for a textured model */
void parseModelInfo(const tinyxml2::XMLAttribute *attribute, Engine_Primitive *p){
    float diffR = 0, diffG = 0, diffB = 0;
    float emisR = 0, emisG = 0, emisB = 0;
    float ambiR = 0, ambiG = 0, ambiB = 0;
    float specR = 0, specG = 0, specB = 0;
    while(attribute) {
        const char* value = attribute->Name();
        if(!strcmp(value, "texture")) p->setTexture(attribute->Value());
        if(!strcmp(value, "diffR")) diffR = atof(attribute->Value());
        if(!strcmp(value, "diffG")) diffG = atof(attribute->Value());
        if(!strcmp(value, "diffB")) diffB = atof(attribute->Value());
        if(!strcmp(value, "emisR")) emisR = atof(attribute->Value());
        if(!strcmp(value, "emisG")) emisG = atof(attribute->Value());
        if(!strcmp(value, "emisB")) emisB = atof(attribute->Value());
        if(!strcmp(value, "ambiR")) ambiR = atof(attribute->Value());
        if(!strcmp(value, "ambiG")) ambiG = atof(attribute->Value());
        if(!strcmp(value, "ambiB")) ambiB = atof(attribute->Value());
        if(!strcmp(value, "specR")) specR = atof(attribute->Value());
        if(!strcmp(value, "specG")) specG = atof(attribute->Value());
        if(!strcmp(value, "specB")) specB = atof(attribute->Value());
        attribute = attribute->Next();
    }
    p->setDiffuseComponent(diffR, diffG, diffB);
    p->setEmisiveComponent(emisR, emisG, emisB);
    p->setAmbientComponent(ambiR, ambiG, ambiB);
    p->setSpecularComponent(specR, specG, specB);
}

void parseGroupElements(XMLElement* group, vector<Transformation*> transformations){
    Engine_Primitive* p;
    string s;
    int size;

    XMLElement *elementos = group->FirstChildElement("models");
    XMLElement *elemento = NULL;
    if(elementos) elemento = elementos->FirstChildElement("model");

    while (elemento) {
        /* Read the file containing the vertices and create the primitive with the corresponding geometric transforms */
        s = elemento->Attribute("file");
        p = readFile(globalPathToDb, s,&size);
        p->setTransformations(transformations);

        /* Read the model info if there's any */
        parseModelInfo(elemento->FirstAttribute(), p);
        allPrimitivesLocal.push_back(p);
        elemento = elemento->NextSiblingElement();
    }
}

void parseGeometricTransforms(XMLElement* group, vector<Transformation*>* transformations){
    XMLElement *element = group->FirstChildElement();
    while(element){
        if(!strcmp(element->Value(), "translate")) transformations->push_back(parseTranslation(element));
        if(!strcmp(element->Value(), "rotate")) transformations->push_back(parseRotation(element->FirstAttribute()));
        if(!strcmp(element->Value(), "scale")) transformations->push_back(parseScaling(element->FirstAttribute()));
        element = element->NextSiblingElement();
    }
}

void parseGroups(XMLElement* root, vector<Transformation*> transformations){
    vector<Transformation*> localTransformations = transformations;

    XMLElement *group = root->FirstChildElement("group");
    while(group){
        parseGeometricTransforms(group, &localTransformations);
        parseGroupElements(group, localTransformations);
        parseGroups(group, localTransformations);
        group = group->NextSiblingElement();
        localTransformations = transformations;
    }
}

Light* parseLight(const tinyxml2::XMLAttribute* attribute){
    Light* light; // the light we want to obtain
    Trio* pos = new Trio(0, 0, 1); // posição
    Trio* ambi = new Trio(0, 0, 0); // cor ambiente
    Trio* spec = new Trio(1, 1, 1); // cor especular
    Trio* diff = new Trio(1, 1, 1); // cor difusa
    Trio* spot = new Trio(0, 0, -1); // Direção da spot light

    float constant = 1.0f, linear = 0.0f, quadratic = 0.0f; // atenuação
    float cutoff = 180.0f, exponent = 0.0f;
    bool isPoint = false, isDirectional = false, isSpot = false; // tipo

    /* Falta perceber se faz sentido permitir a alteração das cores */
    while(attribute) {
        const char* value = attribute->Name();
        if(!strcmp(value, "posX")) pos->setX(atof(attribute->Value()));
        if(!strcmp(value, "posY")) pos->setY(atof(attribute->Value()));
        if(!strcmp(value, "posZ")) pos->setZ(atof(attribute->Value()));
        if(!strcmp(value, "ambiX")) ambi->setX(atof(attribute->Value()));
        if(!strcmp(value, "ambiY")) ambi->setY(atof(attribute->Value()));
        if(!strcmp(value, "ambiZ")) ambi->setZ(atof(attribute->Value()));
        if(!strcmp(value, "specX")) spec->setX(atof(attribute->Value()));
        if(!strcmp(value, "specY")) spec->setY(atof(attribute->Value()));
        if(!strcmp(value, "specZ")) spec->setZ(atof(attribute->Value()));
        if(!strcmp(value, "diffX")) diff->setX(atof(attribute->Value()));
        if(!strcmp(value, "diffY")) diff->setY(atof(attribute->Value()));
        if(!strcmp(value, "diffZ")) diff->setZ(atof(attribute->Value()));
        if(!strcmp(value, "spotDirectionX")) spot->setX(atof(attribute->Value()));
        if(!strcmp(value, "spotDirectionY")) spot->setY(atof(attribute->Value()));
        if(!strcmp(value, "spotDirectionZ")) spot->setZ(atof(attribute->Value()));
        if(!strcmp(value, "type")){
            if(!strcmp(attribute->Value(), "POINT")) isPoint = true;
            if(!strcmp(attribute->Value(), "DIRECTIONAL")) isDirectional = true;
            if(!strcmp(attribute->Value(), "SPOT")) isSpot = true;
        }
        attribute = attribute->Next();
    }
    if(isPoint) light = new Point_Light(pos, diff, spec, ambi, constant, linear, quadratic);
    else if(isDirectional) light = new Direction_Light(pos, diff, spec, ambi, constant, linear, quadratic);
    else if(isSpot) light = new Spot_Light(pos, diff, spec, ambi, constant, linear, quadratic, spot, cutoff, exponent);
    else light = NULL;

    return light;
}

Light* parseLights(XMLElement* root){
    XMLElement* lights = root->FirstChildElement("lights");
    /* If the child element exists parse the lights accordingly to the type */
    if(lights){
        XMLElement* element = lights->FirstChildElement();
        if(!strcmp(element->Value(), "light")) return parseLight(element->FirstAttribute());
    }

    return NULL;
}

vector<Engine_Primitive*> readXML(string pathToXML, string pathToDb){
    XMLDocument doc;
    globalPathToDb += pathToDb;
    string fullPath = globalPathToDb + pathToXML;
    vector<Transformation*> transformations;

    if (!(doc.LoadFile(fullPath.c_str()))) {
        XMLElement* root = doc.FirstChildElement("scene");
        if (root == nullptr) return allPrimitivesLocal;
        parseGroups(root, transformations);
        parseLights(root);
    }
    else {
        cout << "Nao foi possivel realizar a leitura do ficheiro XML (possivelmente porque ele nao existe)! " << endl;
    }
    return allPrimitivesLocal;
}
