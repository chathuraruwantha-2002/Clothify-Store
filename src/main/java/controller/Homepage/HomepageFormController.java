package controller.Homepage;


import com.mysql.cj.xdevapi.Warning;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomepageFormController implements Initializable {

    @FXML
    private AnchorPane UiLoadingArea;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL resource = this.getClass().getResource("/view/Dashboard.fxml");

        assert resource != null;

        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UiLoadingArea.getChildren().clear();
        UiLoadingArea.getChildren().add(load);
    }





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
    public void LogoutButton(ActionEvent event) throws IOException {

        Stage stage = (Stage) UiLoadingArea.getScene().getWindow();
        stage.close();
        Stage loginStage = new Stage();
        loginStage.getIcons().add(new Image("img/Logo for Exe.png"));
        loginStage.setTitle("Clothify Store");
        loginStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login.fxml"))));
        loginStage.show();

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

