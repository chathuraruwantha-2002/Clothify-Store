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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

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

        // Set up the Action column with buttons and textfields
        ColAction.setCellFactory(param -> new TableCell<Inventory, Void>() {
            private final JFXButton btnAdd = new JFXButton();
            private final ImageView AddIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/Add.png")));


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Add button
                    btnAdd.setStyle("-fx-background-color: #FF4C4C; -fx-text-fill: white;");
                    AddIcon.setFitWidth(20);
                    AddIcon.setFitHeight(20);
                    btnAdd.setGraphic(AddIcon);
                    btnAdd.setDisable(true);

                    // TextField for adding stock
                    TextField txtAddStock = new TextField();
                    txtAddStock.setPromptText("Add stock");
                    txtAddStock.setPrefWidth(80);

                    // Restrict to numbers only
                    txtAddStock.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            txtAddStock.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    });


                    HBox actionContainer = new HBox(10, btnAdd, txtAddStock);
                    setGraphic(actionContainer);


                    txtAddStock.textProperty().addListener((observable, oldValue, newValue) -> {
                        btnAdd.setDisable(newValue.trim().isEmpty());
                    });


                    btnAdd.setOnAction(event -> {
                        Inventory selectedInventory = getTableRow().getItem();
                        if (selectedInventory != null) {
                            String inputStock = txtAddStock.getText();
                            UpdateStock(selectedInventory, inputStock);
                            txtAddStock.clear();
                        }
                    });
                }
            }
        });



        loadTableData();

    }

    private void loadTableData() {
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
        List<Inventory> dataList = new InventoryController().getAllInventoryList();
        inventoryList.addAll(dataList);
        System.out.println(dataList);
        tblInventory.setItems(inventoryList);
    }

    public void Searchinput(KeyEvent keyEvent) {
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
        List<Inventory> dataListS = new InventoryController().searchInventory(Searchareainput.getText());
        System.out.println(dataListS);
        inventoryList.addAll(dataListS);
        tblInventory.setItems(inventoryList);

    }

    //experimental purpose
    public void SelectedRowAc(MouseEvent mouseEvent) {

        Inventory selectedInventory = (Inventory) tblInventory.getSelectionModel().getSelectedItem();

        if (selectedInventory != null) {

            int productId = selectedInventory.getProductID();
            System.out.println("Selected Product ID: " + productId);

        }
    }

    //////dddd/////
    private void UpdateStock(Inventory selectedInventory,String inputStock) {
        int productId = selectedInventory.getProductID();
        int Stock = Integer.parseInt(inputStock);
        System.out.println("Update clicked for Product ID @ Stock : " + productId+ " " +Stock);
        new InventoryController().updateInventory(Stock,productId);
        loadTableData();

    }

}
