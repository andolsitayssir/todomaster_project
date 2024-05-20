package ToDo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ToDo/views/home.fxml")));

            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.setTitle("My ToDo Application");
            primaryStage.show();

    }
}