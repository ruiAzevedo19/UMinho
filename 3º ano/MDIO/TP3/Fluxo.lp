/* Função Objectivo */
// Caminho mais longo num grafo acíclico.
max:  4x0_1 + 4x0_4 + 6x1_3 + 2x3_f + 9x4_3 + 9x4_5 + 4x5_3+ 4x5_f + 5x6_4 + 5x6_8 + 5x6_10 + 4x8_5 + 4x8_9 + 2x9_f + 8x10_8 + 8x10_11 + 7x11_9 ;

/* Restrições */
// Fluxo que entra no vértice = fluxo que sai

xi_0 + xi_6 = 1;

xi_0 = x0_1 + x0_4;

x0_1 = x1_3;

x1_3 + x4_3 + x5_3 = x3_f ;

x0_4 + x6_4 = x4_3 + x4_5 ;

x4_5 + x8_5 = x5_3 + x5_f;

xi_6 = x6_4 + x6_8 + x6_10;

x6_8 + x10_8 = x8_5 + x8_9;

x8_9 + x11_9 = x9_f;

x6_10 = x10_8 +x10_11;

x10_11 = x11_9;

x3_f + x5_f + x9_f = 1;

/* Restrições de não negatividade */
x0_1 >=0;
x0_4 >=0; 
x1_3 >=0;  
x3_f >=0;  
x4_3 >=0; 
x4_5 >=0; 
x5_3 >=0; 
x5_f >=0; 
x6_4 >=0; 
x6_8 >=0; 
x6_10 >=0; 
x8_5 >=0; 
x8_9 >=0; 
x9_f >=0;  
x10_8 >=0; 
x10_11 >=0; 
x11_9 >=0; 


/* Restrições de integralidade */
int x0_1,x0_4,x1_3,x3_f,x4_3,x4_5,x5_3,x5_f,x6_4,x6_8,x6_10,x8_5,x8_9,  
x9_f,x10_8,x10_11,x11_9;  

