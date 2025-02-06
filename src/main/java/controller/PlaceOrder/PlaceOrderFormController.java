package controller.PlaceOrder;

import com.jfoenix.controls.JFXButton;
import controller.Cards.ProductCardController;
import controller.Products.ProductsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.jfoenix.controls.JFXTextField;
import model.Inventory;
import model.Order;
import model.Product;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private Label subTotalDisplay;

    @FXML
    private Label taxDisplay;

    @FXML
    private JFXTextField taxInput;

    @FXML
    private Label totalDisplay;

    @FXML
    private Label discountDisplay;

    @FXML
    private JFXTextField discountInput;

    @FXML
    private TableView TableProductsView;

    @FXML
    private TableColumn colActions;

    @FXML
    private TableColumn colProductName;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colTotal;


    @FXML
    private GridPane ProductsContainer;

    @FXML
    private JFXTextField searchProducts;

    @FXML
    private JFXTextField customerName;

    @FXML
    private Label date;

    @FXML
    private Label orderId;

    @FXML
    private JFXTextField searchCustomer;

    private List<Product> SelectedProductList = new ArrayList<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearForm();
        LoadGridCards(new ProductsController().GetAllProducts());
        this.date.setText(java.time.LocalDate.now().toString());
        this.orderId.setText(String.format("O%03d", new PlaceOrderController().getTopOrderID()+1));

        //set table column names
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyBuying"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("TotalQtyPrice"));

    }

    public void LoadGridCards(List<Product> productlist){

        ProductsContainer.getChildren().clear();

        int column = 0;
        int row = 1;

        try{
            for (Product product :productlist) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/ProductCard.fxml"));
                VBox productCard = fxmlLoader.load();
                ProductCardController productcardController = fxmlLoader.getController();
                boolean result = productcardController.setData(product,this);

                if(result){
                    if(column == 4){
                        column = 0;
                        ++row;
                    }
                    ProductsContainer.add(productCard,column++,row);
                    GridPane.setMargin(productCard,new Insets(5.5));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void FSearchProducts(KeyEvent keyEvent) {
        LoadGridCards(new ProductsController().searchProduct(searchProducts.getText()));
    }


    public void fsearchCustomerbyPhno(KeyEvent keyEvent) {
        customerName.setText(new PlaceOrderController().getCustomerName(searchCustomer.getText()));
    }

    //rewrite this again (done )
    public void DisplayOrderDetailsOnSide (Product product) {

        for (Product productlistItem : SelectedProductList) {
            if (productlistItem.getProductID() == product.getProductID()){
                productlistItem.setQtyBuying(productlistItem.getQtyBuying()+1);
                productlistItem.setTotalQtyPrice(productlistItem.getPrice()*productlistItem.getQtyBuying());
                LoadProductsTable();
                return;
            }
        }
        product.setQtyBuying(1);
        product.setTotalQtyPrice(product.getPrice());
        SelectedProductList.add(product);
        LoadProductsTable();
        System.out.println(SelectedProductList);

    }

    private double subTotal (List <Product> productList) {
        double subTotalPrice = 0;
        for (Product productListItem : productList ) {
            subTotalPrice += productListItem.getTotalQtyPrice();
        }
        return Math.round(subTotalPrice * 100.0) / 100.0;
    }

    private double total (double subTotal,double discount,double tax){
        return Math.round((subTotal - discount + tax) * 100.0) / 100.0;
    }

    private void LoadProductsTable() {
        TableProductsView.getItems().clear();
        ObservableList<Product> itemsList = FXCollections.observableArrayList();
        itemsList.addAll(SelectedProductList);
        TableProductsView.setItems(itemsList);
    }


    public void FPlaceOrder(MouseEvent mouseEvent) {
        PlaceOrder();
        clearForm();
    }

    public void fDiscountCheck(KeyEvent keyEvent) {
        calculationsDisplay();
    }

    public void fTaxCheck(KeyEvent keyEvent) {
        calculationsDisplay();
    }

    private void calculationsDisplay (){
        double total = 0;
        double subTotal = subTotal(SelectedProductList);

        String discountInputText = discountInput.getText().trim();
        String taxInputText = taxInput.getText().trim();

        double discount = discountInputText.isEmpty() ? 0.0 : Double.parseDouble(discountInputText);
        double tax = taxInputText.isEmpty() ? 0.0 : Double.parseDouble(taxInputText);

        total = total(subTotal, discount, tax);

        discountDisplay.setText(String.format("%.2f",discount));
        taxDisplay.setText(String.format("%.2f",tax));
        subTotalDisplay.setText(String.format("%.2f",subTotal));
        totalDisplay.setText(String.format("%.2f",total));
    }

    private void clearForm(){
        //date.setText("");
        //orderId.setText("");
        customerName.setText("");
        searchCustomer.setText("");
        searchProducts.setText("");
        discountInput.setText("");
        taxInput.setText("");
        subTotalDisplay.setText("");
        taxDisplay.setText("");
        discountDisplay.setText("");
        totalDisplay.setText("");
        TableProductsView.getItems().clear();
    }

    private void PlaceOrder () {

        //update Inventory each placed products
        // add new records from orderdetails table (ordered products)
        //add a rec from orders table

        Order order = new Order();
        order.setSubTotal(subTotal(SelectedProductList));
        order.setDiscount(Double.parseDouble(discountInput.getText().trim()));
        order.setTax(Double.parseDouble(taxInput.getText().trim()));
        order.setGrandTotal(total(subTotal(SelectedProductList),Double.parseDouble(discountInput.getText().trim()),Double.parseDouble(taxInput.getText().trim())));
        order.setIsReturned("No");
        order.setPaymentType("Cash");
        order.setDate(LocalDate.now().toString());
        order.setCustId(new PlaceOrderController().getCustomerID(searchCustomer.getText().trim()));
        order.setOrderId(new PlaceOrderController().getTopOrderID()+1);
        order.setEmpId(1);

        boolean result = false;
        try {
            result = new PlaceOrderController().placeOrder(order, SelectedProductList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result){
            System.out.println("order placed...!");
        }

    }

}
