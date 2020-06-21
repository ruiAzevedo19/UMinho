import java.text.DecimalFormat;

public class Print {

    public static void printMatrix(Double[][] m, int line, int col){
        DecimalFormat df = new DecimalFormat("0.0000");
        System.out.print("      ");
        for(int i = 0; i < line; i++)
            System.out.print(i + "       ");
        for(int i = 0; i < line; i++){
            System.out.println();
            if(String.valueOf(i).length() == 1)
                System.out.print(i + " | ");
            else
                System.out.print(i + "| ");
            for(int j = 0; j < col; j++)
                System.out.print(df.format(m[i][j]) + "  ");
        }
        System.out.println();
        System.out.println();
    }

    public static void printVector(Integer[] v, int n){
        int index = 0;
        for(int i = 0; i < Odds.states; i++) {
            System.out.print("line " + i + ": ");
            for (int j = 0; j < Odds.states; j++)
                System.out.print("  " + v[index++]);
            System.out.println();
        }
        System.out.println();
    }

    public static void printSumLines(Double[][] mx, int line, int col){
        DecimalFormat df = new DecimalFormat("0.0000");
        double sum;
        for(int i = 0; i < line; i++){
            sum = 0.0;
            for(int j = 0; j < col; j++)
                sum += mx[i][j];
            System.out.println("Line " + i + ": " + df.format(sum));
        }
        System.out.println();
    }
}
