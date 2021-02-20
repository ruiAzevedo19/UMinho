package Controller;

import javafx.fxml.FXML;

import java.awt.*;

public class ControllerConsultas extends Controller {

    public void backView() {
        super.launchController("../View/Login.fxml");
    }

    public void encomendasView(){
        super.launchController("../View/encomendas.fxml");
    }

    public void stockView(){
        super.launchController("../View/menuStock.fxml");
    }
}
