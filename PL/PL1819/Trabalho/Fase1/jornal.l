%{
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#define MAXTAGPOST 20
#define MAX 10000

/*  --------------------------  Estruturas  ----------------------------------*/

// Estrutura para guardar o titulo de um artigo e respetivo id
typedef struct tBucket{
	char *titulo;
	char *id;
	struct tBucket *next;
}*Titulo;

// Estrutura para guardar as tags, nr de ocorrencias e correspondencia tag -> artigo
typedef struct tagBucket{
	char *tag;
 	int n;
	Titulo titulo;
}*TAG;

// Lista de todas as tags e respetivos artigos
typedef TAG tagList[MAX];

// Função que vai gerando a lista de tags e respetivos titulos
int generate(char *tag, char *tit, char *id, tagList tags ){
     int i = 0;
     int n  = strlen(tag);
	int ntit = strlen(tit);
	int nid  = strlen(id);

	Titulo titulo = malloc(sizeof(struct tBucket));
	titulo->titulo = malloc(sizeof(char) * ntit);
	strcpy(titulo->titulo,tit);
	titulo->id = malloc(sizeof(char) * nid);
	strcpy(titulo->id,id);
	titulo->next = NULL;

     while( i < MAX && tags[i] != NULL ){
          if(!strcmp(tag,tags[i]->tag)){
			if(tags[i]->titulo){
				titulo->next = tags[i]->titulo;
				tags[i]->titulo = titulo;
			}
			else
				(tags[i]->titulo) = titulo;
			(tags[i]->n)++;
               break;
          }
          else
               i++;
     }

     if( i < MAX && tags[i] == NULL){
          TAG tmp = malloc(sizeof(struct tagBucket));
          tmp->tag = malloc(sizeof(char) * n);
          strcpy(tmp->tag,tag);
          tmp->n = 1;
          tags[i] = tmp;
		tags[i]->titulo = titulo;
          return 0;
     }
     else
          return 1;
}

/*  ---------------------------  Variáveis  ----------------------------------*/

tagList ltag;
char * tagsPost[MAXTAGPOST];
char * title;
char * id;
char * category;
char * text;
char * date;
int numberTags = 0;
int j = 0;

%}

/*  -------------------------  Start conditions  -----------------------------*/

%x TAGS
%x TAG
%x DATE
%x ID
%x CLEAN
%x CATEGORY
%x TITLE
%x TEXT

/*  ------------------------------  Flex  ------------------------------------*/

%%

#TAG:   			{BEGIN TAGS; numberTags = 0;}
<TAGS>tag:\{ 		{BEGIN TAG;}
<TAGS># 			{BEGIN INITIAL;}

<TAG>\}   			{BEGIN TAGS;}
<TAG>[^}]+ 			{tagsPost[numberTags++]=strdup(yytext);}

ID:\{				{BEGIN ID;}
<ID>[^ ]+			{id = strdup(yytext); BEGIN CLEAN;}

<CLEAN>[^}]+\}\n	{BEGIN CATEGORY;}

<CATEGORY>.+\n\n    {yytext[yyleng-2] = '\0'; category = strdup(yytext); BEGIN TITLE;}

<TITLE>.+			{title = strdup(yytext);}
<TITLE>\n\n      	{BEGIN INITIAL;}

#DATE:\ *\[[^]]+\]\ *   {BEGIN DATE;}
<DATE>[^\n]+ 		 	{date = strdup(yytext);}
<DATE>\n[^\n]*\n\n 		{BEGIN TEXT;}

<TEXT>[^<]+			{text = strdup(yytext); BEGIN INITIAL;}


\<\/pub\>			{FILE *fp;
					char * idPost = strdup(id);
					char *idP = strcat(idPost,".html");
					fp = fopen(idP, "w");
					fprintf(fp, "<html>\n<head>\n\t<meta charset=\"UTF-8\">\n\t<pub id =%s>\n\t<title>%s</title>\n</head>\n", id, title);
					fprintf(fp, "<body>\n\t<h2><author_date>%s</author_date></h2>\n\t<hr>\n\t<p>\n\t<tags>Tags:\n", date);
					for(j= 0; j < numberTags; j++){
						generate(tagsPost[j], title, idP, ltag);
						fprintf(fp,"\t\t<li><tag>%s</tag></li>\n", tagsPost[j]);
					}
					fprintf(fp,"\t</tags>\n\t</p>\n\t<hr>\n\t<p>\t<category>%s</category></p>\n\t<hr>\n\t<text>\n%s\n\t</text>\n</body>\n</html>", category, text);
					fclose(fp);}


<*>.|\n    {;}

%%

int yywrap(){
    return 1;
}

/*  ------------------------------  Main  ------------------------------------*/

int main(){
	yylex();

    	Titulo tmp;
    	FILE *fp;
	fp = fopen("tags.html", "w");
	fprintf(fp, "<head><meta charset=\"UTF-8\"></head>");
	fprintf(fp, "<html><body><h1>Índice de tags</h1>");

	for(int i = 0; i < MAX && ltag[i] != NULL; i++){
		fprintf(fp, "\n<li>Tag: %s | Ocorrência: %d <p>\n\n", ltag[i]->tag,ltag[i]->n);
		for(tmp = ltag[i]->titulo; tmp; tmp = tmp->next)
			fprintf(fp, "\n<p><a href=file:///Users/joanacruz/Desktop/PL/Fase1/%s>%s</a></p>", tmp->id,tmp->titulo);
		fprintf(fp, "</p></li>");
	}
    fprintf(fp, "</body></html>");
    return 0;
}
