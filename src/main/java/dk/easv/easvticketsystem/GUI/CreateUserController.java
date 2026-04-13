package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.BLL.UserManager;
import dk.easv.easvticketsystem.SceneManager;
import dk.easv.easvticketsystem.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class CreateUserController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleBox;
    @FXML
    private DatePicker createdDatePicker;

    @FXML
    public void initialize() {
        roleBox.getItems().addAll("Coordinator", "Admin");
        roleBox.setValue("Coordinator");
        createdDatePicker.setValue(LocalDate.now());
    }

    @FXML
    private void createUser() {
        try {
            String name = nameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String role = roleBox.getValue();
            LocalDate created = createdDatePicker.getValue();

            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("All fields must be filled");
                alert.showAndWait();
                return;
            }

            User user = new User(name, username, email, password, role, created);

            UserManager manager = new UserManager();
            manager.createUser(user);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Coordinator created successfully!");
            success.showAndWait();

            SceneManager.load("userManagement.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error creating user");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

        @FXML
        private void cancel () {
            SceneManager.load("userManagement.fxml");
        }
}