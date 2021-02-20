#ifndef _PARSER_H_
#define _PARSER_H_

#include "interface.h"


/**
 * Carrega a estrutura geral do sistema com a informacao contida nos ficheiros
 *
 * sgv : estrutura geral do sistema
 * fildesFolderPath : caminho para os ficheiros
 */
void loadFiles(SGV sgv, char* clientsFilePath, char* productsFilePath, char* salesFilePath);

#endif
