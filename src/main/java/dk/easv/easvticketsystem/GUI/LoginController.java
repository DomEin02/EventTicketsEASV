package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.DAL.UserDAO;
import dk.easv.easvticketsystem.model.User;
import dk.easv.easvticketsystem.utils.PasswordUtil;
import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label info;

    @FXML
    private void login() {

        String usernameInput = username.getText().trim().toLowerCase();
        String passwordInput = password.getText().trim();

        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            info.setText("Please enter username and password!");
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByUsername(usernameInput);

            if (user == null) {
                info.setText("Invalid username or password!");
                return;
            }

            String hashedInput = PasswordUtil.hashPassword(passwordInput, user.getSalt());

            System.out.println("Input password: " + passwordInput);
            System.out.println("Salt from DB: " + user.getSalt());
            System.out.println("Hashed input: " + hashedInput);
            System.out.println("Stored hash: " + user.getPasswordHash());

            if (hashedInput.equals(user.getPasswordHash())) {
                info.setText("");

                if (user.getRole().equalsIgnoreCase("Admin")) {
                    System.out.println("LOGIN SUCCESS - role: " + user.getRole());
                    SceneManager.load("adminDashboard.fxml");
                } else {
                    SceneManager.load("coordinator.fxml");
                }

            } else {
                info.setText("Invalid username or password!");
            }
        } catch (Exception e) {
            info.setText("System error. Please try again later!");
            e.printStackTrace();
        }
    }

}