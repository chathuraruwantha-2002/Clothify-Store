package controller.Admin.Reports;

import model.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ReportsFormController {
    public VBox CardArea;


    public void loadProducts(List<Product> products) {
        // Loop through each product and create a card for it

        for (Product product : products) {
            try {
                // Load the FXML file for a product card
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProductCard.fxml"));
                Parent productCard = loader.load();

                CardArea.getChildren().add(productCard);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
