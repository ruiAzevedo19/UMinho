#ifndef _PARSER_H_
#define _PARSER_H_

#include <string>
#include <vector>
#include "vertex.h"

using namespace std;

vector<vector<Vertex*>> readXML(std::string pathToXML, std::string pathToDb, vector<int>* sizes);

#endif
