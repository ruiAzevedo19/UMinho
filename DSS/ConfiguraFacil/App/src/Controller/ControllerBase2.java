package Controller;

import Model.Class.Componente;
import Model.Class.Modelo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerBase2 extends Controller  implements Initializable{

    @FXML
    private RadioButton v5, v6, auto, dsg, d16, d20, i20, hi20;

    @FXML
    private ToggleGroup motor, transmissao;

    @FXML
    private TextArea confAtualTxt;

    /** Views -----------------------------------------------------------------------------------------*/

    @Override
    public void launchController(){
        try{
            super.launchController();
            Modelo m = super.getFacade().getConfig().getModelo();
            confAtualTxt.setText(getFacade().getConfig().toString());

            if (m.getCod().equals("1")){
                this.hi20.setDisable(true);
                this.dsg.setDisable(true);
            } else if (m.getCod().equals("2")){
                this.d20.setDisable(true);
                this.hi20.setDisable(true);
                this.v6.setDisable(true);
                this.dsg.setDisable(true);
            } else if (m.getCod().equals("3")){
                this.d16.setDisable(true);
                this.d20.setDisable(true);
                this.i20.setDisable(true);
                this.v5.setDisable(true);
                this.v6.setDisable(true);
                this.auto.setDisable(true);
            }
        }
        catch (Exception e){
            super.error(e.getMessage());
        }
    }

    @FXML
    public void backView(){
        launchController("../View/confBase1.fxml");
    }

    @FXML
    public void confBase3View(){

        System.out.println(super.getFacade().getConfig());
        if((motor.getSelectedToggle() != null) && (transmissao.getSelectedToggle() != null))
                launchController("../View/confBase3.fxml");
        else
            super.error("Tem que selecionar um componente por categoria!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        d16.setUserData("1");
        d20.setUserData("2");
        i20.setUserData("3");
        hi20.setUserData("4");
        v5.setUserData("5");
        v6.setUserData("6");
        auto.setUserData("7");
        dsg.setUserData("8");
        confAtualTxt.setEditable(false);
    }

    /** Seleçao -----------------------------------------------------------------------------------------*/

    @FXML
    private void selectMotor() {
        Componente c = getFacade().getComponente(motor.getSelectedToggle().getUserData());
        try{
            getFacade().seleccionaComponente(c);
            confAtualTxt.setText(getFacade().getConfig().toString());
        }
        catch (Exception e){
            super.error(e.getMessage());
        }
    }

    @FXML
    private void selectTransmissao() {
        Componente c = getFacade().getComponente(transmissao.getSelectedToggle().getUserData());
        try {
            getFacade().seleccionaComponente(c);
            confAtualTxt.setText(getFacade().getConfig().toString());
        } catch (Exception e) {
            super.error(e.getMessage());
        }
    }
}
