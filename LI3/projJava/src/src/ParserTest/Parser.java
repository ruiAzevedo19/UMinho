package ParserTest;

import Model.Sale;

import java.util.*;

public abstract class Parser {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private String pathToFile;              /* caminho para os ficheiros de leitura                                   */
    private Set<String> clients;            /* clientes do sistema                                                    */
    private Set<String> products;           /* produtos do sistema                                                    */
    private List<Sale> sales;               /* vendas do sistema                                                      */
    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * Método construtor
     */
    public Parser(String pathToFile, Set<String> clients, Set<String> products){
        this.pathToFile = pathToFile;
        this.clients = clients;
        this.products = products;
        sales = new ArrayList<>();
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    /**
     * @return Devolve o caminho para o ficheiro de leitura
     */
    public String getPathToFile() {
        return pathToFile;
    }

    /* --- Funcionalidade ------------------------------------------------------------------------------------------- */

    /**
     * Valida uma linha de venda
     * @param sale venda a validar
     * @return true se for válida, false caso contrário
     */
    public boolean validate(String[] sale){
        return  sale.length == 7
                && products.contains(sale[0])
                && Double.parseDouble(sale[1]) >= 0.0 && Double.parseDouble(sale[1]) <= 999.99
                && Integer.parseInt(sale[2]) > 0 && Integer.parseInt(sale[2]) < 201
                && sale[3].length() == 1 && (sale[3].equals("P") || sale[3].equals("N"))
                && clients.contains(sale[4])
                && Integer.parseInt(sale[5]) > 0 && Integer.parseInt(sale[5]) < 13
                && Integer.parseInt(sale[6]) > 0 && Integer.parseInt(sale[6]) < 4;
    }

    /**
     * Adiciona uma venda à lista de vendas
     * @param sale venda a adicionar
     */
    public void addSale(Sale sale){
        sales.add(sale.clone());
    }

    /**
     * Lê um ficheiro sem parsing nem validação
     */
    public abstract void readFile();

    /**
     * Lê um ficheiro com parsing mas sem validação
     */
    public abstract void readFileParsing();

    /**
     * Lê um ficheiro com parsing e validação
     */
    public abstract void readFileParsingValidation();
}
