package Controller;

import Model.Class.User;
import com.mysql.cj.xdevapi.Table;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerAdmin extends Controller implements Initializable {


    @FXML
    private TableView<User> funcTable;

    @FXML
    private TableColumn<User, String> nomeCol, nifCol, passwordCol, usernameCol, contactoCol, funcaoCol;

    @FXML
    private Button sairButton, adicionarButton, removerButton;

    private List<User> users;

    private ObservableList<User> ob;


    /** Butoes -------------------------------------------------------------------------------------------------------*/

    /**
     * Adicionar funcionario
     */
    public void adicionarFuncionario() throws IOException {
        launchController("../View/novoFunc.fxml");
        /*
        Parent root = FXMLLoader.load(getClass().getResource("../View/novoFunc.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        */
    }

    /**
     * Remove funcionario
     */
    public void removeFuncionario(){
        User u = funcTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover funcionário");
        alert.setHeaderText("Tem a certeza que pretende remover o funcionário?");
        alert.setContentText("Uma vez removido, não pode voltar atrás");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            super.getFacade().removeUser(u.getNif());
            funcTable.getItems().remove(u);
        }
    }

    /**
     * Guardar alteracoes
     */
    public void saveList() throws Exception{
        users = ob.stream().collect(Collectors.toList());
        getFacade().updateUsers(users);
    }

    /**
     * Sair do menu
     */
    @FXML
    public void backView(){
        launchController("../View/Login.fxml");
    }

    /** Lancar a vista -----------------------------------------------------------------------------------------------*/

    @Override
    public void launchController(){
        try{
            super.launchController();
            this.users = super.getFacade().getUsers();
            ob = FXCollections.observableArrayList(users);
            funcTable.setItems(ob);
        }
        catch (Exception e){
            super.error(e.getMessage());
        }
    }
    private void editableCols(){
        nomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactoCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nomeCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setNome(e.getNewValue());
        });
        usernameCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setUsername(e.getNewValue());
        });
        passwordCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
        });
        contactoCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setContacto(e.getNewValue());
        });

        funcTable.setEditable(true);
        nomeCol.setEditable(true);
        usernameCol.setEditable(true);
        passwordCol.setEditable(true);
        contactoCol.setEditable(true);
    }

    /** Inicializar a vista ------------------------------------------------------------------------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle rb){
        nomeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        nifCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNif()));
        usernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        passwordCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        contactoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContacto()));
        funcaoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        editableCols();
    }


}
