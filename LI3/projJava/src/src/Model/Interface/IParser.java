package Model.Interface;

import Model.SGV;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface IParser extends Serializable {

    /**
     * Le e carrega os clientes do sistema
     * @param sgv estrutura geral do sistema
     * @param pathToFile caminho para o ficheiro de clientes
     */
    void readClients(SGV sgv, String pathToFile) throws IOException;

    /**
     * Le e carrega os produtos do sistema
     * @param sgv estrutura geral do sistema
     * @param pathToFile caminho para o ficheiro de produtos
     */
    void readProducts(SGV sgv, String pathToFile) throws IOException;

    /**
     * Le e carrega os dados relativos as vendas
     * @param sgv estrutura geral do sistema
     * @param pathToFile caminho para o ficheiro de produtos
     */
    void readSales(SGV sgv, String pathToFile) throws IOException;

    /**
     * Verifica se uma venda esta bem formada
     * @param sgv estrutura geral do sistema
     * @param sale venda a verificar
     * @return true se e valida, false caso contrario
     */
    boolean validate(SGV sgv, String[] sale);
}
