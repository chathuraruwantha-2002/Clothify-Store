package controller.Customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.PlaceOrder.PlaceOrderFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewCustomerFormController implements Initializable {

    @FXML
    private JFXTextField CustAddressSideView;

    @FXML
    private JFXComboBox CustGenderSideView;

    @FXML
    private Label CustIdSideView;

    @FXML
    private ImageView CustImgSideView;

    @FXML
    private JFXTextField CustNameSideView;

    @FXML
    private JFXTextField CustPhnoSideView;

    private PlaceOrderFormController parentController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Customer Id set
        CustIdSideView.setText(String.format("C%03d", new CustomerController().getTopCustomerId()+1));

        // ComboBox gender
        ObservableList<String> genderValues = FXCollections.observableArrayList("Male", "Female");
        CustGenderSideView.setItems(genderValues);
    }

    @FXML
    void btnAddCustomer(MouseEvent event) {
        Customer customer = new Customer(
                Integer.parseInt(CustIdSideView.getText().substring(1)),
                CustNameSideView.getText(),
                CustGenderSideView.getValue().toString(),
                CustPhnoSideView.getText(),
                CustAddressSideView.getText()
        );
        boolean result = new CustomerController().addCustomer(customer);

        if (result) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully.!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Adding Failed.!!").show();
        }
        clearForm();
    }

    @FXML
    void btnClear(MouseEvent event) {
        clearForm();
    }

    public void btnReturn(MouseEvent mouseEvent) {
        parentController.OverlayPane.setVisible(false);
        parentController.OverlayPane.setMouseTransparent(true);

        parentController.orderDetailsDisplayArea.setVisible(true);
        parentController.orderDetailsDisplayArea.setMouseTransparent(false);
    }

    public void setParentController(PlaceOrderFormController placeOrderFormController) {
        this.parentController = placeOrderFormController;
    }

    private void clearForm() {
        CustIdSideView.setText("");
        CustNameSideView.setText("");
        CustGenderSideView.setValue("");
        CustAddressSideView.setText("");
        CustPhnoSideView.setText("");
    }

    public void fGenderState(Event event) {

        if (CustGenderSideView.getValue().equals("Male")) {
            CustImgSideView.setImage(new javafx.scene.image.Image("/img/man.png"));
        } else if (CustGenderSideView.getValue().equals("Female")) {
            CustImgSideView.setImage(new javafx.scene.image.Image("/img/woman.png"));
        }
    }
}
