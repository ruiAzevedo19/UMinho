package Model;

public class Sale {

    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private Product product;    /* KR1593 (example)                                                                   */
    private Client client;      /* L4891  (example)                                                                   */
    private float price;        /* [0.0,999.99]                                                                       */
    private int units;          /* [1,200]                                                                            */
    private String mode;        /* N = normal mode || P = promotion mode                                              */
    private int month;          /* [0,11]                                                                             */
    private int branch;         /* [1,3]                                                                              */
    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * Método construtor parametrizado
     */
    public Sale(String prodCod, String cliCod, float price, int units, String mode, int month, int branch) {
        this.product = new Product(prodCod);
        this.client = new Client(cliCod);
        this.price = price;
        this.units = units;
        this.mode = mode;
        this.month = month;
        this.branch = branch;
    }

    /**
     * Construtor por cópia
     * @param s : estrutura a copiar
     */
    public Sale(Sale s) {
        this.product = s.getProduct();
        this.client = s.getClient();
        this.price = s.getPrice();
        this.units = s.getUnits();
        this.mode = s.getMode();
        this.month = s.getMonth();
        this.branch = s.getBranch();
    }

    /**
     * Método clone
     * @return venda clonada
     */
    public Sale clone(){
        return new Sale(this);
    }

    /* --- Getters ---------------------------------------------------------------------------------------------------*/

    public Product getProduct() {
        return product.clone();
    }

    public Client getClient() {
        return client.clone();
    }

    public float getPrice() {
        return price;
    }

    public int getUnits() {
        return units;
    }

    public String getMode() {
        return mode;
    }

    public int getMonth() {
        return month;
    }

    public int getBranch() {
        return branch;
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setPrice(float price) {
        this.price = price;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    /* --- Funcionality --------------------------------------------------------------------------------------------- */

    @Override
    public String toString() {
        return "Sale{" +
                "prodCod='" + product.getCode() + '\'' +
                ", cliCod='" + client.getCode() + '\'' +
                ", price=" + price +
                ", units=" + units +
                ", mode='" + mode + '\'' +
                ", month=" + month +
                ", branch=" + branch +
                '}';
    }
}
