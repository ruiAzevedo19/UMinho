package Controller;

import Model.Class.Componente;
import Model.Class.Configuracao;
import Model.Class.Encomenda;
import Model.Class.Modelo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerConfirma extends Controller {

    @FXML
    private AnchorPane menuConfirma;

    @FXML
    private TextArea confAtualTxt;

    @FXML
    private Label titleLabel;

    @FXML
    private Button confirmaButton;

    @FXML
    private Button voltarButton;

    @FXML
    private ImageView mycar;

    @FXML
    private ImageView logoImg;

    @Override
    public void launchController(){
        try{
            super.launchController();
            Configuracao c = super.getFacade().getConfig();
            Image car1 = new Image(getClass().getResource("../Imagens/car1.jpg").toExternalForm());
            Image car2 = new Image(getClass().getResource("../Imagens/car2.jpg").toExternalForm());
            Image car3 = new Image(getClass().getResource("../Imagens/car3.jpg").toExternalForm());
            if (c.getModelo().getCod().equals("1"))
                mycar.setImage(car1);
            else if (c.getModelo().getCod().equals("2"))
                mycar.setImage(car2);
            else if (c.getModelo().getCod().equals("3"))
                mycar.setImage(car3);
            confAtualTxt.setText(getFacade().getConfig().toString());
        }
        catch (Exception e){
            super.error(e.getMessage());
        }
    }

    @FXML
    void backView() {
        launchController("../View/confExtra.fxml");
    }

    @FXML
    void pagamentoView() {
        launchController("../View/Pagamento.fxml");
    }

}
