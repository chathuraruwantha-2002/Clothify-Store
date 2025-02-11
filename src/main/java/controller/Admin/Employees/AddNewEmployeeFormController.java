package controller.Admin.Employees;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Suppliers.SuppliersFormController;
import controller.login.LoginFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;

import java.net.URL;
import java.sql.SQLException;
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
    private JFXTextField empPwSideview;

    @FXML
    private AnchorPane EmployeeDetailsArea;

    private EmployeeFormController parentController;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // ComboBox gender
        ObservableList<String> genderValues = FXCollections.observableArrayList("Male", "Female");
        empGenderSideview.setItems(genderValues);

        empIdSideview.setText(String.format("E%03d", new EmployeeController().getTopEmployeeID()+1));

    }

    public void setParentController(EmployeeFormController parentController) {
        this.parentController = parentController;
    }


    public void fAddBtn(MouseEvent mouseEvent) {
        addEmployee();
        parentController.LoadGridCards(new EmployeeController().getAllEmployees());
        ClearForm();
    }

    private void addEmployee(){
        BasicTextEncryptor basicTextEncryptor = new LoginFormController().setEncription();//encryption
        Employee employee = new Employee(
                Integer.parseInt(empIdSideview.getText().substring(1)),
                empNameSideview.getText(),
                empPhoneSideview.getText(),
                empEmailSideview.getText(),
                empAddressSideview.getText(),
                Integer.parseInt(empIdSideview.getText().substring(1)),
                empPositionSideview.getText(),
                empGenderSideview.getValue().toString()
        );

        User user = new User(
                Integer.parseInt(empIdSideview.getText().substring(1)),
                empEmailSideview.getText(),
                basicTextEncryptor.encrypt(empPwSideview.getText()),
                empPositionSideview.getText()
        );


        boolean result = false;
        try {

            result = new EmployeeController().addEmployee(employee,user);
            if(result){
                new Alert(Alert.AlertType.INFORMATION,"Employee Added Successfully.!!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Employee Adding Failed.!!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        empPwSideview.setText("");
    }
}
