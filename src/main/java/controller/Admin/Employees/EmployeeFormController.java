package controller.Admin.Employees;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Cards.EmployeeCardFormController;
import controller.Cards.SupplierCardFormController;
import controller.Products.ProductsController;
import controller.Suppliers.SuppliersController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Employee;
import model.Supplier;

import java.net.URL;
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

    private Employee employee;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
}
