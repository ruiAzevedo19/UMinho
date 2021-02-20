public class Calculate {

    public static Policy policy(Double[][][] decisions, Double[][][] costs){
        double decision;

        Double[] Fn = new Double[169];
        Double[] lastFn = new Double[169];
        for(int i = 0; i < 169; lastFn[i++] = 0.0);
        /* Vn for each decision */
        Double[] Vn = new Double[1183];
        /* Pn * Fn-1 for each decision */
        Double[] PnLastFn = new Double[1183];
        /* Qn for each decision */
        Double[] Qn = new Double[1183];
        /* Dn for each decision */
        Double[] Dn = new Double[169];
        /* Tracks the decision */
        Integer[] D = new Integer[169];

        while(true) {
            /* Calculate Qn */
            straightMul(decisions, costs, Qn);
            /* Calculate Pn * Fn-1 */
            mul(decisions, lastFn, PnLastFn);
            /* Calculate Vn */
            sum(Qn, PnLastFn, Vn);
            /* Calculate Fn */
            max(Vn, Fn, D);
            /* Calculate Dn */
            sub(Fn, lastFn, Dn);
            /* Checks if it has converged */
            if (converged(Dn, 0.01)){
                decision = Dn[0];
                break;
            }
            else cpy(lastFn, Fn);
        }

        return new Policy(decision,D);
    }

    public static void straightMul(Double[][][] Pn, Double[][][] Rn, Double[] Qn){
        double sum;
        for(int d = 0; d < 7; d++) {
            for (int i = 0; i < 169; i++) {
                sum = 0.0;
                for (int j = 0; j < 169; j++)
                    sum += Pn[d][i][j] * Rn[d][i][j];
                Qn[169 * d + i] = sum;
            }
        }
    }

    public static void mul(Double[][][] Pn, Double[] lastFn, Double[] PnLastFn){
        double sum;
        int k;
        for(int d = 0; d < 7; d++) {
            for (int i = 0; i < 169; i++) {
                sum = 0.0;
                k = 0;
                for (int j = 0; j < 169; j++, k++)
                    sum += Pn[d][i][j] * lastFn[k];
                PnLastFn[169 * d + i] = sum;
            }
        }
    }

    public static void sum(Double[] Qn, Double[] PnLastFn, Double[] Vn){
        for(int i = 0; i < 1183; i++)
            Vn[i] = Qn[i] + PnLastFn[i];
    }

    public static void sub(Double[] Fn, Double[] lastFn, Double[] Dn){
        for(int i = 0; i < 169; i++)
            Dn[i] = Fn[i] - lastFn[i];
    }

    public static void max(Double[] Vn, Double[] Fn, Integer[] D){
        double max;
        int d;

        for(int i = 0; i < 169; i++){
            max = Double.MIN_VALUE;
            d = -1;
            for(int decision = 0; decision < 7; decision++)
                if( Vn[169 * decision + i] > max ) {
                    max = Vn[169 * decision + i];
                    d = decision;
                }
            Fn[i] = max;
            D[i] = d;
        }
    }

    public static boolean converged(Double[] Dn, double dx){
        boolean conv = true;
        double n = Dn[0];

        for(int i = 1; conv && i < 169; i++)
            if( Math.abs(n - Dn[i]) > dx )
                conv = false;
        return conv;
    }

    public static void cpy(Double[] lastFn, Double[] Fn){
        for(int i = 0; i < 169; i++)
            lastFn[i] = Fn[i];
    }
}
