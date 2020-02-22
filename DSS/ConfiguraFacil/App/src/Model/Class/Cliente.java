package Model.Class;

public class Cliente{

    private String nome;
    private String nif;
    private String contacto;
    private String morada;

    /**
     * Método construtor não parametrizado
     */
    public Cliente(){
        this.nome = "";
        this.nif  = "";
        this.contacto = "";
        this.morada   = "";
    }

    /**
     * Método construtor parametrizado
     */
    public Cliente(String n, String nif, String c, String m){
        this.nome  = n;
        this.nif   = nif;
        this.contacto = c;
        this.morada   = m;
    }

    /**
     * Construtor por copia
     */
    public Cliente(Cliente c){
        this.nome = c.getNome();
        this.nif  = c.getNif();
        this.contacto = c.getContacto();
        this.morada = c.getMorada();
    }

    /**
     * Método clone
     */
    public Cliente clone(){
        return new Cliente(this);
    }

    /** Getters **/

    public String getNome() {
        return this.nome;
    }

    public String getNif() {
        return this.nif;
    }

    public String getContacto(){
        return this.contacto;
    }

    public String getMorada(){
        return this.morada;
    }

    /** Setters **/

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setContacto(String contacto){
        this.contacto = contacto;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    /** Outros metodos **/

    public boolean equals(Object o){
        if (this == o)
            return true;
        if(this.getClass()!= o.getClass() || o == null)
            return false;
        Cliente c = (Cliente) o;

        return this.nome.equals(c.getNome()) && this.nif.equals(c.getNif()) &&
                this.morada.equals(c.getMorada()) &&
                this.contacto.equals(c.getContacto());
    }
}