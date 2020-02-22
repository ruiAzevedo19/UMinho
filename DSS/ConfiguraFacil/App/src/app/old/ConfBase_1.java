package app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfBase_1 {

    public RadioButton createCheckBox (Group g, String n, int posX, int posY) {
        RadioButton box = new RadioButton();
        box.setText(n);
        box.setLayoutX(posX);
        box.setLayoutY(posY);
        g.getChildren().add(box);
        return box;
    }

    public ConfBase_1 (Stage primaryStage) {

        primaryStage.setTitle("Configura Facil");
        Group loginRoot = new Group();
        Scene loginScene = new Scene(loginRoot, 1200, 800);

        //Title
        Label titleLabel = new Label("Configura Facil");
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

        //---------------------------------------------------------------------------------
            // Carros

        final ToggleGroup group = new ToggleGroup();
        Image car1 = new Image(getClass().getResource("../Imagens/car1.jpg").toExternalForm());
        ImageView car1_View = new ImageView(car1);
        car1_View.setFitWidth(300);
        car1_View.setFitHeight(200);
        car1_View.setLayoutX(100);
        car1_View.setLayoutY(200);
        car1_View.setSmooth(true);
        car1_View.setCache(true);
        loginRoot.getChildren().add(car1_View);
        RadioButton car1_Sel = createCheckBox(loginRoot, "50.000 €", 200, 450);
        car1_Sel.setToggleGroup(group);

        Image car2 = new Image(getClass().getResource("../Imagens/car2.jpg").toExternalForm());
        ImageView car2_View = new ImageView(car2);
        car2_View.setFitWidth(300);
        car2_View.setFitHeight(200);
        car2_View.setLayoutX(430);
        car2_View.setLayoutY(200);
        car2_View.setSmooth(true);
        car2_View.setCache(true);
        loginRoot.getChildren().add(car2_View);
        RadioButton car2_Sel = createCheckBox(loginRoot, "30.000 €", 530, 450);
        car2_Sel.setSelected(true);
        car2_Sel.setToggleGroup(group);

        Image car3 = new Image(getClass().getResource("../Imagens/car3.jpg").toExternalForm());
        ImageView car3_View = new ImageView(car3);
        car3_View.setFitWidth(300);
        car3_View.setFitHeight(200);
        car3_View.setLayoutX(760);
        car3_View.setLayoutY(200);
        car3_View.setSmooth(true);
        car3_View.setCache(true);
        loginRoot.getChildren().add(car3_View);
        RadioButton car3_Sel = createCheckBox(loginRoot, "150.000 €", 860, 450);
        car3_Sel.setToggleGroup(group);

       //-----------------------------------------------------------------------------------

        //Sair button
        Button voltarBtn = new Button();
        voltarBtn.setPrefSize(100,30);
        voltarBtn.setLayoutX(400);
        voltarBtn.setLayoutY(700);
        voltarBtn.setText("Sair");
        voltarBtn.setOnAction( actionEvent -> new Login(primaryStage));
        loginRoot.getChildren().add(voltarBtn);

        //Guardar button
        Button guardarBtn = new Button();
        guardarBtn.setPrefSize(100,30);
        guardarBtn.setLayoutX(700);
        guardarBtn.setLayoutY(700);
        guardarBtn.setText("Avançar");
        guardarBtn.setOnAction( actionEvent -> new ConfBase2(primaryStage));
        loginRoot.getChildren().add(guardarBtn);

        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
    }
}
