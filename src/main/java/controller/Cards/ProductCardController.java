package controller.Cards;

import controller.Products.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;


public class ProductCardController {

    @FXML
    private Label lblProductName;

    @FXML
    private Label lblProductPrice;

    @FXML
    private ImageView productImg;

    @FXML
    private Button btnAddToCart;

    // Method to set product data into the card
    public void setProductData(Product product) {
        lblProductName.setText(product.getName());
        lblProductPrice.setText("$" + product.getPrice());
        productImg.setImage(new Image(product.getImageUrl()));  // Assuming Product has getImageUrl()
    }

    // Method to handle the Add to Cart button action
    @FXML
    private void onAddToCart() {
        System.out.println(lblProductName.getText() + " added to cart!");
    }
}
