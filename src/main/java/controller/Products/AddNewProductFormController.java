package controller.Products;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

public class AddNewProductFormController {

    private ProductsFormController parentController;


    public void FbtnReturn(MouseEvent mouseEvent) {

        parentController.overlayPane.setVisible(false);
        parentController.overlayPane.setMouseTransparent(true);


        parentController.productDetailsarea.setVisible(true);
        parentController.productDetailsarea.setMouseTransparent(false);

    }

    public void setParentController(ProductsFormController parentController) {
        this.parentController = parentController;
        System.out.println(parentController);
    }

}
