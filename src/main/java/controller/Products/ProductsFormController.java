package controller.Products;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Cards.ProductCardController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsFormController implements Initializable {

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
    private JFXTextField SupllierNameSideView;



    @FXML
    private GridPane ProductsContainer;
    private List<Product> ProductList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductList = new ArrayList<>(new ProductsController().GetAllProducts());


        int column = 0;
        int row = 1;

        try{
            for (Product product :ProductList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/ProductCard.fxml"));
                VBox productCard = fxmlLoader.load();
                ProductCardController productcardController = fxmlLoader.getController();
                productcardController.setData(product,this);


                if(column == 4){
                    column = 0;
                    ++row;
                }
                ProductsContainer.add(productCard,column++,row);
                GridPane.setMargin(productCard,new Insets(7.5));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        // ComboBox setup for Category and Size
        ObservableList<String> categoryValues = FXCollections.observableArrayList("Ladies", "Gents", "Kids");
        CategorySideView.setItems(categoryValues);
        //CategorySideView.setValue("Ladies");

        ObservableList<String> sizeValues = FXCollections.observableArrayList("S", "M", "L", "XL", "XXL");
        SizeSideView.setItems(sizeValues);
    }

    public void viewProductDetailsSide(Product product){

        ProductIDSideView.setText(String.format("P%03d", product.getProductID()));
        ProductNameSideView.setText(product.getName());
        CategorySideView.setValue(product.getCategoryName());
        SupllierNameSideView.setText(product.getSupplierName());
        QtySideView.setText(String.valueOf(product.getQty()));
        SizeSideView.setValue(product.getSize());
        PriceSideView.setText(String.valueOf(product.getPrice()));

    }

}