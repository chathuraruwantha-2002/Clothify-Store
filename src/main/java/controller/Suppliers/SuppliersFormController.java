package controller.Suppliers;

import controller.Cards.ProductCardController;
import controller.Cards.SupplierCardFormController;
import controller.Products.ProductsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Product;
import model.Supplier;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SuppliersFormController implements Initializable {

    @FXML
    private GridPane SuppliersContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadGridCards(new ArrayList<>(new SuppliersController().getAllSuppliers()));
    }

    public void LoadGridCards(List<Supplier> supplierList){

        SuppliersContainer.getChildren().clear();

        int column = 0;
        int row = 1;

        try{
            for (Supplier supplier :supplierList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/SupplierCard.fxml"));
                VBox supplierCard = fxmlLoader.load();
                SupplierCardFormController suppliercardController = fxmlLoader.getController();
                boolean result = suppliercardController.setData(supplier,this);
                if(result){
                    if(column == 3){
                        column = 0;
                        ++row;
                    }
                    SuppliersContainer.add(supplierCard,column++,row);
                    GridPane.setMargin(supplierCard,new Insets(21.9));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}
