package controller.Admin.Employees;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Suppliers.SuppliersFormController;
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

    @FXML
    private AnchorPane OverlayPane;

    private EmployeeFormController parentController;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setParentController(EmployeeFormController parentController) {
        this.parentController = parentController;
    }

    public void FaddEmployee(MouseEvent mouseEvent) {

    }

    public void fAddBtn(MouseEvent mouseEvent) {

    }

    public void fCancelBtn(MouseEvent mouseEvent) {

    }

}
