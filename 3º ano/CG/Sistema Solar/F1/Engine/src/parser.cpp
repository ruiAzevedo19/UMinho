#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <sstream>
#include "parser.h"
#include "tinyxml2.h"

using namespace std;

std::vector<Vertex*> readFile(std::string pathToDb, std::string file_name, int* size){
    std::ifstream file;
    std::string line;
    std::stringstream ss;
    std::vector<Vertex*> vertices;

    float x,y,z;
    x = y = z = 0;

    file.open(pathToDb + file_name);
    if( file.is_open() ){
        getline(file,line);
        (*size) = std::stoi(line);
        ss.str("");
        while( getline(file, line) ){
            std::stringstream ss(line);
            ss >> x >> y >> z;
            vertices.push_back(new Vertex(x,y,z));
            ss.str("");
        }
        file.close();
    }
    else std::cout << "Error opening file " + file_name << "!" << std::endl;
    return vertices;
}

vector<vector<Vertex*>> readXML(std::string pathToXML, std::string pathToDb, vector<int>* sizes){
    std::vector<vector<Vertex*>> allVertices;
    std::vector<Vertex*> vertices;
    tinyxml2::XMLDocument doc;
    string s;
    int size;
    if (!(doc.LoadFile((pathToDb+pathToXML).c_str()))) {
        tinyxml2::XMLElement* root = doc.FirstChildElement("scene");
        if (root == nullptr) return allVertices;
        tinyxml2::XMLElement *elemento = root->FirstChildElement("model");
        while (elemento != nullptr) {
            s = elemento->Attribute("file");
            vertices = readFile(pathToDb, s,&size);
            (*sizes).push_back(size);
            allVertices.push_back(vertices);
            elemento = elemento->NextSiblingElement();
        }
    }
    else {
        cout << "Nao foi possivel realizar a leitura do ficheiro XML (possivelmente porque ele nao existe)! " << endl;
    }
    return allVertices;
}

/*
void readXML(string name, vector<Vertex*>* v) {
    XMLDocument doc;
    string s;
    string path = "xml/" + name;
    if (!(doc.LoadFile(path.c_str()))) {
        XMLElement* root = doc.FirstChildElement("scene");
        if (root == nullptr) return;
        XMLElement *elemento = root->FirstChildElement("model");
        while (elemento != nullptr) {
            s = elemento->Attribute("file");
            readFile(s, v);
            elemento = elemento->NextSiblingElement();
        }
    }
    else {
        cout << "Nao foi possivel realizar a leitura do ficheiro XML (possivelmente porque ele nao existe)! " << endl;
    }
    return;
}*/
