package Controller;

import Model.Class.Encomenda;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerEncomendas extends Controller implements Initializable {

    private List<Encomenda> encomendas;

    private ObservableList<Encomenda> ob;
    @FXML
    private TableView<Encomenda> tabelaEncomendas;

    @FXML
    private TableColumn<Encomenda, String> numeroCol,precoCol,metodoCol,vendedorCol,clienteCol,componentesCol;

    public void backView(){
        super.launchController("../View/Login.fxml");
    }

    public void voltarView(){
        super.launchController("../View/consultas.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        numeroCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        metodoCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetodoPagamento()));
        precoCol.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getPreco())));
        vendedorCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVendedor()));
        clienteCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente()));
        componentesCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.join(", ", cellData.getValue().getComponentes())));
    }

    @Override
    public void launchController(){
        try{
            super.launchController();
            this.encomendas = super.getFacade().getEncomendas();
            ob = FXCollections.observableArrayList(encomendas);
            tabelaEncomendas.setItems(ob);
        }
        catch (Exception e){
            super.error(e.getMessage());
        }
    }
}
