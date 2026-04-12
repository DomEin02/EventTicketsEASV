package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.DAL.UserDAO;
import dk.easv.easvticketsystem.model.User;
import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.time.LocalDate;

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
            createdField.setText(
                    selectedUser.getCreated() != null
                            ? selectedUser.getCreated().toString()
                            : ""
            );
        }
    }

    @FXML
    private void saveUser() {
        try {
            if (selectedUser == null)
                selectedUser = new User();

            selectedUser.setName(nameField.getText());
            selectedUser.setUsername(usernameField.getText());
            selectedUser.setEmail(emailField.getText());
            selectedUser.setPassword(passwordField.getText());
            selectedUser.setRole(roleBox.getValue());
            LocalDate created = createdField.getText().isEmpty()
                    ? LocalDate.now()
                    : LocalDate.parse(createdField.getText());

            selectedUser.setCreated(created);

            UserDAO dao = new UserDAO();
            dao.createUser(selectedUser);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("User saved successfully!");
            success.showAndWait();

            selectedUser = null;
            SceneManager.load("admin.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Could not save user");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel() {
        selectedUser = null;
        SceneManager.load("UserManagement.fxml");
    }
}