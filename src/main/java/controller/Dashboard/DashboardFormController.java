package controller.Dashboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private Label activeLabel;

    @FXML
    private Label alertsLabel;

    @FXML
    private Label returnsLabel;

    @FXML
    private Label salesLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
