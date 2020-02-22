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


arco_inicial0: t0>= tinicial + 0;

arco_inicial6: t6>= tinicial + 0;

arco_01: t1>= t0 + 4;

arco_04: t4>= t0 + 4;

arco_13: t3>= t1 + 6;

arco_3final: tfinal>= t3 + 2;

arco_43: t3>= t4 + 9;

arco_45: t5>= t4 + 9;

arco_53: t3>= t5 + 4;

arco_5final: tfinal>= t5 + 4;

arco_64: t4>= t6 + 5;

arco_68: t8>= t6 + 5;

arco_610: t10>= t6 + 5;

arco_85: t5>= t8 + 4;

arco_89: t9>= t8 + 4;

arco_9final: tfinal >= t9 + 2;

arco_108: t8>= t10 + 8;

arco_1011: t11>= t10 + 8;

arco_119: t9>= t11 + 7;

// y48,y411,y811

t8 + 100 - 100y48 >= t4 + 9;
t4 + 100y48 >= t8 + 4;

t11 + 100 - 100y411 >= t4 + 9;
t4 + 100y411 >= t11 + 7;

t11 + 100 - 100y811 >= t8 + 4;
t8 + 100y811 >= t11 + 7;






int tinicial,t0,t1,t3,t4,t5,t6,t8,t9,t10,t11,tfinal;
bin y48,y411,y811;

