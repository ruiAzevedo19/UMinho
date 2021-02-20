package Controller;

public abstract class Messages {
    public static final String help =
            "List of all commands :\n" +
            "help - mostra os menus de ajuda\n" +
            "save - guarda o estado atual do programa\n" +
                    "login - faz login numa conta\n" +
                    "print -d - faz print da login database\n" +
                    "print -ta - faz print das transportadoras disponiveis\n" +
                    "print - faz print ao sgv todo\n" +
            "quit - fecha o programa\n";
    public static final String quit =
            "Obrigado por testar a nossa aplicacao!\n";
    public static final String invalidCommand =
            "O comando que introduziu e invalido, tente de novo...\n";
    public static final String welcome =
            "Bem vindo ao nosso programa\n" +
            "Escreva 'help' para ver os comandos disponiveis.\n";
    public static final String loginTypePrompt =
            "0. utilizador\n" +
            "1. voluntario\n" +
            "2. transportadora\n" +
            "3. loja\n" +
            "Escolha o tipo de login: ";
    public static final String backToMenu =
            "Esta de volta no menu principal.\n";
    public static final String askUsername =
            "Introduza o seu username: \n";
    public static final String askPassword =
            "Introduza a sua password (123 é a password default, altere o mais cedo possivel): \n";
    public static final String loginFailed =
            "O login falhou :(\n";
    public static final String askCommand =
            "Introduza um comando (help se precisar de ajuda): \n";
    public static final String sucessefulLogin =
            "O login funcionou!\n" +
            "Escreva help para ver os comandos disponiveis\n";
    public static final String logOff =
            "Estamos a dar log off na sua conta, vai voltar ao menu principal\n";
    public static final String newPassword =
            "Introduza a nova password: \n";
    public static final String newPasswordConfirmation =
            "Introduza a password de novo: \n";
    public static final String passwordConfirmationInvalid =
            "As passwords que introduziu nao sao iguais.\n";
}
