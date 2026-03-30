package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.model.User;
import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserEditorController {

    public static User selectedUser;

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleBox;
    @FXML private TextField createdField;

    @FXML
    public void initialize() {

        roleBox.getItems().addAll("Admin","Coordinator");

        if (selectedUser != null) {
            nameField.setText(selectedUser.getName());
            usernameField.setText(selectedUser.getUsername());
            emailField.setText(selectedUser.getEmail());
            roleBox.setValue(selectedUser.getRole());
            createdField.setText(selectedUser.getCreated());
        }
    }

    @FXML
    private void saveUser() {

        if (selectedUser == null)
            selectedUser = new User();

        selectedUser.setName(nameField.getText());
        selectedUser.setUsername(usernameField.getText());
        selectedUser.setEmail(emailField.getText());
        selectedUser.setRole(roleBox.getValue());
        selectedUser.setCreated(createdField.getText());

        System.out.println("Saved user: " + selectedUser.getName());

        selectedUser = null;
        SceneManager.load("admin.fxml");
    }

    @FXML
    private void cancel() {
        selectedUser = null;
        SceneManager.load("admin.fxml");
    }
}