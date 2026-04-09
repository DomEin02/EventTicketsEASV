package dk.easv.easvticketsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void load(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/dk/easv/easvticketsystem/" + fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1160, 700);
            scene.getStylesheets().add(SceneManager.class.getResource("/dk/easv/easvticketsystem/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("FAILED TO LOAD: " + fxml);
            e.printStackTrace();
        }
    }
}