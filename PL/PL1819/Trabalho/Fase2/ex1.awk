##############################################
#
# Definicao do Field Separator
# RS = \n (por omissao)
#
##############################################

BEGIN {FS=";"}

##############################################
#
# Normalizacao de todos os campos do registo
#
##############################################

{ 
for(i = 1; i <= NF; i++) 
	{gsub(/^\s+|[]]|\s+$|\s+(?=\s)/,"",$i)}
}

##############################################
#
# Se nao existir local coloca o campo com o
# valor de NIL
#
##############################################

length($3)==0	{$3 = "NIL"}

##############################################
#
# Estrutura que armazena o numero de cartas 
# por local relacionando com o ano de escrita
#
##############################################

NF>5		{nr_cartas[$3][$2]++}

##############################################
#
# No fim de percorrer todos os registos e
# impresso o resultado
#
##############################################

END{
	for(i in nr_cartas){
		printf("> %s\n",i)
		for(j in nr_cartas[i]){
			total += nr_cartas[i][j]
			printf("\t %4s %6s\n",j, nr_cartas[i][j])
		}
		printf("         %s\n","-----------------")
		printf("\t %4s =    %6s\n\n","Total",total)
		total = 0
	}
}

