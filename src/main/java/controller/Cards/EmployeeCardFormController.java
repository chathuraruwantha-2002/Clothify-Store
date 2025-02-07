package controller.Cards;

import controller.Admin.Employees.EmployeeFormController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Employee;

public class EmployeeCardFormController {

    @FXML
    private ImageView EmployeeImage;

    @FXML
    private Label EmployeeName;

    @FXML
    private Label EmployeePhno;

    private EmployeeFormController parentController;
    private Employee employee;

    public void CardData(MouseEvent mouseEvent) {
        parentController.viewEmployeeDetailsSide(employee);

        parentController.OverlayPane.setVisible(false);
        parentController.OverlayPane.setMouseTransparent(true);

        parentController.EmployeeDetailsArea.setVisible(true);
        parentController.EmployeeDetailsArea.setMouseTransparent(false);
    }

    public boolean setData(Employee employee, EmployeeFormController parentController) {
        this.parentController = parentController;
        this.employee = employee;

        EmployeeName.setText(employee.getName());
        EmployeePhno.setText(employee.getContact());

        if (employee.getGender().equals("Male")) {
            EmployeeImage.setImage(new ImageView("/img/man.png").getImage());
        }else{
            EmployeeImage.setImage(new ImageView("/img/woman.png").getImage());
        }
        return (employee != null && parentController != null ? true : false);
    }
}
