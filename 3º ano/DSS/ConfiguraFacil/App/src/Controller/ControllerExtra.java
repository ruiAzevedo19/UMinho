package Controller;

import Model.Class.Componente;
import Model.Class.Pacote;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerExtra extends Controller implements Initializable {

   @FXML
   private TextArea confAtualTxt;

   @FXML
   private CheckBox iluminaPack, invernoPack, confortPack, sportPack;

   @FXML
   private CheckBox comp15, comp16, comp17, comp18, comp19, comp20, comp21, comp22, comp23, comp24;

   @FXML
   private CheckBox comp25, comp26, comp27, comp28, comp29, comp30, comp31, comp32, comp33, comp34;

   @FXML
   private CheckBox comp35, comp36, comp37, comp38, comp39, comp40, comp41, comp42, comp43;

    @Override
    public void launchController(){
        super.launchController();
        confAtualTxt.setText(getFacade().getConfig().toString());
    }

    @FXML
    public void backView() {
        launchController("../View/confBase3.fxml");
    }

    @FXML
    public void confirmaView() {
        launchController("../View/menuConfirma.fxml");
    }


    public void seleciona(){
        String message = getFacade().selecionaCompExtra("31");
        if(message.equals("") == false)
            super.error(message);
    }

    @FXML
    private void selectPack (ActionEvent event) {
        CheckBox cb = (CheckBox) event.getSource();
        String c = (String) cb.getUserData();
        if(cb.isSelected()) {
            try {
                getFacade().seleccionaPacote(c);
                confAtualTxt.setText(getFacade().getConfig().toString());
            } catch (Exception e) {
                cb.setSelected(false);
                super.error(e.getLocalizedMessage());
            }
        }
        else{
            getFacade().removePacote(c);
            confAtualTxt.setText(getFacade().getConfig().toString());
        }
    }

    @FXML
    private void selectExtra(ActionEvent event) {
        CheckBox cb = (CheckBox) event.getSource();
        Componente c = getFacade().getComponente(cb.getUserData());
        if(cb.isSelected()) {
            try {
                getFacade().seleccionaComponente(c);
                getFacade().getConfig().addComponente(c);
                String s = getFacade().matchPacote(getFacade().getConfig().getComponentes());
                if (!s.isEmpty())error(s);
                confAtualTxt.setText(getFacade().getConfig().toString());
            } catch (Exception e) {
                cb.setSelected(false);
                super.error(e.getLocalizedMessage());
            }
        }
        else{
                getFacade().getConfig().removeComponente(c.getCodigo());
                confAtualTxt.setText(getFacade().getConfig().toString());
        }
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        confAtualTxt.setEditable(false);
        iluminaPack.setUserData("1");
        invernoPack.setUserData("2");
        confortPack.setUserData("3");
        sportPack.setUserData("4");
        comp15.setUserData("15");
        comp16.setUserData("16");
        comp17.setUserData("17");
        comp18.setUserData("18");
        comp19.setUserData("19");
        comp20.setUserData("20");
        comp21.setUserData("21");
        comp22.setUserData("22");
        comp23.setUserData("23");
        comp24.setUserData("24");
        comp25.setUserData("25");
        comp26.setUserData("26");
        comp27.setUserData("27");
        comp28.setUserData("28");
        comp29.setUserData("29");
        comp30.setUserData("30");
        comp31.setUserData("31");
        comp32.setUserData("32");
        comp33.setUserData("33");
        comp34.setUserData("34");
        comp35.setUserData("35");
        comp36.setUserData("36");
        comp37.setUserData("37");
        comp38.setUserData("38");
        comp39.setUserData("39");
        comp40.setUserData("40");
        comp41.setUserData("41");
        comp42.setUserData("42");
        comp43.setUserData("43");
    }
}
