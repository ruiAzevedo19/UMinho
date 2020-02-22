##############################################
#
# Definicao do Field Separator
# RS = \n (por omissao)
#
# Inicio da estrutura de dados de um grafo
#
#	digraph grafo {
#		size="100,100";
#
# Escrita do inicio da estrutura no ficheiro
# graph.gv
#
##############################################

BEGIN { FS=";"
        graph = "digraph grafo {\n\tsize=\"100,100\";\n\trankdir=LR;\n";
	   printf graph > "graph.gv";
}

##############################################
#
# Normalizacao de todos os campos do registo
#
##############################################

{ 	
for(i = 1; i <= NF; i++) 
	gsub(/^\s+|\s+$|\s+(?=\s)/,"",$i)
}

##############################################
#
# Encontra todas as linhas cujo campo $4 (Titulo)  
# contenha a palavra carta, requerimento ou 
# certidao 
# Parte-se a frase na proposicao 'ao' e guarda
# no array 'autores' o resultado
# 	autores[1] := Autor
#	autores[2] := Destinatario
#
# Adiciona ao ficheiro graph.gv sd relacoes
#	"autor" -> "destinatario";
#
##############################################

{
	print $4;
}
$4 ~ /(Carta|Requerimento|Certidão)/ {	gsub(/((Carta|Requerimento|Certidão)(([^iA-Z]+)(d(e|o)+|pelo) | enviada|)|>.+)/,"",$4);
					split($4,autores,/ ao?s? /)
					
					if( contain[autores[1]][autores[2]] == 0 ){
						autor = "\t\"%s\" -> \"%s\";\n";
						printf autor, autores[1], autores[2] > "graph.gv";
					}
					contain[autores[1]][autores[2]]++
}

##############################################
#
# No fim de percorrer todos os registos fecha
# a definicao da estrutura do grafo
#
##############################################

END { printf "}" > "graph.gv";}

