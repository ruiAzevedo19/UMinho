#ifndef _PARSER_H_
#define _PARSER_H_

#include <string>
#include <vector>
#include "../../Generator/headers/vertice.h"
#include "../../Generator/headers/plane.h"
#include "../headers/engine_primitive.h"
#include "../headers/transformation.h"
#include "../headers/scale.h"
#include "../headers/rotation.h"
#include "../headers/translation.h"
#include "../headers/light.h"
#include "../headers/point_light.h"
#include "../headers/direction_light.h"
#include "../headers/spot_light.h"
#include "../headers/trio.h"

using namespace std;

vector<Engine_Primitive*> readXML(std::string pathToXML, std::string pathToDb);

#endif
