package controller.Cards;

import controller.Products.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProductCardController {

    @FXML
    private ImageView ProductImage;

    @FXML
    private Label ProductName;

    @FXML
    private Label ProductPrice;

    @FXML
    private Label ProductQty;
    private Product product;

    public void setData(Product product){
        this.product = product;
       // Image productImage = new Image(getClass().getResourceAsStream(product.getImageUrl()));
        //ProductImage.setImage(productImage);

        ProductName.setText(product.getName());

        ProductPrice.setText(String.valueOf(product.getPrice()));

        // ProductQty.setText(String.valueOf(product.getProductQty()));

    }

    public void CardData(MouseEvent mouseEvent) {
        System.out.println(product);
    }
}
