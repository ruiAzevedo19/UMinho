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

public class ConfBase2 {

    public void createLabel (Group g, String n, int posX, int posY) {
        Label label = new Label(n);
        label.setLayoutX(posX);
        label.setLayoutY(posY);
        g.getChildren().add(label);
    }

    public RadioButton createCheckBox (Group g, String n, int posX, int posY) {
        RadioButton box = new RadioButton();
        box.setText(n);
        box.setLayoutX(posX);
        box.setLayoutY(posY);
        g.getChildren().add(box);
        return box;
    }

    public ConfBase2 (Stage primaryStage) {

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
        // Imagens

        createLabel(loginRoot, "Escolha a motorização:", 100, 100);
        createLabel(loginRoot, "Escolha tipo de transmissão:", 500, 100);
        final ToggleGroup group = new ToggleGroup();
        Image car1 = new Image(getClass().getResource("../Imagens/engine.jpeg").toExternalForm());
        ImageView car1_View = new ImageView(car1);
        car1_View.setFitWidth(300);
        car1_View.setFitHeight(200);
        car1_View.setLayoutX(70);
        car1_View.setLayoutY(150);
        car1_View.setSmooth(true);
        car1_View.setCache(true);
        loginRoot.getChildren().add(car1_View);

        Image car2 = new Image(getClass().getResource("../Imagens/gearbox.jpg").toExternalForm());
        ImageView car2_View = new ImageView(car2);
        car2_View.setFitWidth(200);
        car2_View.setFitHeight(150);
        car2_View.setLayoutX(500);
        car2_View.setLayoutY(160);
        car2_View.setSmooth(true);
        car2_View.setCache(true);
        loginRoot.getChildren().add(car2_View);

        //-----------Selection---------------------------------------------------------------

        final ToggleGroup engineGroup = new ToggleGroup();
        RadioButton d16 = createCheckBox(loginRoot, "1.6d (150cv) - diesel (base)", 100, 400); //desable carro3
        d16.setSelected(true);
        d16.setToggleGroup(engineGroup);
        RadioButton d20 = createCheckBox(loginRoot, "2.0d (250cv) - diesel", 100, 450); //desable carro2 e 3
        d20.setToggleGroup(engineGroup);
        RadioButton i20 = createCheckBox(loginRoot, "2.0i (190cv) - gasolina", 100, 500); //desable carro2 e 3
        i20.setToggleGroup(engineGroup);
        RadioButton i20h = createCheckBox(loginRoot, "2.0hi (280cv) - hibrido", 100, 550); // desable carro1
        i20h.setToggleGroup(engineGroup);

        final ToggleGroup gearGroup = new ToggleGroup();
        RadioButton d5 = createCheckBox(loginRoot, "5 Velocidades (base)", 550, 400); //desable carro3
        d5.setSelected(true);
        d5.setToggleGroup(gearGroup);
        RadioButton d6 = createCheckBox(loginRoot, "6 Velocidades", 550, 450); //desable carro2 e 3
        d6.setToggleGroup(gearGroup);
        RadioButton auto = createCheckBox(loginRoot, "Automática", 550, 500); //desable carro2 e 3
        auto.setToggleGroup(gearGroup);
        RadioButton dsg = createCheckBox(loginRoot, "DSG", 550, 550); // desable carro1
        dsg.setToggleGroup(gearGroup);

        //-----------------------------------------------------------------------------------

        //Sair button
        Button voltarBtn = new Button();
        voltarBtn.setPrefSize(100,30);
        voltarBtn.setLayoutX(400);
        voltarBtn.setLayoutY(700);
        voltarBtn.setText("Voltar");
        voltarBtn.setOnAction( actionEvent -> new ConfBase_1(primaryStage));
        loginRoot.getChildren().add(voltarBtn);

        //Guardar button
        Button guardarBtn = new Button();
        guardarBtn.setPrefSize(100,30);
        guardarBtn.setLayoutX(700);
        guardarBtn.setLayoutY(700);
        guardarBtn.setText("Avançar");
        guardarBtn.setOnAction( actionEvent -> new ConfBase3(primaryStage));
        loginRoot.getChildren().add(guardarBtn);

        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
    }
}
