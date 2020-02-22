package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class menuConfEncomenda {

    public menuConfEncomenda (Stage primaryStage) {

        primaryStage.setTitle("Admin");
        Group loginRoot = new Group();
        Scene loginScene = new Scene(loginRoot, 1200, 800);

        //Title
        Label titleLabel = new Label("O seu carro");
        titleLabel.setLayoutX(50);
        titleLabel.setLayoutY(30);
        titleLabel.setFont(Font.font(36));
        titleLabel.setStyle("-fx-font-weight: bold");
        loginRoot.getChildren().add(titleLabel);

        // Logo
        Image img = new Image(getClass().getResource("../Imagens/bkgrd.png").toExternalForm());
        ImageView bkImg = new ImageView(img);
        bkImg.setFitWidth(100);
        bkImg.setFitHeight(100);
        bkImg.setLayoutX(1050);
        bkImg.setLayoutY(30);
        bkImg.setSmooth(true);
        bkImg.setCache(true);
        loginRoot.getChildren().add(bkImg);

        //---------------------------------------------------------------------------------
                // Carro Escolhido
        Image ca1_spt = new Image(getClass().getResource("../Imagens/car1_spt.jpg").toExternalForm());
        ImageView ca1_spt_View = new ImageView(ca1_spt);
        ca1_spt_View.setFitWidth(500);
        ca1_spt_View.setFitHeight(350);
        ca1_spt_View.setLayoutX(100);
        ca1_spt_View.setLayoutY(200);
        ca1_spt_View.setSmooth(true);
        ca1_spt_View.setCache(true);
        loginRoot.getChildren().add(ca1_spt_View);

                // Lista componentes

        ListView compList = new ListView();
        compList.setEditable(false);
        compList.setMouseTransparent(true);
        compList.setFocusTraversable(false);
        compList.setPrefSize(400, 500);
        compList.setLayoutX(650);
        compList.setLayoutY(100);
        loginRoot.getChildren().add(compList);
        ObservableList<String> comp = FXCollections.observableArrayList ("Lista de componentes");
        compList.setItems(comp);

        //---------------------------------------------------------------------------------
        //Sair button
        Button voltarBtn = new Button();
        voltarBtn.setPrefSize(100,30);
        voltarBtn.setLayoutX(400);
        voltarBtn.setLayoutY(700);
        voltarBtn.setText("Voltar");
        voltarBtn.setOnAction( actionEvent -> new ConfBase3(primaryStage));
        loginRoot.getChildren().add(voltarBtn);

        //Guardar button
        Button guardarBtn = new Button();
        guardarBtn.setPrefSize(200,30);
        guardarBtn.setLayoutX(700);
        guardarBtn.setLayoutY(700);
        guardarBtn.setText("Confirmar Pedido");
        guardarBtn.setOnAction( actionEvent -> new menuPagamentos(primaryStage));
        loginRoot.getChildren().add(guardarBtn);

        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
    }

}
