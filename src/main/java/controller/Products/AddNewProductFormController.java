package controller.Products;

import UserInfo.UserInfo;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Homepage.DefUserHomepageFormController;
import controller.Homepage.HomepageFormController;
import controller.Suppliers.SuppliersController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.Product;

import java.io.File;
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

    private String imagePath;



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
                imagePath,
                Double.parseDouble(PriceSideView.getText()),
                CategorySideView.getSelectionModel().getSelectedIndex() + 1,
                UserInfo.getInstance().getUser().getUserId(),
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
            parentController.LoadGridCards(new ArrayList<>(new ProductsController().CategoryProductsList((String) CategorySideView.getValue())));
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
        ProductImgSideView.setImage(new Image("/img/shadow art.png"));
    }

    public void btnAddPic(MouseEvent mouseEvent) {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String absolutePath = selectedFile.getAbsolutePath();
            System.out.println("Absolute file path: " + absolutePath);


            // Get the index where 'img\\products\\' starts
            int index = absolutePath.indexOf("img\\products\\");

            if (index != -1) {
                // Extract relative path from absolute path
                String relativePath = absolutePath.substring(index);

                // Replace backslashes with forward slashes
                relativePath = relativePath.replace("\\", "/");
                imagePath = "/" + relativePath;

                System.out.println("Final path: " + imagePath);
                ProductImgSideView.setImage(new Image(imagePath));


            } else {
                System.out.println("folder not found in path!");
            }
        }
    }
}
