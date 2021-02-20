package app;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfExtra {


    public void createLabel (Group g, String n, int posX, int posY) {
        Label label = new Label(n);
        label.setLayoutX(posX);
        label.setLayoutY(posY);
        g.getChildren().add(label);
    }

    public CheckBox createCheckBox (Group g, String n, int posX, int posY) {
        CheckBox box = new CheckBox();
        box.setText(n);
        box.setLayoutX(posX);
        box.setLayoutY(posY);
        g.getChildren().add(box);
        return box;
    }

    public ConfExtra (Stage primaryStage) {

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

        createLabel(loginRoot, "Pacotes:", 100, 100);
        createLabel(loginRoot, "Extras:", 500, 100);


        CheckBox Lightpkg = createCheckBox(loginRoot, "Pacote de Iluminação (+400€)", 100, 150);
        Tooltip lpkgToolTip = new Tooltip();
        lpkgToolTip.setFont(new Font(16));
        lpkgToolTip.setText("Controlo automático das luses com deteção de tunel\n" +
                            "Espelho interior electrocromático\n" +
                            "Sensor de chuva");
        Lightpkg.setTooltip(lpkgToolTip);

        CheckBox invpkg = createCheckBox(loginRoot, "Pacote de Inverno (+600€)", 100, 200);
        Tooltip invToolTip = new Tooltip();
        invToolTip.setFont(new Font(16));
        invToolTip.setText("Faróis de nevoeiro\n" +
                           "Espelhos aquecidos\n" +
                           "Sensor de chuva");
        invpkg.setTooltip(invToolTip);

        CheckBox confpkg = createCheckBox(loginRoot, "Pacote de Confort (+750€)", 100, 250);
        Tooltip confToolTip = new Tooltip();
        confToolTip.setFont(new Font(16));
        confToolTip.setText("Bancos aquecidos\n" +
                            "Suspensão confort\n" +
                            "Sensor de chuva");
        confpkg.setTooltip(confToolTip);

        CheckBox despkg = createCheckBox(loginRoot, "Pacote Desportivo (+800€)", 100, 300);
        Tooltip desToolTip = new Tooltip();
        desToolTip.setFont(new Font(16));
        desToolTip.setText("Para-Choques desportivo\n" +
                           "Escape desportivo\n" +
                           "Suspensão adaptativa");
        despkg.setTooltip(desToolTip);

        //-----------------------------------------------------------------------------------

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
        guardarBtn.setPrefSize(100,30);
        guardarBtn.setLayoutX(700);
        guardarBtn.setLayoutY(700);
        guardarBtn.setText("Avançar");
        guardarBtn.setOnAction( actionEvent -> new menuConfEncomenda(primaryStage));
        loginRoot.getChildren().add(guardarBtn);

        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
    }

}
