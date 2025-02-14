package controller.login;

import UserInfo.UserInfo;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.Homepage.DefUserHomepageFormController;
import controller.Homepage.HomepageFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXTextField userEmail;

    @FXML
    private JFXPasswordField userPassword;


    public void fLogin(MouseEvent mouseEvent) throws IOException {

        User user = new LoginController().getUserDetails(userEmail.getText());
        BasicTextEncryptor basicTextEncryptor = setEncription();//encription object

        if(userEmail.getText().isEmpty() && userPassword.getText().isEmpty()){

            new Alert(Alert.AlertType.ERROR,"Please Enter Email and Password.!!").show();
            //String encrypt = basicTextEncryptor.encrypt("ssss223");
            //System.out.println(encrypt);
            //String decrypt = basicTextEncryptor.decrypt("plXhPE/hMLQ0LzBwrTTumWfgIiWnUQ3e");
            //System.out.println(decrypt);

        }else if(userEmail.getText().isEmpty()){

            new Alert(Alert.AlertType.ERROR,"Please Enter Email.!!").show();

        } else if (userPassword.getText().isEmpty()) {

            new Alert(Alert.AlertType.ERROR, "Please Enter Password.!!").show();

        }else if(user == null){

            new Alert(Alert.AlertType.ERROR,"User Not Found.!!").show();

        }else if(userPassword.getText().equals(basicTextEncryptor.decrypt(user.getPassword()))) {

            UserInfo.getInstance().setUser(user);

            if(user.getRole().equals("Admin")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Homepage.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.getIcons().add(new Image("img/Logo for Exe.png"));
                stage.setTitle("Clothify Store");
                stage.setScene(new Scene(root));
                stage.show();

            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DefUserHomepage.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.getIcons().add(new Image("img/Logo for Exe.png"));
                stage.setTitle("Clothify Store");
                stage.setScene(new Scene(root));
                stage.show();
            }

            Stage currentStage = (Stage) userEmail.getScene().getWindow();
            currentStage.close();
            new Alert(Alert.AlertType.INFORMATION,"Login Success.!!").show();

        }else{

            new Alert(Alert.AlertType.ERROR,"Invalid Password.!!").show();

        }
    }

    public BasicTextEncryptor setEncription (){
        String key = "#1120GK2";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);
        return basicTextEncryptor;
    }
}
