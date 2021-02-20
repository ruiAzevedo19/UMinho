/* Minimização do tempo de conclusão obedecendo precedências */


/* Função Objectivo */
min: tfinal -tinicial ;


/* Variáveis de decisão:

	tinicial : Tempo inicial do projecto.


	ti : representa o tempo de início da actividade i,com i ∈ {0,1,3,4,5,6,8,9,10,11}

	tfinal : Tempo final do projecto.

 */



 /* 
 Restrições de precedência :

 t(i,j) : t(j) >= t(i) + d(i),  sendo d(i) a duração de uma determinada actividade.

 */


//arco(inicial_0)
t0>= tinicial + 0;

//arco(inicial_6) 
t6>= tinicial + 0;

//arco(0,1)
t1>= t0 + 4;

//arco(0,4) 
t4>= t0 + 4;

//arco(1,3)
t3>= t1 + 6;

//arco(3,final)
tfinal>= t3 + 2;

//arco(4,3)
t3>= t4 + 9;

//arco(4,5) 
t5>= t4 + 9;

//arco(5,3)
t3>= t5 + 4;

//arco(5,final)
tfinal>= t5 + 4;

//arco(6,4)
t4>= t6 + 5;

//arco(6,8)
t8>= t6 + 5;

//arco(6,10)
t10>= t6 + 5;

//arco(8,5)
t5>= t8 + 4;

//arco(8,9)
t9>= t8 + 4;

//arco(9,final)
tfinal >= t9 + 2;

//arco(10,8)
t8>= t10 + 8;

//arco(10,11)
t11>= t10 + 8;

//arco(11,9)
t9>= t11 + 7;




int tinicial,t0,t1,t3,t4,t5,t6,t8,t9,t10,t11,tfinal;

