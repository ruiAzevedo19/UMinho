package Controller;
import Model.Class.Configuracao;
import Model.Class.Modelo;
import Model.DAOs.ModeloDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerBase1 extends Controller{

    private String modelo;

    @FXML
    private RadioButton car3RadioBt;

    @FXML
    private RadioButton car1RadioBt;

    @FXML
    private RadioButton car2RadioBt;

    @FXML
    private void backView(){
        launchController("../View/Login.fxml");
    }

    @FXML
    void carro3() {
        this.modelo = "3";
    }

    @FXML
    void carro1() {
        this.modelo = "1";
    }

    @FXML
    void carro2() {
        this.modelo = "2";
    }

    @FXML
    private void confBase2View(){
        Modelo mod;
        if( car1RadioBt.isSelected() == false && car2RadioBt.isSelected() == false && car3RadioBt.isSelected() == false ) {
            super.error("Escolha um modelo de carro");
            return;
        }
        else
            mod = new ModeloDAO().get(this.modelo);
        try {
            super.getFacade().getConfig().setModelo(mod);
        } catch (Exception e) {
            super.error(e.getMessage());
        }

        launchController("../View/confBase2.fxml");
    }
}
