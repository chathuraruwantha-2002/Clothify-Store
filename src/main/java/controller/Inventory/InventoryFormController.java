package controller.Inventory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryFormController implements Initializable {

    public JFXTextField Searchareainput;
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

        // Set up the Action column with buttons
        ColAction.setCellFactory(param -> new TableCell<Inventory, Void>() {
            private final JFXButton btnDelete = new JFXButton();
            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/delete icon.png")));


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    btnDelete.setStyle("-fx-background-color: #FF4C4C; -fx-text-fill: white;");
                    deleteIcon.setFitWidth(20);
                    deleteIcon.setFitHeight(20);
                    btnDelete.setGraphic(deleteIcon);
                    btnDelete.setOnAction(event -> {
                        Inventory selectedInventory = getTableRow().getItem();
                        if (selectedInventory != null) {
                            handleDelete(selectedInventory);
                        }
                    });

                    setGraphic(btnDelete);
                }
            }
        });



        loadTableData();

    }

    private void loadTableData() {
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
        List<Inventory> dataList = new InventoryController().getAllCombinedProductData();
        inventoryList.addAll(dataList);
        tblInventory.setItems(inventoryList);
    }

    public void Searchinput(KeyEvent keyEvent) {
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
        List<Inventory> dataListS = new InventoryController().search(Searchareainput.getText());
        System.out.println(dataListS);
        inventoryList.addAll(dataListS);
        tblInventory.setItems(inventoryList);

    }

    public void SelectedRowAc(MouseEvent mouseEvent) {
        // Get the selected item (Inventory object) from the table
        Inventory selectedInventory = (Inventory) tblInventory.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedInventory != null) {
            // Get the ProductID from the selected row
            int productId = selectedInventory.getProductId(); // Assuming getProductId() method exists in Inventory class
            System.out.println("Selected Product ID: " + productId);

            // You can now use productId for further actions (e.g., fetching detailed product info, etc.)
        }
    }

    //////dddd/////
    private void handleDelete(Inventory selectedInventory) {
        int productId = selectedInventory.getProductId();
        System.out.println("Delete clicked for Product ID: " + productId);

        // Call your deletion logic
        deleteProduct(productId);
    }

    private void deleteProduct(int productId) {
        System.out.println("Product with ID " + productId + " has been deleted.");
        // Perform deletion from database or data source here

        // Reload data to update the table
        loadTableData();
    }

}
