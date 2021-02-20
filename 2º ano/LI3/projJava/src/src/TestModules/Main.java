package TestModules;

import Model.Answer.QuantProd;
import Model.Client;
import Model.Interface.IClient;
import Model.Interface.IProduct;
import Model.Interface.ISGV;
import Model.Product;
import Model.SGV;
import Utilities.Time;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;


public class Main {
    /* --- Variaveis de instancia ----------------------------------------------------------------------------------- */
    private static int RUNS=5;
    private static double[] time = new double[11];
    private static IProduct product = new Product("AA1001");
    private static IClient client = new Client("F2916");
    private static int month = 2;
    private static int limit = 1000000;
    /* -------------------------------------------------------------------------------------------------------------- */

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        String clientPath = "DB/Clientes.txt";
        String productPath = "DB/Produtos.txt";
      String salesPath = "DB/Vendas_1M.txt";
//      String salesPath = "../../../../gestVendasDB/Vendas_3M.txt";
//        String salesPath = "../../../../gestVendasDB/Vendas_5M.txt";
        ISGV sgv = new SGV();



        for(int i = 0; i < RUNS; i++){

            /* --- Parsing ------------------------------------------------------------------------------------------ */
            System.out.println("Parsing");
            Time.start();
            sgv.loadSGVFromFiles(clientPath,productPath,salesPath);
            time[0] += Time.stop();

            /* --- Query 1 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 1");
            Time.start();
            sgv.getProductsNeverBought();
            time[1] += Time.stop();

            /* --- Query 2 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 2");
            Time.start();
            sgv.getSalesClientsGlobalBranch(month);
            time[2] += Time.stop();

            /* --- Query 3 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 3");
            Time.start();
            sgv.getClientBuyingInfo(client);
            time[3] += Time.stop();

            /* --- Query 4 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 4");
            Time.start();
            sgv.getProductBuyingInfo(product);
            time[4] += Time.stop();

            /* --- Query 5 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 5");
            Time.start();
            sgv.mostPurchasedProducts(client);
            time[5] += Time.stop();

            /* --- Query 6 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 6");
            Time.start();
            Set<QuantProd> r = sgv.mostBoughtProducts(limit);
            time[6] += Time.stop();

            /* --- Query 7 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 7");
            Time.start();
            sgv.greatests3BuyersBraches();
            time[7] += Time.stop();

            /* --- Query 8 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 8");
            Time.start();
            sgv.buyersBoughtMoreProducts(limit);
            time[8] += Time.stop();

            /* --- Query 9 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 9");
            Time.start();
            sgv.clientsMostBoughtProduct(product,limit);
            time[9] += Time.stop();

            /* --- Query 10 ------------------------------------------------------------------------------------------ */
            System.out.println("Query 10");
            Time.start();
            sgv. prodTotalBillings();
            time[10] += Time.stop();
        }

        double[] average = Arrays.stream(time).map(d -> d / RUNS).toArray();
        for(int i = 0; i < 11; i++)
            System.out.println("Query " + i + ": " + average[i]);
    }
}
