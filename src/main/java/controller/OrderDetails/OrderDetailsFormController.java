package controller.OrderDetails;

import DBConnection.DBConnection;
import com.jfoenix.controls.JFXTextField;
import controller.Inventory.InventoryController;
import controller.Products.ProductsController;
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
import model.OrderItemsDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailsFormController implements Initializable {

    @FXML
    private TableView tableItemview;

    @FXML
    private TableColumn colItemId;

    @FXML
    private TableColumn colItemName;

    @FXML
    private TableColumn colItemPrice;

    @FXML
    private TableColumn colItemQty;

    @FXML
    private TableColumn colIQTotPrice;




    @FXML
    private JFXTextField SearchField;

    @FXML
    private TableColumn colTax;

    @FXML
    private TableColumn colDiscount;

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

        //orderDetails main table
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colTotAmount.setCellValueFactory(new PropertyValueFactory<>("grandTotal"));
        colOrderStatus.setCellValueFactory(new PropertyValueFactory<>("isReturned"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colTax.setCellValueFactory(new PropertyValueFactory<>("tax"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));


        //items table
        colItemId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colIQTotPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));


    }

    private void LoadTableData(List<Order> OrdersList) {

        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        ordersList.addAll(OrdersList);
        orderDetailsTable.setItems(ordersList);

    }

    private void loadItemsTableData(List<OrderItemsDetails> ItemsList){

        ObservableList<OrderItemsDetails> itemsList = FXCollections.observableArrayList();
        itemsList.addAll(ItemsList);
        tableItemview.setItems(itemsList);

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
        loadItemsTableData(new OrderDetailsController().getItemList(datalist.getOrderId()));
    }

    private void clearform(){
        orderDate.setText("");
        orderId.setText("");
        empId.setText("");
        empName.setText("");
        custId.setText("");
        custName.setText("");
        subTotal.setText("");
        discountSales.setText("");
        tax.setText("");
        finalTotAmount.setText("");
        paymentMethod.setText("");
        tableItemview.getItems().clear();
    }

    public void fPrintOrderDetails(MouseEvent mouseEvent) {
        try {
            JasperDesign report = JRXmlLoader.load("src/main/resources/reports/orderDetailsReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(report);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
