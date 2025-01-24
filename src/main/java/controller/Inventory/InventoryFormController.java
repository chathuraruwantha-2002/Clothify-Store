package controller.Inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryFormController implements Initializable {

    @FXML
    private TableColumn ColAction;

    @FXML
    private TableColumn ColCategory;

    @FXML
    private TableColumn ColItemID;

    @FXML
    private TableColumn ColLastRestocked;

    @FXML
    private TableColumn ColLastRestockerID;

    @FXML
    private TableColumn ColProductName;

    @FXML
    private TableColumn ColQtyinStock;

    @FXML
    private TableColumn ColSupplier;

    @FXML
    private TableView tblInventory;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set up column bindings
        ColItemID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        ColProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        ColCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        ColSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        ColQtyinStock.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));
        ColLastRestocked.setCellValueFactory(new PropertyValueFactory<>("lastRestockedDate"));
       // ColLastRestockerID.setCellValueFactory(new PropertyValueFactory<>("lastRestockerId")); // will be implement
       // ColAction.setCellValueFactory(new PropertyValueFactory<>("action")); // will be implement

        loadTableData();

    }

    private void loadTableData() {
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
        List<Inventory> dataList = new InventoryController().getAllCombinedProductData();
        inventoryList.addAll(dataList);
        tblInventory.setItems(inventoryList);
    }
}
