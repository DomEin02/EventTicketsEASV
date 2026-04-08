package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.DAL.UserDAO;
import dk.easv.easvticketsystem.SceneManager;
import dk.easv.easvticketsystem.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateCoordinatorController {

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
    private TextField createdField;

    @FXML
    public void initialize() {
        roleBox.getItems().addAll("Coordinator", "Admin");
        roleBox.setValue("Coordinator");
    }

    @FXML
    private void createCoordinator() {
        try {
            String name = nameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String role = roleBox.getValue();
            String created = createdField.getText();

            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("All fields must be filled");
                alert.showAndWait();
                return;
            }

            User user = new User(name, username, email, password, role, created);

            UserDAO dao = new UserDAO();
            dao.createUser(user);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setHeaderText("Coordinator created successfully!");
            success.showAndWait();

            SceneManager.load("admin.fxml");

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
            SceneManager.load("admin.fxml");
        }
    }