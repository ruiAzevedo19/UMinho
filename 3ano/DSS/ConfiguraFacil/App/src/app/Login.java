package app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login {

    public void changeMenu (Stage s, TextField user, TextField pass) {
        if (user.getText().equals("admin") && pass.getText().equals("admin")) {
            new app.menuAdmin(s);
        } else if (user.getText().equals("s") && pass.getText().equals("s")) {
            new app.menuStock(s);
        } else if (user.getText().equals("b") && pass.getText().equals("b")) {
            new app.ConfBase_1(s);
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Utilizador ou Password incorrectos");
            errorAlert.showAndWait();
            user.setText("");
            pass.setText("");
        }
    }

    public Login(Stage primaryStage) {

        primaryStage.setTitle("Login");
        Group loginRoot = new Group();
        Scene loginScene = new Scene(loginRoot, 600, 300);

        Image img = new Image(getClass().getResource("../Imagens/bkgrd.png").toExternalForm());
        ImageView bkImg = new ImageView(img);
        bkImg.setFitWidth(300);
        bkImg.setFitHeight(800);
        bkImg.setLayoutX(150);
        bkImg.setPreserveRatio(true);
        bkImg.setCache(true);
        loginRoot.getChildren().add(bkImg);

        //Title
        Label titleLabel = new Label("Configura Facil");
        titleLabel.setLayoutX(140);
        titleLabel.setLayoutY(30);
        titleLabel.setFont(Font.font(36));
        titleLabel.setStyle("-fx-font-weight: bold");
        loginRoot.getChildren().add(titleLabel);

        //User
        Label userLabel = new Label("Utilizador:");
        userLabel.setLayoutX(130);
        userLabel.setLayoutY(100);
        userLabel.setStyle("-fx-font-weight: bold");
        loginRoot.getChildren().add(userLabel);
        //User Text
        TextField userText = new TextField ();
        userText.setPrefSize(200,30);
        userText.setLayoutX(210);
        userText.setLayoutY(95);
        loginRoot.getChildren().add(userText);

        //Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setLayoutX(130);
        passwordLabel.setLayoutY(150);
        passwordLabel.setStyle("-fx-font-weight: bold");
        loginRoot.getChildren().add(passwordLabel);
        //Pass Text
        PasswordField passField = new PasswordField ();
        passField.setPrefSize(200,30);
        passField.setLayoutX(210);
        passField.setLayoutY(145);
        loginRoot.getChildren().add(passField);

        // Login Button
        Button loginBtn = new Button();
        loginBtn.setPrefSize(100,30);
        loginBtn.setLayoutX(250);
        loginBtn.setLayoutY(200);
        loginBtn.setText("Entrar");

        loginBtn.setOnAction( actionEvent -> changeMenu(primaryStage, userText, passField));
        loginRoot.getChildren().add(loginBtn);
        //
        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
    }

}
