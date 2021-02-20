package app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class menuStock {

    private TableView table = new TableView();

    public menuStock (Stage primaryStage) {

        primaryStage.setTitle("Admin");
        Group loginRoot = new Group();
        Scene loginScene = new Scene(loginRoot, 1200, 800);

        //Title
        Label titleLabel = new Label("Stock de Material");
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
        TableColumn codCol = new TableColumn("Codigo");
        codCol.setPrefWidth(100);
        TableColumn desigCol = new TableColumn("Designação");
        desigCol.setPrefWidth(300);
        TableColumn qntCol = new TableColumn("Quantidade");
        qntCol.setPrefWidth(130);
        TableColumn dispCol = new TableColumn("Disponibilidade");
        dispCol.setPrefWidth(170);
        TableColumn armCol = new TableColumn("Armazém");
        armCol.setPrefWidth(200);
        TableColumn actionsCol = new TableColumn("Actions");
        actionsCol.setPrefWidth(200);
        table.getColumns().addAll(codCol, desigCol, qntCol, dispCol, armCol, actionsCol);
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
