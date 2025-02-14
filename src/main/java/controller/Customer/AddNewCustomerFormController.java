package controller.Customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.PlaceOrder.PlaceOrderFormController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AddNewCustomerFormController {

    @FXML
    private JFXTextField CustAddressSideView;

    @FXML
    private JFXComboBox<?> CustGenderSideView;

    @FXML
    private Label CustIdSideView;

    @FXML
    private ImageView CustImgSideView;

    @FXML
    private JFXTextField CustNameSideView;

    @FXML
    private JFXTextField CustPhnoSideView;

    private PlaceOrderFormController parentController;

    @FXML
    void btnAddCustomer(MouseEvent event) {

    }

    @FXML
    void btnClear(MouseEvent event) {

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
}
