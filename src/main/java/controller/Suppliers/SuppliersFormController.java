package controller.Suppliers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Cards.ProductCardController;
import controller.Cards.SupplierCardFormController;
import controller.Products.ProductsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private JFXTextField SupAddressSideView;

    @FXML
    private JFXTextField SupCompanySideView;

    @FXML
    private JFXTextField SupEmailSideView;

    @FXML
    private JFXComboBox SupGenderSideView;

    @FXML
    private Label SupIdSideView;

    @FXML
    private ImageView SupImgSideView;

    @FXML
    private JFXTextField SupNameSideView;

    @FXML
    private JFXTextField SupPhnoSideView;



    @FXML
    private GridPane SuppliersContainer;

    private Supplier supplier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadGridCards(new ArrayList<>(new SuppliersController().getAllSuppliers()));

        // ComboBox gender
        ObservableList<String> genderValues = FXCollections.observableArrayList("Male", "Female");
        SupGenderSideView.setItems(genderValues);
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

    public void viewSupplierDetailsSide(Supplier supplier){
        this.supplier = supplier;

        SupIdSideView.setText(String.format("S%03d" , supplier.getSupplierId()));
        SupNameSideView.setText(supplier.getName());
        SupGenderSideView.setValue(supplier.getGender());
        SupAddressSideView.setText(supplier.getAddress());
        SupPhnoSideView.setText(supplier.getPhone());
        SupEmailSideView.setText(supplier.getEmail());
        SupCompanySideView.setText(supplier.getCompany());

        if(supplier.getGender().equals("Male")){
            SupImgSideView.setImage(new ImageView("/img/man.png").getImage());
        }else{
            SupImgSideView.setImage(new ImageView("/img/woman.png").getImage());
        }

    }


    public void btnUpdateSupplier(MouseEvent mouseEvent) {

        supplier.setName(SupNameSideView.getText());
        supplier.setGender((String) SupGenderSideView.getValue());
        supplier.setAddress(SupAddressSideView.getText());
        supplier.setPhone(SupPhnoSideView.getText());
        supplier.setEmail(SupEmailSideView.getText());
        supplier.setCompany(SupCompanySideView.getText());

        boolean isUpdated = new SuppliersController().updateSupplier(supplier);
        if(isUpdated){
            LoadGridCards(new ArrayList<>(new SuppliersController().getAllSuppliers()));
            new Alert(Alert.AlertType.INFORMATION,"Supplier Updated Successfully.!!").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Supplier Updation Failed.!!").show();
        }
        clearForm();
    }

    public void btnDeleteSupplier(MouseEvent mouseEvent) {

        boolean isDeleted = new SuppliersController().deleteSupplier(supplier.getSupplierId());
        if(isDeleted){
            LoadGridCards(new ArrayList<>(new SuppliersController().getAllSuppliers()));
            new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted Successfully.!!").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Supplier Deletion Failed.!!").show();
        }
        clearForm();
    }

    public void btnAddNewSupplier(MouseEvent mouseEvent) {

    }
    public void clearForm(){
        //SupImgSideView.setImage();
        SupIdSideView.setText("");
        SupNameSideView.setText("");
        SupGenderSideView.setValue("");
        SupAddressSideView.setText("");
        SupPhnoSideView.setText("");
        SupEmailSideView.setText("");
        SupCompanySideView.setText("");
    }
}
