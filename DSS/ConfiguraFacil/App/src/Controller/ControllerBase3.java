package Controller;

import Model.Class.Componente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerBase3 extends Controller implements Initializable {

    @FXML
    private AnchorPane confBase3;

    @FXML
    private Label preco;

    @FXML
    private Label precoLabel;


    @FXML
    private ToggleGroup estofos, jantes;

    @FXML
    private RadioButton pele, tecido, alcantara, j18, j17, j16;

    @FXML
    private ImageView motorImg;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView logoImg;

    @FXML
    private Button voltarButton;

    @FXML
    private Button nextButton;

    @FXML
    private RadioButton white;

    @FXML
    private ToggleGroup cor;

    @FXML
    private RadioButton cinza;

    @FXML
    private RadioButton azul;

    @FXML
    private RadioButton preto;

    @FXML
    private TextArea confAtualTxt;

    @FXML
    private void selectJantes() throws Exception{
        Componente c = getFacade().getComponente(jantes.getSelectedToggle().getUserData());
        try{
            getFacade().seleccionaComponente(c);
            confAtualTxt.setText(getFacade().getConfig().toString());
        }
        catch (Exception e){
            super.error(e.getMessage());
        }
    }

    @FXML
    private void selectEstofos() throws Exception {
        Componente c = getFacade().getComponente(estofos.getSelectedToggle().getUserData());
        try {
            getFacade().seleccionaComponente(c);
            confAtualTxt.setText(getFacade().getConfig().toString());
        } catch (Exception e) {
            super.error(e.getMessage());
        }
    }

    @FXML
    private void selectCor() throws Exception {
        getFacade().getConfig().setCor(cor.getSelectedToggle().getUserData().toString());
        confAtualTxt.setText(getFacade().getConfig().toString());
    }

    @FXML
    public void setPrice(){
        this.preco.setText(Double.toString(getFacade().getConfig().actualizaPreco()) +" €");
    }



    @FXML
    void backView() {
        launchController("../View/confBase2.fxml");
    }

    @FXML
    void confExtraView(){
        if((estofos.getSelectedToggle() != null) && (jantes.getSelectedToggle() != null) && (cor.getSelectedToggle() != null) )
            launchController("../View/confExtra.fxml");
        else
            super.error("Tem que selecionar um componente por categoria!");
    }

    @Override
    public void launchController(){
        super.launchController();
        confAtualTxt.setText(getFacade().getConfig().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        j16.setUserData("9");
        j17.setUserData("10");
        j18.setUserData("11");
        tecido.setUserData("12");
        pele.setUserData("13");
        alcantara.setUserData("14");
        white.setUserData("Branco");
        azul.setUserData("Azul");
        cinza.setUserData("Cinza");
        preto.setUserData("Preto");
        confAtualTxt.setEditable(false);
        //String t = getFacade().getConfig().toString();
        //confAtualTxt.setText(t);
    }
}
