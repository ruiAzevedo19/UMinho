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
    Engine_Primitive* p = new Engine_Primitive();

    float x,y,z;
    x = y = z = 0;

    file.open(globalPathToDb + file_name);
    if( file.is_open() ){
        getline(file,line);
        (*size) = std::stoi(line);
        ss.str("");
        while( getline(file, line) ){
            stringstream ss(line);
            ss >> x >> y >> z;
            p->addVertice(new Vertice(x, y, z));
            ss.str("");
        }
        file.close();
    }
    else cout << "Error opening file " + file_name << "!" << std::endl;
    return p;
}

Transformation* parseTranslation(const tinyxml2::XMLAttribute *attribute){
        float x = 0, y = 0, z = 0;
        while(attribute) {
            const char* value = attribute->Name();
            if(!strcmp(value, "X")) x = atof(attribute->Value());
            if(!strcmp(value, "Y")) y = atof(attribute->Value());
            if(!strcmp(value, "Z")) z = atof(attribute->Value());
            attribute = attribute->Next();
        }
        Transformation *t = new Translation(x, y, z);
        return t;
}

Transformation* parseRotation(const tinyxml2::XMLAttribute *attribute){
        float angle = 0, x = 0, y = 0, z = 0;
        while(attribute) {
            const char* value = attribute->Name();
            if(!strcmp(value, "angle")) angle = atof(attribute->Value());
            if(!strcmp(value, "axisX")) x = atof(attribute->Value());
            if(!strcmp(value, "axisY")) y = atof(attribute->Value());
            if(!strcmp(value, "axisZ")) z = atof(attribute->Value());
            attribute = attribute->Next();
        }
        Transformation *t = new Rotation(angle, x, y, z);
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

void parseGroupElements(XMLElement* group, vector<Transformation*> transformations){
    Engine_Primitive* p;
    string s;
    int size;

    XMLElement *elementos = group->FirstChildElement("models");
    XMLElement *elemento = elementos->FirstChildElement("model");
    while (elemento) {
        s = elemento->Attribute("file");
        p = readFile(globalPathToDb, s,&size);
        p->setTransformations(transformations);
        allPrimitivesLocal.push_back(p);
        elemento = elemento->NextSiblingElement();
    }
}

void parseGeometricTransforms(XMLElement* group, vector<Transformation*>* transformations){
    XMLElement *element = group->FirstChildElement();
    while(element){
        if(!strcmp(element->Value(), "translate")) transformations->push_back(parseTranslation(element->FirstAttribute()));
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
        localTransformations.clear();
    }
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
    }
    else {
        cout << "Nao foi possivel realizar a leitura do ficheiro XML (possivelmente porque ele nao existe)! " << endl;
    }
    return allPrimitivesLocal;
}
