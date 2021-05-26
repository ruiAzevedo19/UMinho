package creational.factoryMethod;

public class Client {
    private static Dialog dialog;
    private static String os = "mac";

    static void configure(){
        if( os.equals("windows") )
            dialog = new WindowsDialog();
        else
            dialog = new HtmlDialog();
    }

    static void runLogic(){
        dialog.renderWindow();
    }

    public static void main(String[] args) {
        configure();
        runLogic();
    }
}
