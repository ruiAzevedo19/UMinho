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
	if(i != 5 )
      	gsub(/^\s+|\s+$|\s+(?=\s)/,"",$i)
}

##############################################
#
# Parte o campo $5 em apelidos através da 
# funcao split. Em cada posicao do array
# apelidos temos um apelido
#
##############################################

{split($5,apelidos,/\s{2,}|:/)}
#{split($5,apelidos,/[^a-zA-ZáÁéÉóÓçÇ]+/)}

##############################################
#
# Imprime o resultado ao fim de processar 
# cada record
#	result := <id,[Apelidos]>
#
##############################################

length(apelidos)>0	{ printf("Número da carta: %d\n",$1);	
			  printf("Apelidos: ")
			  for(i in apelidos){ 
				if(length(apelidos[i]) > 0)
					printf("%s| ", apelidos[i])
			  }
			  print "\n"					
							 
			}
