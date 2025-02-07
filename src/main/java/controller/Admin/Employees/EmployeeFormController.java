package controller.Admin.Employees;

import controller.Cards.EmployeeCardFormController;
import controller.Cards.SupplierCardFormController;
import controller.Suppliers.SuppliersController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Employee;
import model.Supplier;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoadGridCards(new EmployeeController().getAllEmployees());
    }

    @FXML
    private GridPane EmployeesContainer;

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

}
