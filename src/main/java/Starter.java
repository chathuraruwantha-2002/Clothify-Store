import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Starter extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("img/Logo for Exe.png"));
        stage.setTitle("Clothify Store");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/Login.fxml"))));
        stage.show();
    }
}
