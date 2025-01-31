package controller.Suppliers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Products.ProductsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Supplier;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddNewSupplierFormController implements Initializable {

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

    private SuppliersFormController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Supplier Last Id
        SupIdSideView.setText(String.format("S%03d", new SuppliersController().GetLastSupplierID()+1));

        // ComboBox gender
        ObservableList<String> genderValues = FXCollections.observableArrayList("Male", "Female");
        SupGenderSideView.setItems(genderValues);

    }
    public void btnReturn(MouseEvent mouseEvent) {
        parentController.OverlayPane.setVisible(false);
        parentController.OverlayPane.setMouseTransparent(true);

        parentController.SupplierDetailsArea.setVisible(true);
        parentController.SupplierDetailsArea.setMouseTransparent(false);
    }

    public void btnAddSupplier(MouseEvent mouseEvent) {
        Supplier supplier = new Supplier(
                Integer.parseInt(SupIdSideView.getText().substring(1)),
                0,
                (String) SupGenderSideView.getValue(),
                SupNameSideView.getText(),
                SupCompanySideView.getText(),
                SupAddressSideView.getText(),
                SupPhnoSideView.getText(),
                SupEmailSideView.getText()
        );
        boolean result = new SuppliersController().AddSupplier(supplier);

        if (result) {
            clearForm();
            parentController.LoadGridCards(new SuppliersController().getAllSuppliers());
            new Alert(Alert.AlertType.INFORMATION,"Supplier Added Successfully.!!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Supplier Adding Failed.!!").show();
        }
    }

    public void btnClear(MouseEvent mouseEvent) {
        clearForm();
    }

    public void setParentController(SuppliersFormController parentController) {
        this.parentController = parentController;
    }

    private void clearForm(){
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
