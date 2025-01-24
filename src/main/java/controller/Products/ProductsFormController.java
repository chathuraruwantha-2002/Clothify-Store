package controller.Products;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsFormController implements Initializable {

    @FXML
    private VBox productContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductsController controller = new ProductsController();
        List<Product> getall = controller.GetAllProducts();

    }
}
