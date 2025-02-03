package controller.OrderDetails;

import com.jfoenix.controls.JFXTextField;
import controller.Inventory.InventoryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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


    @FXML
    private Label custId;

    @FXML
    private Label custName;

    @FXML
    private Label discountSales;

    @FXML
    private Label empId;

    @FXML
    private Label empName;

    @FXML
    private Label finalTotAmount;

    @FXML
    private Label orderDate;

    @FXML
    private Label orderId;

    @FXML
    private Label paymentMethod;

    @FXML
    private Label subTotal;

    @FXML
    private Label tax;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LoadTableData(new OrderDetailsController().GetAllOrderList());

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colTotAmount.setCellValueFactory(new PropertyValueFactory<>("grandTotal"));
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
        clearform();
        LoadTableData(new OrderDetailsController().SearchOrder(SearchField.getText()));
    }

    public void fTableRowSelection(MouseEvent mouseEvent) {
        ShowDataSideview((Order) orderDetailsTable.getSelectionModel().getSelectedItem());
    }

    private void ShowDataSideview (Order datalist){

        orderDate.setText(datalist.getDate().substring(0,10));
        orderId.setText(String.format("D%03d",datalist.getOrderId()));
        empId.setText(String.format("E%03d",datalist.getEmpId()));
        empName.setText(new OrderDetailsController().getEmpName(datalist.getEmpId()));
        custId.setText(String.format("C%03d",datalist.getCustId()));
        custName.setText(new OrderDetailsController().getCustomerName(datalist.getCustId()));
        subTotal.setText(String.valueOf(datalist.getSubTotal()));
        discountSales.setText(String.valueOf(datalist.getDiscount()));
        tax.setText(String.valueOf(datalist.getTax()));
        finalTotAmount.setText(String.valueOf(datalist.getGrandTotal()));
        paymentMethod.setText(datalist.getPaymentType());
    }

    private void clearform(){
        orderDate.setText("");
        orderId.setText("");
        empId.setText("");
        //empName.setText("");
        custId.setText("");
        //custName.setText("");
        subTotal.setText("");
        discountSales.setText("");
        tax.setText("");
        finalTotAmount.setText("");
        paymentMethod.setText("");
    }

}
