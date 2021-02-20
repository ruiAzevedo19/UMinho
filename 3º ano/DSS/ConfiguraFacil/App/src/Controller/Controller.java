package Controller;

import Model.Class.ConfiguraFacil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Controller {

    private ConfiguraFacil facade;
    private Scene scene;
    private Stage stage;

    public void setFacade(ConfiguraFacil facade) {
        this.facade = facade;
    }

    public void setScene(final Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ConfiguraFacil getFacade() {
        return this.facade;
    }

    public Scene getScene(){
        return this.scene;
    }

    public Stage getStage(){
        return this.stage;
    }

    public void launchController(){
        this.stage.setScene(scene);
        this.stage.centerOnScreen();
        this.stage.show();
    }

    public void launchController(String view){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view));
            Parent lista = fxmlLoader.load();
            Controller controller = fxmlLoader.getController();
            controller.setFacade(facade);
            controller.setScene(new Scene(lista));
            controller.setStage(stage);
            controller.launchController();
        }
        catch(Exception e){}
    }

    public void error(String erro){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(erro);
        errorAlert.showAndWait();
    }
}
