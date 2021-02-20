##############################################
#
# Definicao do Field Separator
# RS = \n (por omissao)
#
# Definicao de tags HTML
#
##############################################

BEGIN { FS=";"
        headerTitle="Processamento de Linguagens-TP2";
        bodyTitle="Índice de anos";
        headerFormat= "<html><head><meta charset = 'UTF-8'/><title>%s</title></head><body><center><h1>%s</h1></center><ul>\n";
        refHtml = "<a href=file:///Users/joanacruz/Desktop/PL/Fase2/%s>%s</a>\n";
       # refHtml = "<a href=file:///Users/ruiazevedo/Desktop/Universidade/PL/PL/Fase2/%s>%s</a>\n";
        textHtml = "<h3>%s</h3>%s";
        endHtml = "</ul></body></html>";
        printf headerFormat , headerTitle , bodyTitle > "index.html";
}

##############################################
#
# Normalizacao de todos os campos do registo
#
# No campo $2 (datas), apenas nos interessa 
# o ano
#
##############################################

{ 	
for(i = 1; i <= NF; i++) 
	gsub(/^\s+|\s+$|\s+(?=\s)/,"",$i)
gsub(/\.[0-9.]+/,"",$2);
}

##############################################
#
# Agregacao de todos os campos a partir do
# campo $6 numa unica string
#
# Estes campos representao a descricao da carta
# dai necessitarmos de os agregar
#
# Estrutura 'ano' que armazena a correspondencia
# entre uma data um titulo de uma carta e a 
# respetiva descricao
#
##############################################

{
	for(i=6; i <=NF; i++)
		str=sprintf("%s %s", str, $i);
	ano[$2][$4] = str;
	str = NULL;
}

##############################################
#
# No fim de percorrer todos os registos são
# gerados os ficheiros HTML
#
##############################################

END {  	for(i in ano){
			printf headerFormat, headerTitle, i > i".html";
			for(j in ano[i])
				printf textHtml, j, ano[i][j] > i".html";
			printf endHtml > i".html";
			printf refHtml, i".html", i > "index.html";
		}
		print endHtml > "index.html"; 
}


