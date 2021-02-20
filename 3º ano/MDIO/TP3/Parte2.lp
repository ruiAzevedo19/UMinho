/* */


/* Função Objectivo */

min : 200 r0p + 600r1p +200r3p + 800r4p +1600r5p + 180r6p + 200r8p + 0r9p + 1000r10p + 600r11p + 100r0s + 300r1s + 100r3s + 400r4s + 800r5s + 90r6s + 100r8s + 0r9s + 500r10s + 300r11s ;


/* Variáveis de decisão:

tinicial: instante de tempo que o projecto se inicia.[U.T].

ti: Tempo de início de execução da actividade i, com i ∈ {0,1,3,4,5,6,8,9,10,11}.

tfinal: Instante de tempo que o projecto termina.

rip: Primeira redução de duração da actividade i, com i ∈ {0,1,3,4,5,6,8,9,10,11}.

ris: Segunda redução da actividade i, com i ∈ {0,1,3,4,5,6,8,9,10,11}. 


si: 1 se a segunda redução for aplicada a actividade i, com i ∈ 
			{0,1,3,4,5,6,8,9,10,11}.
	0, caso contrário.


rri: Resto da primeira redução em relação a redução máxima permitida, com i ∈ {0,1,3,4,5,6,8,9,10,11}.

 */


 // Restrições 

 // Tempo máximo para conclusão
 tfinal - tinicial <= 20;

 // Reduções máximas permitidas no projecto.

 // Primeira redução

 r0p + rr0 = 0.5;
 r1p + rr1 = 1;
 r3p + rr3 = 0.5;
 r4p + rr4 = 2;
 r5p + rr5 = 0.5;
 r6p + rr6 = 1;
 r8p + rr8 = 0.5;
 r9p + rr9 = 0;
 r10p + rr10 = 0.5;
 r11p + rr11 = 1;

 // Segunda redução

 r0s <= 0.5s0;
 r1s<= 1s1;
 r3s<= 0.5s3;
 r4s<= 1s4;
 r5s<= 0.5s5;
 r6s<= 1s6;
 r8s<= 0.5s8;
 r9s<= 0s9;
 r10s<= 0.5s10;
 r11s<= 1s11;


 /* Restos das reduções máximas no projecto referentes a primeira redução caso a segunda redução seja realizada tem de ser igual a 0, ou seja rri <= c1(max)i - c1(max)isi
*/

rr0 <= 0.5 -0.5s0;
rr1 <= 1 - 1s1;
rr3 <= 0.5 - 0.5s3;
rr4 <= 2 - 2s4;
rr5 <= 0.5 - 0.5s5;
rr6 <= 1 - 1s6;
rr8 <= 0.5 - 0.5s8;
rr9 <= 0 - 0s9;
rr10 <= 0.5 - 0.5s10;
rr11 <= 1 - 1s11;



// Precedências

t0 >= tinicial + 0;
t6 >= tinicial + 0;
t1 + r0p + r0s >= t0 + 4;
t4  + r0p + r0s >=t0 + 4;
t3  + r1p + r1s >= t1 + 6;
tfinal  + r3p + r3s >= t3 + 2;
t3  + r4p + r4s >= t4 + 9;
t5  + r4p + r4s >= t4 + 9;
t3  + r5p + r5s >= t5 + 4;
tfinal  + r5p + r5s >= t5 + 4;
t4  + r6p + r6s >= t6 + 5;
t8  + r6p + r6s >= t6 + 5;
t10  + r6p + r6s >= t6 + 5;
t5  + r8p + r8s >= t8 + 4;
t9  + r8p + r8s >= t8 + 4;
tfinal  + r9p + r9s >= t9 + 2;
t8  + r10p + r10s >= t10 + 8;
t9  + r11p + r11s >= t11 + 7;
t11  + r10p + r10s >= t10 +8;


bin s0,s1,s3,s4,s5,s6,s8,s9,s10,s11;

