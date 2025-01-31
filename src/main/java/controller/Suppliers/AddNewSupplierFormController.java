package controller.Suppliers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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
    }

    public void btnClear(MouseEvent mouseEvent) {
    }

    public void setParentController(SuppliersFormController parentController) {
        this.parentController = parentController;
    }

}
