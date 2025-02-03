package controller.Cards;

import controller.PlaceOrder.PlaceOrderFormController;
import model.Product;
import controller.Products.ProductsFormController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private ProductsFormController parentController;

    private PlaceOrderFormController parentControllerPlaceOrder;

    public boolean setData(Product product, ProductsFormController parentFormController){
        this.product = product;
        this.parentController = parentFormController;
       // Image productImage = new Image(getClass().getResourceAsStream(product.getImageUrl()));
        //ProductImage.setImage(productImage);

        ProductName.setText(product.getName());

        ProductPrice.setText(String.valueOf(product.getPrice()));

        // ProductQty.setText(String.valueOf(product.getProductQty()));

        return (product!=null && parentFormController!=null ? true : false);

    }

    public boolean setData(Product product, PlaceOrderFormController parentFormControllerPlaceOrder) {
        this.product = product;
        this.parentControllerPlaceOrder = parentFormControllerPlaceOrder;
        // Image productImage = new Image(getClass().getResourceAsStream(product.getImageUrl()));
        //ProductImage.setImage(productImage);

        ProductName.setText(product.getName());

        ProductPrice.setText(String.valueOf(product.getPrice()));

        // ProductQty.setText(String.valueOf(product.getProductQty()));

        return (product!=null && parentFormControllerPlaceOrder!=null ? true : false);

    }

    public void CardData(MouseEvent mouseEvent) {

        if (parentController != null) {
            parentController.viewProductDetailsSide(product);

            parentController.overlayPane.setVisible(false);
            parentController.overlayPane.setMouseTransparent(true);

            parentController.productDetailsarea.setVisible(true);
            parentController.productDetailsarea.setMouseTransparent(false);

        } else if (parentControllerPlaceOrder != null) {
            parentControllerPlaceOrder.DisplayOrderDetailsOnSide(product);
            //parentControllerPlaceOrder.overlayPane.setVisible(false);
            //parentControllerPlaceOrder.overlayPane.setMouseTransparent(true);

           // parentControllerPlaceOrder.productDetailsarea.setVisible(true);
           // parentControllerPlaceOrder.productDetailsarea.setMouseTransparent(false);
        }

    }

}
