package Controller;

import Model.DAOs.ComponenteDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import Model.Class.ConfiguraFacil;
import java.awt.*;

public class ControllerLogin extends Controller{


    @FXML
    TextField userText;
    @FXML
    PasswordField passText;


    public void valida() throws Exception {
        String user = this.userText.getText();
        String pass = this.passText.getText();

        int login = getFacade().login(user,pass);
        switch (login){
            case 1 : launchController("../View/menuAdmin.fxml");
                     getFacade().setUser(user);
                     break;
            case 2 : launchController("../View/consultas.fxml");
                     getFacade().setUser(user);
                     break;
            case 3 : launchController("../View/confBase1.fxml");
                     getFacade().setUser(user);
                     break;
            default : error("Credênciais erradas");
                      userText.setText("");
                      passText.setText("");
                      break;
        }
    }
}
