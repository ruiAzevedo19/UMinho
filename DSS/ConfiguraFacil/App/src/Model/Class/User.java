package Model.Class;

public class User {

    private String nome;
    private String nif;
    private String username;
    private String password;
    private String contacto;
    private String tipo;

    /** Construtores ------------------------------------------------------------------------------------------------**/

    /**
     * Método construtor não parametrizado
     */
    public User(){
        this.nome = "";
        this.nif  = "";
        this.username = "";
        this.password = "";
        this.contacto = "";
        this.tipo = "";
    }

    /**
     * Método construtor parametrizado
     */
    public User(String nif, String nome, String username, String password, String contacto, String tipo){
        this.nome = nome;
        this.nif  = nif;
        this.username = username;
        this.password = password;
        this.contacto = contacto;
        this.tipo = tipo;
    }

    /**
     * Método construtor por copia
     */
    public User(User u){
        this.nome = u.getNome();
        this.nif  = u.getNif();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.contacto = u.getContacto();
        this.tipo = u.getTipo();
    }

    /**
     * Método clone
     */
    public  User clone(){
        return new User(this);
    }

    /** Getters -----------------------------------------------------------------------------------------------------**/

    public String getNome() {
        return this.nome;
    }

    public String getNif() {
        return this.nif;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getContacto(){
        return this.contacto;
    }

    public String getTipo() {
        return this.tipo;
    }

    /** Setters -----------------------------------------------------------------------------------------------------**/

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContacto(String contacto){
        this.contacto = contacto;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    /** outros metodos ----------------------------------------------------------------------------------------------**/

    /**
     * Comparacao com User
     */
    public boolean equals(Object o){
        if (this == o)
            return true;
        if(this.getClass()!= o.getClass() || o == null)
            return false;
        User u = (User) o;

        return this.nome.equals(u.getNome()) && this.nif.equals(u.getNif()) &&
               this.username.equals(u.getUsername()) && this.password.equals(u.getPassword()) &&
               this.contacto.equals(u.getContacto()) && this.tipo.equals(u.getTipo());
    }
}
