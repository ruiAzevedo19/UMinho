package app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class menuPagamentos {

    public TextField createTextLabel(Group g, String l, int posX, int posY) {
        //labeltream

        Label label = new Label(l);
        label.setLayoutX(posX);
        label.setLayoutY(posY);
        g.getChildren().add(label);
        //Text
        TextField text = new TextField ();
        text.setPrefSize(200,30);
        text.setLayoutX(posX+70);
        text.setLayoutY(posY-5);
        text.setPrefSize(300,30);
        g.getChildren().add(text);
        return text;
    }

    public RadioButton createCheckBox (Group g, String n, int posX, int posY) {
        RadioButton box = new RadioButton();
        box.setText(n);
        box.setLayoutX(posX);
        box.setLayoutY(posY);
        g.getChildren().add(box);
        return box;
    }

    public menuPagamentos(Stage s) {

        Stage primaryStage = new Stage();
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Dados do Cliente");
        Group loginRoot = new Group();
        Scene loginScene = new Scene(loginRoot, 800, 500);

        //-------------------------------- Dados Cliente ----------------------------------
        //Title
        Label titleLabel = new Label("Dados do Cliente");
        titleLabel.setLayoutX(50);
        titleLabel.setLayoutY(10);
        titleLabel.setFont(Font.font(36));
        titleLabel.setStyle("-fx-font-weight: bold");
        loginRoot.getChildren().add(titleLabel);

        TextField nome = createTextLabel(loginRoot, "Nome: ", 50, 100);
        TextField morada = createTextLabel(loginRoot, "Morada: ", 50, 150);
        TextField mail = createTextLabel(loginRoot, "E-mail: ", 50, 200);
        TextField contacto = createTextLabel(loginRoot, "Contacto: ", 50, 250);
        TextField nif = createTextLabel(loginRoot, "NIF: ", 50, 300);

        //-------------------------------- Pagamento ----------------------------------
        //Title
        Label paymentLabel = new Label("Método de Pagamento");
        paymentLabel.setLayoutX(500);
        paymentLabel.setLayoutY(100);
        paymentLabel.setFont(Font.font(20));
        loginRoot.getChildren().add(paymentLabel);

        final ToggleGroup group = new ToggleGroup();
        RadioButton visa = createCheckBox(loginRoot, "Visa", 550, 150);
        visa.setToggleGroup(group);
        RadioButton masterCard = createCheckBox(loginRoot, "MasterCard", 550, 200);
        masterCard.setToggleGroup(group);
        RadioButton cheque = createCheckBox(loginRoot, "Cheque", 550, 250);
        cheque.setToggleGroup(group);
        RadioButton numerario = createCheckBox(loginRoot, "Numerário", 550, 300);
        numerario.setToggleGroup(group);

        //-----------------------------------------------------------------------------

        //Voltar button
        Button voltarBtn = new Button();
        voltarBtn.setPrefSize(100,30);
        voltarBtn.setLayoutX(250);
        voltarBtn.setLayoutY(400);
        voltarBtn.setText("Voltar");
/* */   voltarBtn.setOnAction( actionEvent -> primaryStage.close());//--- ALTERAR FUTURAMENTE
        loginRoot.getChildren().add(voltarBtn);

        //Guardar button
        Button guardarBtn = new Button();
        guardarBtn.setPrefSize(200,30);
        guardarBtn.setLayoutX(420);
        guardarBtn.setLayoutY(400);
        guardarBtn.setText("Efectuar Pagamento");
/* */   guardarBtn.setOnAction( actionEvent -> primaryStage.close());//--- ALTERAR FUTURAMENTE
        loginRoot.getChildren().add(guardarBtn);

        //-----------------------------------------------------------------------------

        primaryStage.setScene(loginScene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

}
