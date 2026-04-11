package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;

public class AdminMenuController {

    @FXML
    private void openUsers() {
        SceneManager.load("UserManagementView.fxml");
    }

    @FXML
    private void openEvents() {
        SceneManager.load("EventsOverviewView.fxml");
    }

    @FXML
    public void logout() {
        SceneManager.load("login.fxml");
    }
}
