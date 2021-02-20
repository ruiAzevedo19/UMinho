package Controller;

import Model.Class.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.InputEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerNovoFunc extends Controller implements Initializable {

    @FXML
    private TextField nomeTxt, nifTxt, usernameTxt, passwordTxt, contactoTxt;

    @FXML
    private ChoiceBox<String> funcaoBox;


    @FXML
    private Button guardarButton, cancelarButton;

    public void adicionaFuncionario(){
        String nif  = nifTxt.getText();
        String nome = nomeTxt.getText();
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        String contacto = contactoTxt.getText();
        String tipo = funcaoBox.getValue();

        if(nif.equals("") || nome.equals("") || username.equals("") || password.equals("") || contacto.equals("") || tipo.equals(""))
            super.error("Tem que preencher todos os campos");
        else{
            super.getFacade().addUser(nif,nome,username,password,contacto,tipo);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Novo Funcionário");
            alert.setContentText("Funcionário adicionado com sucesso");
            alert.showAndWait();
            launchController("../View/menuAdmin.fxml");
        }
    }

    public void backView(){
        launchController("../View/menuAdmin.fxml");
    }

    public void initialize(URL url, ResourceBundle rb){
        List<String> list = new ArrayList<String>();
        list.add("Admin");
        list.add("FuncionarioLoja");
        list.add("FuncionarioFabril");
        ObservableList obList = FXCollections.observableList(list);
        funcaoBox.getItems().clear();
        funcaoBox.setItems(obList);
    }
}
