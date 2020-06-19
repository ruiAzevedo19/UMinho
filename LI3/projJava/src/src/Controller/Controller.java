package Controller;

import Model.Answer.*;
import Model.Client;
import Model.Interface.IProduct;
import Model.Interface.ISGV;
import Model.Product;
import Utilities.Input;
import Utilities.Time;
import View.TableView;
import View.ErrorView;
import View.Interface.ITableView;
import View.Interface.IRequestView;
import View.Interface.IView;
import View.RequestView;

import java.io.IOException;
import java.util.*;

public class Controller implements IController{
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private ISGV model;                         /* modelo do sistema                                                  */
    private final IView view;                   /* vista                                                              */
    private final IView errorView;              /* vista para erros                                                   */
    private final IRequestView requestView;     /* vista para pedidos                                                 */
    private final ITableView tableView;         /* vista para enumeracoes                                             */
    private final IConverter converter;         /* conversores de dados                                               */
    private INavegator navegator;               /* navegador de dados                                                 */
    /* -------------------------------------------------------------------------------------------------------------- */

    public Controller(ISGV model, IView view) {
        this.model = model;
        this.view = view;
        this.errorView = new ErrorView();
        this.requestView = new RequestView();
        this.tableView = new TableView();
        this.converter = new Converter(model.getNrBranches());
    }

    /* --- Main flow ------------------------------------------------------------------------------------------------ */

    public void start() {
        String op = "";
        do {
            requestView.clearScreen();
            view.show();
            op = Input.lerString();
            switch (op) {
                case "0":
                    loadFlow();
                    break;
                case "1":
                    saveFlow();
                    break;
                case "2":
                    q1Flow();
                    break;
                case "3":
                    q2Flow();
                    break;
                case "4":
                    q3Flow();
                    break;
                case "5":
                    q4Flow();
                    break;
                case "6":
                    q5Flow();
                    break;
                case "7":
                    q6Flow();
                    break;
                case "8":
                    q7Flow();
                    break;
                case "9":
                    q8Flow();
                    break;
                case "10":
                    q9Flow();
                    break;
                case "11":
                    q10Flow();
                    break;
                case "12":
                    statsOne();
                    break;
                case "13":
                    statesTwo();
                    break;
                case "S":
                    break;
                default:
                    new ErrorView().show("Opção inválida");
            }
        } while (!op.equals("S") && !op.equals("s"));
    }

    /* --- Utilities ------------------------------------------------------------------------------------------------ */

    private boolean checkLoad(){
        boolean r = true;
        if( !model.isSGVLoaded() ) {
            errorView.show("As estruturas não estão carregadas para serem guardadas");
            r = false;
        }
        return r;
    }

   private void waitAndOff() {
       System.out.print("Prima qualquer tecla para sair\n<$> ");
       Input.lerString();
   }

   private int readInt(int min, int max){
        int r = Input.lerInt();
        if( r < min || r > max )
            r = -1;
        return r;
   }

    private int readInt(int min){
        int r = Input.lerInt();
        if( r < min )
            r = -1;
        return r;
    }
    private void requestData(String queryHeader, String message){
        requestView.clearScreen();
        requestView.printQueryHeader(queryHeader);
        requestView.show(message);
    }

    /* --- Table flows ---------------------------------------------------------------------------------------------- */
    private void listFlow(int query, String[] headers, double time ){
        String op = "";
        boolean isRunning = true;

        while( isRunning ){
            requestView.clearScreen();
            requestView.printQueryHeader("Query " + query);
            requestView.showTime(time);
            tableView.printTable(navegator.getCurrentPage(),navegator.getMaxPage(),navegator.getPageSize(),
                                 navegator.isTotal(),navegator.getSize(),navegator.isHasTitle(),(String)navegator.getTitle(),navegator.getTopHeaders(), navegator.convert());
            op = Input.lerString();
            if( op.equals("s") || op.equals("S"))
                isRunning = false;
            else if(!navegator.getPage(op))
                errorView.show("Input inválido");
        }
    }

    private void tableFlow(int query, double time, String[] sideHeaders, String[] topHeaders, Object[][] data){
        requestView.clearScreen();
        requestView.printQueryHeader("Query " + query);
        requestView.showTime(time);
        tableView.simpleTable(sideHeaders, topHeaders,data);
        waitAndOff();
    }

    /* --- Query 1 -------------------------------------------------------------------------------------------------- */

    private void q1Flow(){
        if( !checkLoad() ) return;
        double time;
        Time.start();
        List<IProduct> p = model.getProductsNeverBought();
        time = Time.stop();

        String[] headers = {"Produtos"};
        navegator = new Navegator(10,headers,converter.q1Converter(p));
        navegator.setTotal();
        listFlow(1,headers,time);
    }

    /* --- Query 2 -------------------------------------------------------------------------------------------------- */

    private void q2Flow(){
        if( !checkLoad() ) return;
        double time;
        boolean isRunning = true;
        int m = 1;
        while(isRunning){
            requestData("Query " + 2,"Introduza um mês [1..12]");
            m = readInt(1,12);
            if(m != -1)
                isRunning = false;
            else
                errorView.show("Mês inválido");
        }
        Time.start();
        SalesClients sc = model.getSalesClientsGlobalBranch(m);
        time = Time.stop();

        String[] topHeaders = {"","Vendas realizadas","Clientes ativos"};
        String[] sideHeaders = new String[1 + model.getNrBranches()];
        sideHeaders[0] = "Global";
        for(int i = 1; i <= model.getNrBranches(); i++)
            sideHeaders[i] = "Filial " + i;

        tableFlow(2,time,sideHeaders,topHeaders,converter.q2Converter(sc));
    }

    /* --- Query 3 -------------------------------------------------------------------------------------------------- */

    public void q3Flow(){
        if( !checkLoad() ) return;
        double time;
        boolean isRunning = true;
        String op = "";
        while( isRunning ){
           requestData("Query " + 3, "Client");
           op = Input.lerString();
           if( op.matches("s|S") )
               return;
           if( model.containsClient(op) )
               isRunning = false;
           else
               errorView.show("O cliente " + op + " não existe");
        }
        Time.start();
        PurchProdsSpent pps = model.getClientBuyingInfo(new Client(op));
        time = Time.stop();

        String[] topHeaders = {"","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
        String[] sideHeaders = {"Nº de compras","Nº produtos comprados", "Dinheiro gasto"};
        tableFlow(3,time,sideHeaders,topHeaders,converter.q3Converter(pps));
    }

    /* --- Query 4 -------------------------------------------------------------------------------------------------- */

    private void q4Flow(){
        if( !checkLoad() ) return;
        double time;
        boolean isRunning = true;
        String op = "";
        while( isRunning ){
            requestData("Query " + 4, "Produto");
            op = Input.lerString();
            if( op.matches("s|S") )
                return;
            if( model.containsProduct(op) )
                isRunning = false;
            else
                errorView.show("O produto " + op + " não existe");
        }
        Time.start();
        PurchClientsBillings pcb = model.getProductBuyingInfo(new Product(op));
        time = Time.stop();

        String[] topHeaders = {"","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
        String[] sideHeaders = {"Nº de compras","Nº de clientes que compraram", "Faturação"};
        tableFlow(4,time,sideHeaders,topHeaders,converter.q4Converter(pcb));
    }

    /* --- Query 5 -------------------------------------------------------------------------------------------------- */

    public void q5Flow(){
        if( !checkLoad() ) return;
        double time;
        boolean isRunning = true;
        String op = "";
        while( isRunning ){
            requestData("Query " + 5, "Client");
            op = Input.lerString();
            if( op.matches("s|S") )
                return;
            if( model.containsClient(op) )
                isRunning = false;
            else
                errorView.show("O cliente " + op + " não existe");
        }
        Time.start();
        Set<QuantProd> mpp = model.mostPurchasedProducts(new Client(op));
        time = Time.stop();

        String[] headers = {"Produto","Quantidade"};
        navegator = new Navegator(10,headers,converter.q5Converter(mpp));
        listFlow(5,headers,time);
    }

    /* --- Query 6 -------------------------------------------------------------------------------------------------- */

    public void q6Flow(){
        if( !checkLoad() ) return;
        double time;
        boolean isRunning = true;
        int l = 1;
        while(isRunning){
            requestData("Query " + 6,"Limite");
            l = readInt(1);
            if(l != -1)
                isRunning = false;
            else
                errorView.show("Limite inválido, introduza um limite positivo");
        }
        Time.start();
        Set<QuantProd> mbp = model.mostBoughtProducts(l);
        time = Time.stop();
        String[] headers = {"Produto","Nº clientes que compraram", "Nº de compras"};
        navegator = new Navegator(10,headers,converter.q6Converter(mbp));
        listFlow(6,headers,time);
    }

    /* --- Query 7 -------------------------------------------------------------------------------------------------- */

    public void q7Flow(){
        if( !checkLoad() ) return;
        double time;

        Time.start();
        List<List<AbstractMap.SimpleEntry<String,Double>>> result = model.greatests3BuyersBraches();
        time = Time.stop();

        String[] topHeaders = {"Cliente (total gasto)","1º","2º","3º"};
        String[] sideHeaders = {"Nº de compras","Nº de clientes que compraram", "Faturação"};
        tableFlow(7,time,sideHeaders,topHeaders,converter.q7Converter(result));
    }

    /* --- Query 8 -------------------------------------------------------------------------------------------------- */
    public void q8Flow(){
        boolean isRunning = true;
        double time;
        String prod = "";
        int l = 1;
        while( isRunning ){
            while(isRunning){
                requestData("Query " + 8,"Limite");
                l = readInt(1);
                if(l != -1)
                    isRunning = false;
                else
                    errorView.show("Limite inválido, tem que ser um número positivo");
            }
        }
        Time.start();
        List<AbstractMap.SimpleEntry<String,Integer>> bbp = model.buyersBoughtMoreProducts(l);
        time = Time.stop();

        String[] headers = {"Cliente","Nº produtos comprados"};
        navegator = new Navegator(10,headers,converter.q8Converter(bbp));
        listFlow(8,headers,time);
    }

    /* --- Query 9 -------------------------------------------------------------------------------------------------- */
    public void q9Flow(){
        boolean isRunning = true;
        double time;
        String prod = "";
        int l = 1;
        while( isRunning ){
            requestView.clearScreen();
            requestView.printQueryHeader("Query " + 9);
            requestView.show("Produto");
            prod = Input.lerString();
            requestView.show("Limite");
            isRunning = false;
            try {
                l = Input.lerInt();
                if( l < 0 ) {
                    errorView.show("O limite tem que ser positivo");
                    isRunning = true;
                }
            }catch (NumberFormatException e){
                errorView.show("Formato inválido");
                isRunning = true;
            }
            if( !model.containsProduct(prod) ) {
                errorView.show("O produto " + prod + " não existe");
                isRunning = true;
            }
        }
        Time.start();
        List<ClientQuantBillings> cqb = model.clientsMostBoughtProduct(new Product(prod),l);
        time = Time.stop();

        String[] headers = {"Cliente","Valor gasto"};
        navegator = new Navegator(10,headers,converter.q9Converter(cqb));
        listFlow(9,headers,time);
    }

    /* --- Query 10 ------------------------------------------------------------------------------------------------- */

    public void q10Flow(){
        if( !checkLoad() ) return;
        double time;

        Time.start();
        Map<String,List<List<Double>>> result = model.prodTotalBillings();
        time = Time.stop();

        String[] topHeaders = {"","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
        String[] sideHeaders = new String[model.getNrBranches()];
        for(int i = 0; i < model.getNrBranches(); i++)
            sideHeaders[i] = "Filial " + (i+1);
        navegator= new Navegator(3,"Produto",topHeaders, sideHeaders,converter.q10Converter(result));
        listFlow(10,topHeaders,time);
    }

    /* --- Loads and writes ----------------------------------------------------------------------------------------- */

    private void loadStateInfo(double time) {
        String op = "";
        requestView.showTime(time);
        requestView.printMessage("Estado referente ao ficheiro: " + model.getLastSaleFile());
        waitAndOff();
    }

    private boolean readDefaultFlow(){
        boolean running = true;
        String client, products, sales;
        String op = "";
        requestView.show("Carregar ficheiros default? [Y/N]");
        op = Input.lerString();
        switch (op) {
            case "Y":
            case "y":
                try {
                    Time.start();
                    model.loadSGVDefault();
                    tableView.showParsingStats(model.getVClients(), model.getIClients(),
                            model.getVProducts(), model.getIProducts(), model.getVSales(), model.getISales(), Time.stop());
                    waitAndOff();
                    running = false;
                } catch (IOException e) {
                    errorView.show("Ficheiros não encontrados");
                }
                break;
            case "N":
            case "n":
                requestView.show("Ficheiro de clientes");
                client = Input.lerString();
                requestView.show("Ficheiro de produtos");
                products = Input.lerString();
                requestView.show("Ficheiro de vendas");
                sales = Input.lerString();
                try {
                    Time.start();
                    model.loadSGVFromFiles(client, products, sales);
                    tableView.showParsingStats(model.getVClients(), model.getIClients(),
                            model.getVProducts(), model.getIProducts(), model.getVSales(), model.getISales(), Time.stop());
                    waitAndOff();
                    running = false;
                } catch (IOException e) {
                    errorView.show("Ficheiros não encontrados");
                }
        }
        return running;
    }

    public boolean readStateFlow(){
        boolean isRunning = true;
        String op = "";
        requestView.show("Pretende ler o ficheiro default? [Y/N]");
        op = Input.lerString();
        switch (op) {
            case "Y":
            case "y":
                try {
                    Time.start();
                    model = model.loadStateDefault();
                    loadStateInfo(Time.stop());
                    isRunning = false;
                } catch (IOException | ClassNotFoundException e) {
                    errorView.show("O estado não chegou a ser guardado, o ficheiro default não existe!");
                }
                break;
            case "N":
            case "n":
                requestView.show("Insira o caminho para o ficheiro");
                op = Input.lerString();
                try {
                    Time.start();
                    model = model.loadState(op);
                    loadStateInfo(Time.stop());
                    isRunning = false;
                } catch (IOException | ClassNotFoundException e) {
                    errorView.show("Erro a carregar o estado, o ficheiro não existe!");
                }
                break;
        }
        return isRunning;
    }

    private void loadFlow() {
        String client, products, sales;
        boolean running = true;
        String op = "";
        while (running) {
            requestView.clearScreen();
            requestView.printQueryHeader("Carregar dados");
            requestView.show("Prima 'E' para carregar estado ou 'L' para ler dos ficheiros");
            op = Input.lerString();
            if (op.equals("S") || op.equals("s"))
                running = false;
            switch (op) {
                case "E":
                case "e": running = readStateFlow();
                          break;
                case "L":
                case "l": running = readDefaultFlow();
                          break;
            }
        }
    }

    private void saveFlow() {
        if( !checkLoad() ) return;
        String op = "";
        requestView.clearScreen();
        requestView.printQueryHeader("Guardar estado");
        requestView.show("Pretende guardar no ficheiro default? [Y/N]");
        op = Input.lerString();
        switch (op) {
            case "Y":
            case "y":
                Time.start();
                model.saveStateDefault();
                requestView.showTime(Time.stop());
                waitAndOff();
                break;
            case "N":
            case "n":
                requestView.show("Introduza o nome do ficheiro");
                op = Input.lerString();
                Time.start();
                model.saveState(op);
                requestView.showTime(Time.stop());
                waitAndOff();
        }
    }
    
    /* --- Stats ---------------------------------------------------------------------------------------------------- */
    private void statsOne(){
        requestView.clearScreen();
        requestView.printQueryHeader("Consulta estatística");
        if( model.isStatLoaded() ) {
            tableView.showStatsFull(model.getStats());
            waitAndOff();
        }
        else
            errorView.show("Nenhum ficheiro lido anteriormente");
    }

    private void statesTwo(){
        requestView.clearScreen();
        requestView.printQueryHeader("Consulta estatística");
        if( model.isSGVLoaded() ){
            tableView.showStats(model.getStats(),model.getNrBranches());
            waitAndOff();
        }
        else
            errorView.show("As estruturas não estão carregadas");
    }
}