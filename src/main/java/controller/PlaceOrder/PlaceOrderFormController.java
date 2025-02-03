package controller.PlaceOrder;

import controller.Cards.ProductCardController;
import controller.Products.ProductsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.jfoenix.controls.JFXTextField;
import model.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    @FXML
    private GridPane ProductsContainer;

    @FXML
    private JFXTextField searchProducts;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadGridCards(new ProductsController().GetAllProducts());

    }

    public void LoadGridCards(List<Product> productlist){

        ProductsContainer.getChildren().clear();

        int column = 0;
        int row = 1;

        try{
            for (Product product :productlist) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/ProductCard.fxml"));
                VBox productCard = fxmlLoader.load();
                ProductCardController productcardController = fxmlLoader.getController();
                boolean result = productcardController.setData(product,this);

                if(result){
                    if(column == 4){
                        column = 0;
                        ++row;
                    }
                    ProductsContainer.add(productCard,column++,row);
                    GridPane.setMargin(productCard,new Insets(5.5));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void FSearchProducts(KeyEvent keyEvent) {
        LoadGridCards(new ProductsController().searchProduct(searchProducts.getText()));
    }


}
