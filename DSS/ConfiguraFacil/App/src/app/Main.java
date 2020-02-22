package app;

import Controller.ControllerLogin;
import Model.Class.ConfiguraFacil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    ConfiguraFacil facade;

    @Override
    public void start(Stage stage) throws Exception{
        facade = new ConfiguraFacil();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/Login.fxml"));
        Parent lista = fxmlLoader.load();
        ControllerLogin controller = fxmlLoader.getController();
        controller.setFacade(facade);
        controller.setScene(new Scene(lista));
        controller.setStage(stage);
        controller.launchController();
    }

    public static void main(String[] args) {
        Application.launch(args);

    }

}
