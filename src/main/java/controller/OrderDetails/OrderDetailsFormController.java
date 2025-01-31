package controller.OrderDetails;

import com.jfoenix.controls.JFXTextField;
import controller.Inventory.InventoryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Inventory;
import model.Order;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailsFormController implements Initializable {

    @FXML
    private JFXTextField SearchField;

    @FXML
    private TableColumn colAction;

    @FXML
    private TableColumn colCustId;

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colEmpId;

    @FXML
    private TableColumn colOrderId;

    @FXML
    private TableColumn colOrderStatus;

    @FXML
    private TableColumn colPaymentMethod;

    @FXML
    private TableColumn colTotAmount;

    @FXML
    private TableView orderDetailsTable;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LoadTableData(new OrderDetailsController().GetAllOrderList());

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colTotAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colOrderStatus.setCellValueFactory(new PropertyValueFactory<>("isReturned"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));

    }

    private void LoadTableData(List<Order> OrdersList) {

        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        ordersList.addAll(OrdersList);
        orderDetailsTable.setItems(ordersList);

    }

    public void fSearchOrders(KeyEvent keyEvent) {
        LoadTableData(new OrderDetailsController().SearchOrder(SearchField.getText()));
        System.out.println(new OrderDetailsController().SearchOrder(SearchField.getText()));
    }

}
