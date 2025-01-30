package controller.Products;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Suppliers.SuppliersController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddNewProductFormController implements Initializable {
    @FXML
    private JFXComboBox CategorySideView;

    @FXML
    private JFXTextField PriceSideView;

    @FXML
    private Label ProductIDSideView;

    @FXML
    private ImageView ProductImgSideView;

    @FXML
    private JFXTextField ProductNameSideView;

    @FXML
    private JFXTextField QtySideView;

    @FXML
    private JFXComboBox SizeSideView;

    @FXML
    private AnchorPane productDetailsarea;

    @FXML
    private JFXComboBox SupllierNameSideView;

    private ProductsFormController parentController;

    private Product product;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> categoryValues = FXCollections.observableArrayList("Ladies", "Gents", "Kids");
        CategorySideView.setItems(categoryValues);

        ObservableList<String> sizeValues = FXCollections.observableArrayList("S", "M", "L", "XL", "XXL");
        SizeSideView.setItems(sizeValues);

        //set productID
        ProductIDSideView.setText(String.format("P%03d", new ProductsController().getLastProductID()+1));

        //load all suppliers to the combobox
        SupllierNameSideView.setItems(FXCollections.observableArrayList(new SuppliersController().getAllSupplierNames()));

    }


    public void setParentController(ProductsFormController parentController) {
        this.parentController = parentController;
        System.out.println(parentController);
    }

    public void btnReturn(MouseEvent mouseEvent) {

        parentController.overlayPane.setVisible(false);
        parentController.overlayPane.setMouseTransparent(true);


        parentController.productDetailsarea.setVisible(true);
        parentController.productDetailsarea.setMouseTransparent(false);
    }

    //completely done
    public void btnAdd(MouseEvent mouseEvent) {

        //product object
        Product product = new Product(
                Integer.parseInt(ProductIDSideView.getText().substring(1)),
                ProductNameSideView.getText(),
                (String) SizeSideView.getValue(),
                "/img/Shirt.jpg",
                Double.parseDouble(PriceSideView.getText()),
                CategorySideView.getSelectionModel().getSelectedIndex() + 1,
                0,
                new SuppliersController().getSupplierIdByName((String) SupllierNameSideView.getValue()),
                Integer.parseInt(QtySideView.getText())
        );
        System.out.println(product);

        boolean result = false;
        try {
            result = new ProductsController().addProduct(product);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (result) {
            parentController.LoadGridCards(new ArrayList<>(new ProductsController().GetAllProducts()));
            new Alert(Alert.AlertType.INFORMATION,"Product Added Successfully.!!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Product Adding Failed.!!").show();
        }
        clearform();
    }

    public void btnClear(MouseEvent mouseEvent) {
        clearform();
    }

    private void clearform(){
        ProductIDSideView.setText("");
        ProductNameSideView.setText("");
        CategorySideView.setValue("");
        SupllierNameSideView.setValue("");
        QtySideView.setText("");
        SizeSideView.setValue("");
        PriceSideView.setText("");
    }

}
