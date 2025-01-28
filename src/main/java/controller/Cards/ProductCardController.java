package controller.Cards;

import controller.Products.Product;
import controller.Products.ProductsFormController;
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
    private ProductsFormController parentFormController;

    public void setData(Product product, ProductsFormController parentFormController){
        this.product = product;
        this.parentFormController = parentFormController;
       // Image productImage = new Image(getClass().getResourceAsStream(product.getImageUrl()));
        //ProductImage.setImage(productImage);

        ProductName.setText(product.getName());

        ProductPrice.setText(String.valueOf(product.getPrice()));

        // ProductQty.setText(String.valueOf(product.getProductQty()));

    }

    public void CardData(MouseEvent mouseEvent) {

        if (parentFormController != null) {
            parentFormController.viewProductDetailsSide(product);
        }

    }
}
