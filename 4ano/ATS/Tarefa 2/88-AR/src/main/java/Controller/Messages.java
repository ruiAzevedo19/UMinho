package controller;

public abstract class Messages {

    private Messages(){

    }

    public static final String HELP =
            "List of all commands :\n" +
            "help - mostra os menus de ajuda\n" +
            "save - guarda o estado atual do programa\n" +
                    "login - faz login numa conta\n" +
                    "print -d - faz print da login database\n" +
                    "print -ta - faz print das transportadoras disponiveis\n" +
                    "print - faz print ao sgv todo\n" +
            "quit - fecha o programa\n";
    public static final String QUIT =
            "Obrigado por testar a nossa aplicacao!\n";
    public static final String INVALIDCOMMAND =
            "O comando que introduziu e invalido, tente de novo...\n";
    public static final String WELCOME =
            "Bem vindo ao nosso programa\n" +
            "Escreva 'help' para ver os comandos disponiveis.\n";
    public static final String LOGINTYPEPROMPT =
            "0. utilizador\n" +
            "1. voluntario\n" +
            "2. transportadora\n" +
            "3. loja\n" +
            "Escolha o tipo de login: ";
    public static final String BACKTOMENU  =
            "Esta de volta no menu principal.\n";
    public static final String ASKUSERNAME  =
            "Introduza o seu username: \n";
    public static final String ASKPASSWORD  =
            "Introduza a sua password (123 é a password default, altere o mais cedo possivel): \n";
    public static final String LOGINFAILED  =
            "O login falhou :(\n";
    public static final String ASKCOMMAND  =
            "Introduza um comando (help se precisar de ajuda): \n";
    public static final String SUCESSEFULLOGIN  =
            "O login funcionou!\n" +
            "Escreva help para ver os comandos disponiveis\n";
    public static final String LOGOFF  =
            "Estamos a dar log off na sua conta, vai voltar ao menu principal\n";
    public static final String NEWPASSWORD  =
            "Introduza a nova password: \n";
    public static final String NEWPASSWORDCONFIRMATION  =
            "Introduza a password de novo: \n";
    public static final String PASSWORDCONFIRMATIONINVALID  =
            "As passwords que introduziu nao sao iguais.\n";
}
