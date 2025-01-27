package controller.Products;

import controller.Cards.ProductCardController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsFormController implements Initializable {

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
                productcardController.setData(product);


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
    }
}