package app;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class menuAdmin {

    private TableView table = new TableView();

    public menuAdmin (Stage primaryStage) {

        primaryStage.setTitle("Admin");
        Group loginRoot = new Group();
        Scene loginScene = new Scene(loginRoot, 1200, 800);

        //Title
        Label titleLabel = new Label("Lista de Funcionários");
        titleLabel.setLayoutX(50);
        titleLabel.setLayoutY(10);
        titleLabel.setFont(Font.font(36));
        titleLabel.setStyle("-fx-font-weight: bold");
        loginRoot.getChildren().add(titleLabel);

        //Logo
        Image img = new Image(getClass().getResource("../Imagens/bkgrd.png").toExternalForm());
        ImageView bkImg = new ImageView(img);
        bkImg.setFitWidth(100);
        bkImg.setFitHeight(100);
        bkImg.setLayoutX(1050);
        bkImg.setLayoutY(30);
        bkImg.setSmooth(true);
        bkImg.setCache(true);
        loginRoot.getChildren().add(bkImg);

        //Table
        table.setEditable(true);
        table.setPrefSize(1100, 600);
        table.setLayoutY(75);
        table.setLayoutX(50);
        TableColumn nomeCol = new TableColumn("Nome");
        nomeCol.setPrefWidth(250);
        TableColumn moradaCol = new TableColumn("Morada");
        moradaCol.setPrefWidth(250);
        TableColumn idadeCol = new TableColumn("Idade");
        idadeCol.setPrefWidth(100);
        TableColumn activoCol = new TableColumn("Activo");
        activoCol.setPrefWidth(100);
        TableColumn localTrCol = new TableColumn("Local de Trabalho");
        localTrCol.setPrefWidth(200);
        TableColumn actionsCol = new TableColumn("Actions");
        actionsCol.setPrefWidth(200);
        table.getColumns().addAll(nomeCol, moradaCol, idadeCol, activoCol, localTrCol, actionsCol);
        loginRoot.getChildren().add(table);

        //Sair button
        Button voltarBtn = new Button();
        voltarBtn.setPrefSize(100,30);
        voltarBtn.setLayoutX(700);
        voltarBtn.setLayoutY(700);
        voltarBtn.setText("Sair");
        voltarBtn.setOnAction( actionEvent -> new Login(primaryStage));
        loginRoot.getChildren().add(voltarBtn);

        //Guardar button
        Button guardarBtn = new Button();
        guardarBtn.setPrefSize(100,30);
        guardarBtn.setLayoutX(400);
        guardarBtn.setLayoutY(700);
        guardarBtn.setText("Guardar");
        //guardarBtn.setOnAction( actionEvent -> changeMenu(primaryStage, userText, passField));
        loginRoot.getChildren().add(guardarBtn);

        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
    }

}
