package Controller;

import Model.Class.Cliente;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerPagamento extends Controller implements Initializable {

    @FXML
    private AnchorPane menuPagamento;

    @FXML
    private TextField nome, morada, contacto, nif;

    @FXML
    private RadioButton visa, master, cheque, numerario;

    @FXML
    private ToggleGroup pag;

    @FXML
    private Button voltarButton;

    @FXML
    public void backView() {
        launchController("../View/menuConfirma.fxml");
    }

    @FXML
    public void pagamentoView() {

        String modelo = super.getFacade().getConfig().getModelo().getCod();
        double preco = super.getFacade().getConfig().getPreco();
        String metodo = (String) pag.getSelectedToggle().getUserData();
        String vendedor = super.getFacade().getUser();
        List<String> componentes = new ArrayList<>();

        if (this.nif.getText().equals("") || this.nome.getText().equals("") || this.contacto.getText().equals("") || this.morada.getText().equals(""))
            super.error("Preencha todos os campos!");
        else {
            System.out.println("\n" + this.nome.getText() + "\n" + this.nif.getText() + "\n" + this.contacto.getText() + "\n" + this.morada.getText());
            getFacade().addCliente(this.nome.getText(),this.nif.getText(),this.contacto.getText(),this.morada.getText());
            System.out.println("\nAdicionou Cliente");
            getFacade().addEncomenda(modelo, preco, metodo, vendedor, this.nif.getText(), componentes);
            System.out.println("\nAdicionou Encomenda");
            getFacade().getConfig().resetConf();
            launchController("../View/confBase1.fxml");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        visa.setUserData("Visa");
        master.setUserData("MasterCard");
        numerario.setUserData("Numerário");
        cheque.setUserData("Cheque");
    }

}
