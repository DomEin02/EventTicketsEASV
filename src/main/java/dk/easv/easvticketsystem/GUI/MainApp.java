package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.load("login.fxml");
        stage.setTitle("EASV Ticket System");
    }

    public static void main(String[] args) {
        launch();
    }
}