package controller.Homepage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class HomepageFormController {

    @FXML
    private AnchorPane UiLoadingArea;

    @FXML
    public void Dashboardbutton(ActionEvent action) throws IOException {
        URL resource = this.getClass().getResource("/view/Dashboard.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        UiLoadingArea.getChildren().clear();
        UiLoadingArea.getChildren().add(load);

    }

    @FXML
    public void InventoryButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void LogoutButton(ActionEvent event) {

    }

    @FXML
    public void OrderDetailsbutton(ActionEvent event) {

    }

    @FXML
    public void PlaceOrderButton(ActionEvent event) {

    }

    @FXML
    public void ProductsDetailsButton(ActionEvent event) {

    }

    @FXML
    public void SuppliersButton(ActionEvent event) {

    }

}

