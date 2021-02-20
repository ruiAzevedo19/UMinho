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

public class ConfBase3 {

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

    public ConfBase3 (Stage primaryStage) {

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

        createLabel(loginRoot, "Tamanho das Jantes:", 100, 100);
        createLabel(loginRoot, "Interior:", 500, 100);
        final ToggleGroup group = new ToggleGroup();
        Image car1 = new Image(getClass().getResource("../Imagens/jantes.jpeg").toExternalForm());
        ImageView car1_View = new ImageView(car1);
        car1_View.setFitWidth(350);
        car1_View.setFitHeight(200);
        car1_View.setLayoutX(70);
        car1_View.setLayoutY(150);
        car1_View.setSmooth(true);
        car1_View.setCache(true);
        loginRoot.getChildren().add(car1_View);

        Image car2 = new Image(getClass().getResource("../Imagens/interior.jpg").toExternalForm());
        ImageView car2_View = new ImageView(car2);
        car2_View.setFitWidth(300);
        car2_View.setFitHeight(200);
        car2_View.setLayoutX(500);
        car2_View.setLayoutY(160);
        car2_View.setSmooth(true);
        car2_View.setCache(true);
        loginRoot.getChildren().add(car2_View);

        //-----------Selection---------------------------------------------------------------

        final ToggleGroup engineGroup = new ToggleGroup();
        RadioButton j16 = createCheckBox(loginRoot, "Jante 16\" (base)", 100, 400); //desable carro3
        j16.setSelected(true);
        j16.setToggleGroup(engineGroup);
        RadioButton j17 = createCheckBox(loginRoot, "Jante 17\"", 100, 450); //desable carro2 e 3
        j17.setToggleGroup(engineGroup);
        RadioButton j18 = createCheckBox(loginRoot, "Jante 18\"", 100, 500); //desable carro1 e 2 (base no 3)
        j18.setToggleGroup(engineGroup);

        final ToggleGroup gearGroup = new ToggleGroup();
        RadioButton teci = createCheckBox(loginRoot, "Tecido (base)", 550, 400);
        teci.setSelected(true);
        teci.setToggleGroup(gearGroup);
        RadioButton pel = createCheckBox(loginRoot, "Pele", 550, 450);
        pel.setToggleGroup(gearGroup);
        RadioButton alc = createCheckBox(loginRoot, "Alcantara", 550, 500);
        alc.setToggleGroup(gearGroup);

        createLabel(loginRoot, "Cor:", 950, 300);
        final ToggleGroup colorGroup = new ToggleGroup();
        RadioButton cinz = createCheckBox(loginRoot, "Cinza", 950, 350);
        cinz.setSelected(true);
        cinz.setToggleGroup(colorGroup);
        RadioButton branc = createCheckBox(loginRoot, "Branco", 950, 400);
        branc.setToggleGroup(colorGroup);
        RadioButton azu = createCheckBox(loginRoot, "Azul", 950, 450);
        azu.setToggleGroup(colorGroup);
        RadioButton pret = createCheckBox(loginRoot, "Preto", 950, 500);
        pret.setToggleGroup(colorGroup);
        //-----------------------------------------------------------------------------------

        //Sair button
        Button voltarBtn = new Button();
        voltarBtn.setPrefSize(100,30);
        voltarBtn.setLayoutX(400);
        voltarBtn.setLayoutY(700);
        voltarBtn.setText("Voltar");
        voltarBtn.setOnAction( actionEvent -> new ConfBase2(primaryStage));
        loginRoot.getChildren().add(voltarBtn);

        //Guardar button
        Button guardarBtn = new Button();
        guardarBtn.setPrefSize(100,30);
        guardarBtn.setLayoutX(700);
        guardarBtn.setLayoutY(700);
        guardarBtn.setText("Avançar");
        guardarBtn.setOnAction( actionEvent -> new ConfExtra(primaryStage));
        loginRoot.getChildren().add(guardarBtn);

        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
    }

}
