package controller.Admin.Employees;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Cards.EmployeeCardFormController;
import controller.Cards.SupplierCardFormController;
import controller.Products.ProductsController;
import controller.Suppliers.AddNewSupplierFormController;
import controller.Suppliers.SuppliersController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Employee;
import model.Supplier;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {
    @FXML
    private JFXTextField searchInput;

    @FXML
    private GridPane EmployeesContainer;

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
    public AnchorPane EmployeeDetailsArea;

    @FXML
    public AnchorPane OverlayPane;

    private Employee employee;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ClearForm();
        LoadGridCards(new EmployeeController().getAllEmployees());
        // ComboBox gender
        ObservableList<String> genderValues = FXCollections.observableArrayList("Male", "Female");
        empGenderSideview.setItems(genderValues);
    }


    public void LoadGridCards (List<Employee> employeeList){

        EmployeesContainer.getChildren().clear();

        int column = 0;
        int row = 1;

        try{
            for (Employee employee :employeeList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/EmployeeCard.fxml"));
                VBox employeeCard = fxmlLoader.load();
                EmployeeCardFormController employeecardController = fxmlLoader.getController();
                boolean result = employeecardController.setData(employee,this);
                if(result){
                    if(column == 3){
                        column = 0;
                        ++row;
                    }
                    EmployeesContainer.add(employeeCard,column++,row);
                    GridPane.setMargin(employeeCard,new Insets(21.9));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void FSearchEmp(KeyEvent keyEvent) {
        LoadGridCards(new EmployeeController().searchEmployee(searchInput.getText()));
    }

    public void viewEmployeeDetailsSide(Employee employee){
        this.employee = employee;

        empIdSideview.setText(String.format("E%03d",employee.getEmployeeId()));
        empNameSideview.setText(employee.getName());
        empPhoneSideview.setText(employee.getContact());
        empEmailSideview.setText(employee.getEmail());
        empAddressSideview.setText(employee.getAddress());
        empPositionSideview.setText(employee.getRole());
        empGenderSideview.setValue(employee.getGender());

        if(employee.getGender().equals("Male")){
            empImgSideview.setImage(new ImageView("/img/man.png").getImage());
        }else{
            empImgSideview.setImage(new ImageView("/img/woman.png").getImage());
        }

    }

    public void fAddNewEmployee(MouseEvent mouseEvent) {
        ClearForm();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddNewEmpolyee.fxml"));
            Parent newForm = loader.load();

            AddNewEmployeeFormController addNewEmployeeFormController = loader.getController();

            addNewEmployeeFormController.setParentController(this);


            OverlayPane.getChildren().clear();
            OverlayPane.getChildren().add(newForm);

            OverlayPane.setVisible(true);
            OverlayPane.setMouseTransparent(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void fUpdate(MouseEvent mouseEvent) {
        updateEmployee();
        ClearForm();
        LoadGridCards(new EmployeeController().getAllEmployees());

    }

    private void updateEmployee(){
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

        User user = new User();
                user.setUserId(Integer.parseInt(empIdSideview.getText().substring(1)));
                user.setEmail(empEmailSideview.getText());
                user.setRole(empPositionSideview.getText());



        boolean result = false;
        try {

            result = new EmployeeController().updateEmployee(employee,user);
            if(result){
                new Alert(Alert.AlertType.INFORMATION,"Employee Updated Successfully.!!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Employee Updating Failed.!!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void fDelete(MouseEvent mouseEvent) {
        deleteEmployee();
        ClearForm();
        LoadGridCards(new EmployeeController().getAllEmployees());
    }

    private void deleteEmployee(){
        boolean result = false;
        try {
            result = new EmployeeController().deleteEmployee(employee.getEmployeeId(),employee.getUserId());
            if(result){
                new Alert(Alert.AlertType.INFORMATION,"Employee Deleted Successfully.!!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Employee Deletion Failed.!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
