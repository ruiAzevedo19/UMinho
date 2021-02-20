package Model;

public class User {
    private String username;
    private String password;

    /**
     * Método construtor
     *
     * @param username  : nome do utilizador
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Contrutor por copia
     *
     * @param user
     */
    public User(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    /**
     * Metodo clone
     */
    public User clone(){
        return new User(this);
    }

    /**
     * @return : nome do utilizador
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @return : password do utilizador
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return : dados do utilizador convertidos para uma String
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("User { \n" );
        sb.append("\tUsername: " + this.username + "\n");
        sb.append("\tPassword: " + this.password + "\n");
        sb.append("}");

        return sb.toString();
    }
}
