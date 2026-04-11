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

        String user = username.getText().trim().toLowerCase();
        String pass = password.getText().trim();

        if (user.equals("admin")) {

            SceneManager.load("AdminDashboard.fxml");

        } else if (user.equals("coord")) {

            SceneManager.load("coordinator.fxml");

        } else {

            info.setText("Unknown user");
        }
    }
}