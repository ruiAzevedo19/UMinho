#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "person.h"

int main(){
     Person p = new_person("rui",23);
     printf("Nome: %s\n", p.nome);
     printf("Idade: %d\n", p.idade);
}
