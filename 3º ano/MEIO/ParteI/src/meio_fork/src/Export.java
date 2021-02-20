import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class Export {
    private static final String csvPath = "dump.csv";

    public static void dumpMatricesIntoCSV(Double[][][][] odds, Double[][][][] costs) throws FileNotFoundException {
        DecimalFormat df = new DecimalFormat("0.0000");
        /* Open the file */
        PrintWriter pw = new PrintWriter(new File(csvPath));

        /* Dump stuff there (CSV-style) */
        for(int d = 0; d < 7; d++){
            pw.println("Decisao " + (d+1));
            for(int b = 0; b < 2; b++){
                pw.println("Filial " + (b+1));
                for(int t = 0; t < 2; t++) {
                    if(t == 0) pw.println("Probabilidades");
                    else pw.println("Contribuicoes");
                    for (int i = 0; i < 13; i++) {
                        StringBuilder line = new StringBuilder();
                        for (int j = 0; j < 13; j++) {
                            if(t == 0) line.append(df.format(odds[d][b][i][j])).append(";");
                            else line.append(df.format(costs[d][b][i][j])).append(";");
                        }
                        pw.println(line.toString());
                    }
                }
                pw.println();
            }
            pw.println("\n");
        }

        /* Close file */
        pw.close();
    }
}
