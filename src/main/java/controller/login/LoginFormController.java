package controller.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXTextField userEmail;

    @FXML
    private JFXPasswordField userPassword;


    public void fLogin(MouseEvent mouseEvent) throws IOException {
        User user = new LoginController().getUserDetails(userEmail.getText());

        if(userEmail.getText().isEmpty() && userPassword.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Email and Password.!!").show();
        }else if(userEmail.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please Enter Email.!!").show();
        } else if (userPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Password.!!").show();
        }else if(user == null){
            new Alert(Alert.AlertType.ERROR,"User Not Found.!!").show();
        }else if(user.getPassword().equals(userPassword.getText())) {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Homepage.fxml"))));
            stage.show();
            //close the login window
            Stage currentStage = (Stage) userEmail.getScene().getWindow();
            currentStage.close();
            new Alert(Alert.AlertType.INFORMATION,"Login Success.!!").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid Password.!!").show();
        }
    }
}
