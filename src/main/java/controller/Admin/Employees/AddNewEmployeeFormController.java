package controller.Admin.Employees;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Suppliers.SuppliersFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewEmployeeFormController implements Initializable {

    @FXML
    private JFXTextField empAddressSideview;

    @FXML
    private JFXTextField empEmailSideview;

    @FXML
    private JFXComboBox empGenderSideview;

    @FXML
    private Label empIdSideview;

    @FXML
    private ImageView empImgSideview;

    @FXML
    private JFXTextField empNameSideview;

    @FXML
    private JFXTextField empPhoneSideview;

    @FXML
    private JFXTextField empPositionSideview;

    @FXML
    private AnchorPane EmployeeDetailsArea;

    private EmployeeFormController parentController;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // ComboBox gender
        ObservableList<String> genderValues = FXCollections.observableArrayList("Male", "Female");
        empGenderSideview.setItems(genderValues);

    }

    public void setParentController(EmployeeFormController parentController) {
        this.parentController = parentController;
    }


    public void fAddBtn(MouseEvent mouseEvent) {

    }

    public void fCancelBtn(MouseEvent mouseEvent) {
        ClearForm();
    }

    public void Freturn(MouseEvent mouseEvent) {
        parentController.OverlayPane.setVisible(false);
        parentController.OverlayPane.setMouseTransparent(true);

        parentController.EmployeeDetailsArea.setVisible(true);
        parentController.EmployeeDetailsArea.setMouseTransparent(false);
    }

    private void ClearForm(){
        empIdSideview.setText("");
        empAddressSideview.setText("");
        empEmailSideview.setText("");
        empGenderSideview.setValue("");
        empNameSideview.setText("");
        empPhoneSideview.setText("");
        empPositionSideview.setText("");
    }
}
