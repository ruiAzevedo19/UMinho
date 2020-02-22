package Guiao7.Bank;

public class Movimento {
    private int id;             /** ID do movimento: 0 : credito | 1 : debito **/
    private String descritivo;  /** Descricao da operacao **/
    private double valor;       /** Valor da operacao **/
    private double saldo;       /** Saldo da conta no instante do movimento **/

    /**
     * Metodo construtor
     *
     * @param id
     * @param descritivo
     * @param valor
     * @param saldo
     */
    public Movimento(int id, String descritivo, double valor, double saldo) {
        this.id = id;
        this.descritivo = descritivo;
        this.valor = valor;
        this.saldo = saldo;
    }

    /**
     * @return : representacao de um movimento numa string
     */
    @Override
    public String toString() {
        return "Movimento {" + "ID: " + id +
                             ", Descritivo: " + descritivo +
                             ", Valor: " + valor +
                             ", Saldo: " + saldo + "}";
    }
}
