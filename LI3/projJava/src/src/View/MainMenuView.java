package View;

import View.Interface.IView;

public class MainMenuView implements IView {

    public void clearScreen(){

    }
    public void show(){
        System.out.println("\n -------------------------------------------------------------------------------------");
        System.out.println("|\t\t\t   _____          _          __      __            _                      ");
        System.out.println("|\t\t\t  / ____|        | |         \\ \\    / /           | |                     ");
        System.out.println("|\t\t\t | |  __  ___ ___| |_ __ _  __\\ \\  / ___ _ __   __| | __ _ ___            ");
        System.out.println("|\t\t\t | | |_ |/ _ / __| __/ _` |/ _ \\ \\/ / _ | '_ \\ / _` |/ _` / __|           ");
        System.out.println("|\t\t\t | |__| |  __\\__ | || (_| | (_) \\  |  __| | | | (_| | (_| \\__ \\           ");
        System.out.println("|\t\t\t  \\_____|\\___|___/\\__\\__,_|\\___/ \\/ \\___|_| |_|\\__,_|\\__,_|___/           ");
        System.out.println("|                                                                                    ");
        System.out.println("|-------------------------------------------------------------------------------------|");
        System.out.println("                                                                                     ");
        System.out.println("\t0  - Carregar dados                                                               ");
        System.out.println("\t1  - Guardar estado                                                               ");
        System.out.println("\t2  - Códigos dos produtos nunca comprados                                         ");
        System.out.println("\t3  - Número total de vendas  e número de clientes distintos (Global e por Filial  ");
        System.out.println("\t4  - Número de compras e produtos comprados por um determinado cliente            ");
        System.out.println("\t5  - Número de vezes que um determinado produto foi comprados                     ");
        System.out.println("\t6  - Produto mais comprados por um determinado cliente                            ");
        System.out.println("\t7  - Produtos mais vendidos em todo o ano                                         ");
        System.out.println("\t8  - Top 3 compradores                                                            ");
        System.out.println("\t9  - Clientes que compraram mais produtos diferentes                              ");
        System.out.println("\t10 - Clientes que mais compraram um determinado produto                           ");
        System.out.println("\t11 - Faturação total de um produto                                                ");
        System.out.println("\t12 - Consulta estatística do último ficheiro de vendas lido                       ");
        System.out.println("\t13 - Consulta estatística do estado atual do sistema                              ");
        System.out.println("                                                                                     ");
        System.out.println("S. Sair       ");
        System.out.println("                                                                                     ");
        System.out.println(" -------------------------------------------------------------------------------------");
        System.out.print("<$> ");
    }

    public void show(Object o){

    }
}
