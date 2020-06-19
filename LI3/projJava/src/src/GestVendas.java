import Controller.IController;
import Controller.Controller;
import Model.Interface.ISGV;
import Model.SGV;
import View.ErrorView;
import View.Interface.IView;
import View.MainMenuView;

public class GestVendas {
    private static ISGV createData(){
        ISGV isgv = new SGV();
        return isgv;
    }

    public static void main(String[] args) {
        IView view;
        ISGV model = createData();

        if( model == null ){
            view = new ErrorView();
            view.show("Erro a carregar o ficheiro de configurações");
            System.exit(-1);
        }
        view = new MainMenuView();
        IController controller = new Controller(model,view);
        controller.start();

        System.exit(0);
    }
}
