package Resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

public class Configuration implements Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private int clientsRange;         /* Numero de letras diferentes do codigo do cliente                             */
    private int clientsDepth;         /* Numero de letras de um codigo do cliente                                     */
    private int clientsMinRange;      /* Numero minimo da parte numerica do codigo do cliente                         */
    private int clientsMaxRange;      /* Numero maximo da parte numerica do codigo do cliente                         */
    private int productsRange;        /* Numero de letras diferentes do codigo do produto                             */
    private int productsDepth;        /* Numero de letras de um codigo do produto                                     */
    private int productsMinRange;     /* Numero minimo da parte numerica do codigo do produto                         */
    private int productsMaxRange;     /* Numero maximo da parte numerica do codigo do produto                         */
    private int nrBranches;           /* numero de filiais do sistema                                                 */
    private String clientsPath;       /* caminho por omissão do ficheiro de clientes                                  */
    private String productsPath;      /* caminho por omissão do ficheiro de produtos                                  */
    private String salesPath;         /* caminho por omissão do ficheiro de vendas                                    */
    private String objectStreamPath;  /* caminho por omissão do ficheiro de object streams                            */
    private String statsPath;         /* caminho para o ficheiro objecto de estatisticas                              */
    /* -------------------------------------------------------------------------------------------------------------- */

    public Configuration(){
        try {
            readConfFile();
        }catch (IOException e){
            e.getMessage();
        }
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int getClientsRange() {
        return clientsRange;
    }

    public int getClientsDepth() {
        return clientsDepth;
    }

    public int getClientsMinRange() {
        return clientsMinRange;
    }

    public int getClientsMaxRange() {
        return clientsMaxRange;
    }

    public int getProductsRange() {
        return productsRange;
    }

    public int getProductsDepth() {
        return productsDepth;
    }

    public int getProductsMinRange() {
        return productsMinRange;
    }

    public int getProductsMaxRange() {
        return productsMaxRange;
    }

    public int getNrBranches() {
        return nrBranches;
    }

    public String getClientsPath() {
        return clientsPath;
    }

    public String getProductsPath() {
        return productsPath;
    }

    public String getSalesPath() {
        return salesPath;
    }

    public String getObjectStreamPath() {
        return objectStreamPath;
    }

    public String getStatsPath() {
        return statsPath;
    }

    /* --- Funcionalidade ------------------------------------------------------------------------------------------- */

    public void readConfFile() throws IOException {
        InputStream inputStream = null;
        try{
            Properties prop = new Properties();
            String propFileName = "Resources/config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if(inputStream != null)
                prop.load(inputStream);
            else
                throw new FileNotFoundException("Property file " + propFileName + " doesn't exists!");

            this.clientsRange = Integer.parseInt(prop.getProperty("clientsRange"));
            this.clientsDepth = Integer.parseInt(prop.getProperty("clientsDepth"));
            this.clientsMinRange = Integer.parseInt(prop.getProperty("clientsMinRange"));
            this.clientsMaxRange = Integer.parseInt(prop.getProperty("clientsMaxRange"));
            this.productsRange = Integer.parseInt(prop.getProperty("productsRange"));
            this.productsDepth = Integer.parseInt(prop.getProperty("productsDepth"));
            this.productsMinRange = Integer.parseInt(prop.getProperty("productsMinRange"));
            this.productsMaxRange = Integer.parseInt(prop.getProperty("productsMaxRange"));
            this.nrBranches = Integer.parseInt(prop.getProperty("branches"));
            this.clientsPath = prop.getProperty("clientsPath");
            this.productsPath = prop.getProperty("productsPath");
            this.salesPath = prop.getProperty("salesPath");
            this.objectStreamPath = prop.getProperty("objectStreamPath");
            this.statsPath = prop.getProperty("statsPath");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert inputStream != null;
            inputStream.close();
        }
    }
}
