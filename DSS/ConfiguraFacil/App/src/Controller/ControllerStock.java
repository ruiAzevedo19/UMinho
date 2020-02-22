package Controller;

import Model.Class.Componente;
import Model.Class.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerStock extends Controller implements Initializable {

    @FXML
    private TableView<Componente> stockTable;

    @FXML
    private TableColumn<Componente, String> codCol, descCol, qtdCol, tipoCol, precoCol;

    @FXML
    private Button saveButton;

    private List<Componente> componentes;
    private ObservableList<Componente> ob;

    @Override
    public void launchController(){

        try{
            super.launchController();
            this.componentes = super.getFacade().getComponentes();
            ob = FXCollections.observableArrayList(componentes);
            stockTable.setItems(ob);
        }
        catch (Exception e){
            super.error(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        codCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        descCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        qtdCol.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getStock())));
        tipoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        precoCol.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getPreco())));
        editableCols();
    }

    private void editableCols(){
        qtdCol.setCellFactory(TextFieldTableCell.forTableColumn());
        precoCol.setCellFactory(TextFieldTableCell.forTableColumn());

        qtdCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setStock(Integer.parseInt(e.getNewValue()));
        });
        precoCol.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPreco(Double.parseDouble(e.getNewValue()));
        });
        stockTable.setEditable(true);
        qtdCol.setEditable(true);
        precoCol.setEditable(true);
    }

    @FXML
    private void backView(){
        launchController("../View/Login.fxml");
    }

    public void voltarView(){
        launchController("../View/consultas.fxml");
    }

    @FXML
    private void saveList() throws Exception{
        componentes = ob.stream().collect(Collectors.toList());
        getFacade().updateComponentes(componentes);
    }
}
