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
        URL resource = this.getClass().getResource("/view/Inventory.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        UiLoadingArea.getChildren().clear();
        UiLoadingArea.getChildren().add(load);

    }

    @FXML
    public void LogoutButton(ActionEvent event) {

    }

    @FXML
    public void OrderDetailsbutton(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/OrderDetails.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        UiLoadingArea.getChildren().clear();
        UiLoadingArea.getChildren().add(load);
    }

    @FXML
    public void PlaceOrderButton(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/PlaceOrder.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        UiLoadingArea.getChildren().clear();
        UiLoadingArea.getChildren().add(load);
    }

    @FXML
    public void ProductsDetailsButton(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/ProductsDetails.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        UiLoadingArea.getChildren().clear();
        UiLoadingArea.getChildren().add(load);
    }

    @FXML
    public void SuppliersButton(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/Suppliers.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        UiLoadingArea.getChildren().clear();
        UiLoadingArea.getChildren().add(load);

    }

    public void ReportsButton(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Reports.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        UiLoadingArea.getChildren().clear();
        UiLoadingArea.getChildren().add(load);
    }

    public void EmployeesButton(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/Employees.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);

        UiLoadingArea.getChildren().clear();
        UiLoadingArea.getChildren().add(load);
    }
}

