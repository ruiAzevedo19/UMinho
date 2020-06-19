package View;

import Model.Stats;
import View.Interface.ITableView;
import com.jakewharton.fliptables.FlipTableConverters;

import java.text.DecimalFormat;

public class TableView implements ITableView {

    public void showParsingStats(int vCli, int iCli, int vProd, int iProd, int vSale, int iSale, double time){
        System.out.println();
        System.out.println("Tempo de carregamento : " + time + "s");
        String[] headers = {"", "Válidos", "Inválidos"};
        Object[][] data = {{"Clientes",vCli,iCli},{"Produtos",vProd,iProd},{"Vendas",vSale,iSale}};
        System.out.println(FlipTableConverters.fromObjects(headers,data));
    }

    public void showStatsFull(Stats s){
        String[] headers = {"","Valores"};
        Object[][] data = new Object[10][2];
        data[0] = new Object[]{"Último ficheiro de vendas lido", s.getLastFile()};
        data[1] = new Object[]{"Registos de vendas errados", s.getWrongSales()};
        data[2] = new Object[]{"Produtos", s.getProducts()};
        data[3] = new Object[]{"Produtos comprados", s.getTotalProductsBought()};
        data[4] = new Object[]{"Produtos não comprados", s.getTotalProductsNotBought()};
        data[5] = new Object[]{"Clientes", s.getClients()};
        data[6] = new Object[]{"Clientes ativos", s.getTotalBuyingClients()};
        data[7] = new Object[]{"Clientes não ativos", s.getTotalClientsNotBuying()};
        data[8] = new Object[]{"Compras com valor total igual a 0", s.getTotalSalesNull()};
        data[9] = new Object[]{"Faturação total", s.getTotalBilling()};

        System.out.println(FlipTableConverters.fromObjects(headers,data));
    }

    public void showStats(Stats s, int nrBranches){
        DecimalFormat df = new DecimalFormat("#.##");
        String[] headers = {"","Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
        Object[][] data = new Object[1][13];
        data[0][0] = "Número total de compras";
        int[] sales = s.getTotalSalesMonth();
        for(int i = 1; i < 13; i++)
            data[0][i] = sales[i-1];
        System.out.println("<$> Compras");
        System.out.println(FlipTableConverters.fromObjects(headers,data));
        System.out.println("<$> Faturação global: " + s.totalBilling());
        double[][] b = s.getTotalBillingMonth();
        data = new Object[nrBranches][13];
        for(int i = 0; i < nrBranches; i++){
            data[i][0] = "Filial " + (i + 1);
            for(int j = 1; j < 13; j++)
                data[i][j] = df.format(b[i][j-1]);
        }
        System.out.println(FlipTableConverters.fromObjects(headers,data));
        System.out.println("<$> Clientes ativos");
        int[][] c = s.getClientsBuyingMonth();
        for(int i = 0; i < nrBranches; i++){
            data[i][0] = "Filial " + (i + 1);
            for(int j = 1; j < 13; j++)
                data[i][j] = c[i][j-1];
        }
        System.out.println(FlipTableConverters.fromObjects(headers,data));
        System.out.println();
    }

    public void showMonthInfo(Object[][] data){
        String[] headers = {"","Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
        System.out.println(FlipTableConverters.fromObjects(headers,data));
    }

    public void simpleTable(String[] headers, Object[][] data){
        System.out.println(FlipTableConverters.fromObjects(headers,data));
    }

    public void simpleTable(String[] sideHeaders, String[] topHeaders, Object[][] data){
        Object[][] d = new Object[data.length][topHeaders.length];

        for(int i = 0; i < data.length; i++){
            d[i][0] = sideHeaders[i];
            for(int j = 1; j <= data[i].length; j++)
                d[i][j] = data[i][j-1];
        }
        System.out.println(FlipTableConverters.fromObjects(topHeaders,d));
    }

    public void printTable(int currentPage, int maxPage, int pageSize, boolean total, int totalSize, boolean hasTitle, String title, String[] topHeaders, Object[][] data ){
        int normPage = Math.max(0, Math.min(currentPage,maxPage));
        currentPage = normPage;
        int dx = (normPage - 1) * pageSize;
        if( total )
            System.out.println("Total : " + totalSize);
        if( hasTitle )
            System.out.println(title);
        System.out.println(FlipTableConverters.fromObjects(topHeaders,data));;
        System.out.println();
        System.out.println("(A) Página anterior | (D) Página seguinte | (/campo:valor) Procura por elemento | (número) Número da página");
        System.out.println("Página " + normPage + "/" + maxPage);
        System.out.print("<$> ");
    }
}
