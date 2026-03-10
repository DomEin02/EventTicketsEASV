package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateCoordinatorController {

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleBox;
    @FXML private TextField createdField;

    @FXML
    public void initialize() {
        roleBox.setValue("Coordinator");
    }

    @FXML
    private void createCoordinator() {

        String name = nameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = roleBox.getValue();
        String created = createdField.getText();

        System.out.println("COORDINATOR CREATED:");
        System.out.println(name + " | " + username + " | " + email + " | " + role + " | " + created);

        SceneManager.load("admin.fxml");
    }

    @FXML
    private void cancel() {
        SceneManager.load("admin.fxml");
    }
}