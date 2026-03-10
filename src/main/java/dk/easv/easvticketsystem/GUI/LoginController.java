package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label info;

    @FXML
    private void login() {

        if (username.getText().equalsIgnoreCase("admin"))
            SceneManager.load("admin.fxml");

        else if (username.getText().equalsIgnoreCase("coord"))
            SceneManager.load("coordinator.fxml");

        else
            info.setText("Unknown user");
    }
}