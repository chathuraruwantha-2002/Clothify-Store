package controller.PlaceOrder;

import controller.Cards.ProductCardController;
import controller.Products.ProductsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.jfoenix.controls.JFXTextField;
import model.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadGridCards(new ProductsController().GetAllProducts());
        this.date.setText(java.time.LocalDate.now().toString());
        this.orderId.setText(String.format("O%03d", new PlaceOrderController().getTopOrderID()+1));

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

    public void DisplayOrderDetailsOnSide (Product product) {
        System.out.println(product);

    }

}
