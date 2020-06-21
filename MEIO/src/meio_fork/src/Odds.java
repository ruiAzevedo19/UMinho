import java.util.Arrays;

public class Odds {

    static Double[][] b1 = {{0.0392 , 0.0692 , 0.1236 , 0.1360 , 0.1284 , 0.1124 , 0.1108 , 0.0900 , 0.0640 , 0.0580 , 0.0416 , 0.0200 , 0.0068} ,
                            {0.0352 , 0.0700 , 0.1152 , 0.1412 , 0.1452 , 0.1032 , 0.1024 , 0.0892 , 0.0740 , 0.0568 , 0.0376 , 0.0228 , 0.0072} };
    static Double[][] b2 = {{0.0460 , 0.0816 , 0.1244 , 0.1492 , 0.1136 , 0.1108 , 0.1020 , 0.0876 , 0.0664 , 0.0508 , 0.0384 , 0.0212 , 0.0080},
                            {0.0516 , 0.1548 , 0.2132 , 0.2136 , 0.1692 , 0.1092 , 0.0556 , 0.0224 , 0.0072 , 0.0024 , 0.0008 , 0.0000 , 0.0000} };

    static int states = 13;
    static int totalStates = 169;

    static int REQUEST  = 0;
    static int DELIVERY = 1;

    static int DECISIONS = 7;
    static int BRANCHES  = 2;

    static double TRANSFER = 7.0;

    public static Double[][] decisionM(Double[][] probs, int decision){
        Double[][] p = new Double[states][states];
        int i,j,k,d,r;
        int minRequest, maxDelivery, minDelivery;
        for(i = 0; i < states; i++){
            for(j = 0; j < states; j++) {
                System.out.print(i + "," + j + ":");
                p[i][j] = 0.0;
                maxDelivery = j - decision;
                minDelivery = (i >= j) ? 0 : (j - i - 1);
                minRequest = (j > i) ? 0 : (i - j + decision);

                if( j == states - 1) {
                    if(i == states - 1){
                        for(r = 1; r < states; r++)
                            for(int kx = r -1; kx < states; kx++) {
                                System.out.print(" P(" + r + ")*E(" + kx + ") +");
                                p[i][j] += probs[REQUEST][r] * probs[DELIVERY][kx];
                            }
                    }
                    else {
                        int ax = minDelivery;
                        for (r = 0; r < i; r++) {
                            for (k = ax; k < states; k++) {
                                System.out.print(" P(" + r + ")*E(" + k + ") +");
                                p[i][j] += probs[REQUEST][r] * probs[DELIVERY][k];
                            }
                            ax++;
                        }
                        for (; r < states; r++)
                            for (int kx = 0; kx < decision + 1; kx++) {
                                System.out.print(" P(" + r + ")*E(" + (12 - kx) + ") +");
                                p[i][j] += probs[REQUEST][r] * probs[DELIVERY][12 - kx];
                            }
                    }
                    /*
                    int ax = minDelivery;
                    for (r = minRequest; r < states; r++) {
                        if (ax == states)
                            break;
                        for (k = ax; k < states; k++) {
                            System.out.print(" P(" + r + ")*E(" + k + ") +");
                            p[i][j] += probs[REQUEST][r] * probs[DELIVERY][k];
                        }
                        ax++;
                    }
                    for (; r < states; r++) {
                        for(k = 0; k < decision; k++) {
                            System.out.print(" P(" + r + ")*E(" + (ax - 1 - k) + ") +");
                            p[i][j] += probs[REQUEST][r] * probs[DELIVERY][ax - 1 - k];
                        }
                    }
                     */
                }
                    else{
                        if (j >= decision) {
                            for (d = minDelivery, r = minRequest; d < maxDelivery + 1; d++, r++) {
                                    System.out.print(" P(" + r + ")*E(" + d + ") +");
                                    p[i][j] += probs[REQUEST][r] * probs[DELIVERY][d];
                            }
                            for (; r < states; r++) {
                                System.out.print("P(" + r + ")*E(" + (d-1) + ") +");
                                p[i][j] += probs[REQUEST][r] * probs[DELIVERY][(d-1)];
                            }
                        }
                    }
                System.out.println();
            }
        }
        return p;
    }

    public static Double[][] deliveryRequestProbs(Double[][] probs){
        Double[][] p = new Double[states][states];
        int i,j,k,d,r;
        int minRequest, maxDelivery, minDelivery;
        for(i = 0; i < states; i++){
            for(j = 0; j < states; j++){
                p[i][j] = 0.0;
                maxDelivery = j;
                minDelivery = (i > j) ? 0 : ( j - i );
                minRequest  = (j > i) ? 0 : ( i - j );
                for(d = minDelivery, r = minRequest; d < maxDelivery; d++, r++)
                    if( j == states - 1 )
                        for(k = d; k < states; k++)
                            p[i][j] += probs[REQUEST][r] * probs[DELIVERY][k];
                    else
                        p[i][j] += probs[REQUEST][r] * probs[DELIVERY][d];
                for( ; r < states; r++)
                    p[i][j] += probs[REQUEST][r] * probs[DELIVERY][d];
            }
        }
        return p;
    }

    public static Double[][] merge(Double[][] m, Double[][] t, char type){
        Double[][] p = new Double[totalStates][totalStates];
        int i = -1,j;
        int mi, mj, ti, tj;

        for(mi = 0; mi < states; mi++)
            for(mj = 0; mj < states; mj++){
                i++;
                for (ti = 0, j = 0; ti < states; ti++)
                    for (tj = 0; tj < states; tj++, j++)
                        if( type == 'P')
                            p[i][j] = m[mi][ti] * t[mj][tj];
                        else
                            p[i][j] = m[mi][ti] + t[mj][tj];
            }
        return p;
    }

    public static Double[][] decisionMakerPositive(Double[][] probs, int decision, char type){
        int i, dx = Math.abs(decision);
        Double[][] p = new Double[states][states];

        for(i = dx; i < states; i++)
            System.arraycopy(probs[i-dx],0, p[i],0, states);

        for(i = 0; i < dx; i++) {
            if(type == 'P') Arrays.fill(p[i], 0.0);
            else Arrays.fill(p[i], (double) 0);
        }

        return p;
    }

    public static Double[][] decisionMakerNegative(Double[][] probs, int decision, char type){
        int i, dx = Math.abs(decision);
        Double[][] p = new Double[states][states];

        for(i = 0; i < states - dx; i++)
            System.arraycopy(probs[i + decision],0, p[i],0, states);

        for(i = states - dx; i < states; i++) {
            if(type == 'P') Arrays.fill(p[i], 0.0);
            else Arrays.fill(p[i], (double) 0);
        }

        return p;
    }

    public static Double[][][][] allDecisions(Double[][] probsB1, Double[][] probsB2, char type){
        Double[][][][] decisions = new Double[DECISIONS][BRANCHES][states][states];

        decisions[0][0] = Arrays.stream(probsB1).map(Double[]::clone).toArray(Double[][]::new);
        decisions[0][1] = Arrays.stream(probsB2).map(Double[]::clone).toArray(Double[][]::new);

        for(int d = 1, i = 1; i < DECISIONS; i+=2, d++){
            /* (+d,-d) */
            decisions[i][0] = decisionMakerPositive(probsB1,d,type);
            decisions[i][1] = decisionMakerNegative(probsB2,d,type);
            /* (-d,+d) */
            decisions[i+1][0] = decisionMakerNegative(probsB1,d,type);
            decisions[i+1][1] = decisionMakerPositive(probsB2,d,type);
        }
        return decisions;
    }

    public static Double[][][] mergeDecisions(Double[][][][] decisions, char type){
        Double[][][] m = new Double[DECISIONS][totalStates][totalStates];

        for(int d = 0; d < DECISIONS; d++)
            m[d] = merge(decisions[d][0],decisions[d][1], type);

        return m;
    }

    public static Double[][] costsProbs(Double[][] probs){
        Double[][] c = new Double[states][states];
        int i,j,k,d,r;
        int minRequest, maxDelivery, minDelivery;
        for(i = 0; i < states; i++){
            for(j = 0; j < states; j++){
                c[i][j] = 0.0;
                maxDelivery = j;
                minDelivery = (i > j) ? 0 : ( j - i );
                minRequest  = (j > i) ? 0 : ( i - j );
                if( j > 8 )
                    c[i][j] -= 10;
                for(d = minDelivery, r = minRequest; d < maxDelivery; d++, r++)
                    if( j == states - 1 )
                        for(k = d; k < states; k++)
                            c[i][j] += (probs[REQUEST][r] * probs[DELIVERY][k]) * (30 * r);
                    else
                        c[i][j] += (probs[REQUEST][r] * probs[DELIVERY][d]) * (30 * r);
                for(int dif = r ; r < states; r++)
                    c[i][j] += (probs[REQUEST][dif] * probs[DELIVERY][d]) * (30 * dif);
            }
        }
        return c;
    }

    public static void transferCost(Double[][][][] costs){
        int c = 0;
        for(int d = 1; d < DECISIONS; d += 2) {
            c++;
            for (int i = 0; i < states; i++)
                for (int j = 0; j < states; j++) {
                    costs[d][1][i][j] -= TRANSFER * c;
                    costs[d + 1][0][i][j] -= TRANSFER * c;
                }
        }
    }
}
