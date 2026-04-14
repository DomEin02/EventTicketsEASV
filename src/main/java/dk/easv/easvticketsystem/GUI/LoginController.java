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

        if (user.isEmpty() || pass.isEmpty()) {
            info.setText("Please enter username and password!");
            return;
        }

        if (user.equals("admin") && pass.equals("Admin123")) {
            info.setText("");
            SceneManager.load("adminDashboard.fxml");

        } else if (user.equals("coord") && pass.equals("Coord123")) {
            info.setText("");
            SceneManager.load("coordinator.fxml");

        } else {
            info.setText("Invalid username or password!");
        }
    }
}