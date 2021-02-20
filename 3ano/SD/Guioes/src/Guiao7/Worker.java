package Guiao7;
import Guiao7.Bank.Bank;
import Guiao7.Bank.InvalidAccount;
import Guiao7.Bank.Movimento;
import Guiao7.Bank.NotEnoughFunds;

import java.util.List;

public class Worker {
    /** Banco partilhado entre as threads **/
    private Bank bank;

    /**
     * Metodo construtor
     *
     * @param bank : banco partilhado entre as threads
     */
    public Worker(Bank bank){
        this.bank = bank;
    }

    /**
     * Executa o pedido do cliente
     *
     * @param task : pedido a executar
     * @return : resposta para o cliente
     */
    public String execute(String task){
        /** Exemplo : "transfer 0 1 100"  ->  ["transfer", "0", "1", "100"] **/
        String[] parser = task.trim().split("\\s+");
        /** Resposta que irá ser dada ao cliente **/
        String answer = "";

        switch (parser[0]){
            case "create_account" : int id = this.bank.createAccount(Double.parseDouble(parser[1]));
                                    answer = "Conta criada com o id " + id;
                                    break;
            case "close_account"  : try {
                                        this.bank.closeAccount(Integer.parseInt(parser[1]));
                                        answer = "Conta fechada com sucesso!";
                                    }catch (InvalidAccount ia){
                                        answer = ia.getMessage();
                                    }finally {
                                        break;
                                    }
            case "transfer"       : try {
                                       int from = Integer.parseInt(parser[1]);
                                       int to = Integer.parseInt(parser[2]);
                                       double amount = Double.parseDouble(parser[3]);
                                       this.bank.transfer(from, to, amount,null);
                                       answer = "Transferência feita com sucesso";
                                    }catch (InvalidAccount | NotEnoughFunds ia){
                                       answer = ia.getMessage();
                                    } finally {
                                        break;
                                    }
            case "total_balance"  : int[] accounts = new int[parser.length - 1];
                                    for(int i = 1; i < parser.length; i++)
                                        accounts[i - 1] = Integer.parseInt(parser[i]);
                                    answer = Double.toString(this.bank.totalBalance(accounts));
            case "consult"         : try {
                                        answer = Double.toString(this.bank.consult(Integer.parseInt(parser[1])));
                                        break;
                                    }catch (InvalidAccount ia){
                                        answer = ia.getMessage();
                                    }finally {
                                        break;
                                    }
            default               : answer = "Operação inválida!";
        }
        return answer;
    }

    public List<Movimento> executeMoves(int id) throws InvalidAccount {
        return this.bank.movimentos(id);
    }
}
