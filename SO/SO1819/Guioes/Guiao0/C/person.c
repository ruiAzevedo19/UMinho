#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "person.h"

Person new_person(char *name, int age){
     Person p;
     char *s  = strdup(name);
     p.nome  = s;
     p.idade = age;

     return p;
}

Person clone_person(Person *p){
     return(Person){
          .nome  = strdup(p->nome),
          .idade = p->idade
     };
}

void destroy_person(Person *p){
     free(p->nome);
}

int person_age(Person *p){
     return(p->idade);
}

void person_change_age(Person * p, int age){
     p->idade = age;
}
