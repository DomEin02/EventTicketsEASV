package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;

public class AdminMenuController {

    @FXML
    private void openUsers() {
        SceneManager.load("userManagementView.fxml");
    }

    @FXML
    private void openEvents() {
        SceneManager.load("eventsOverviewView.fxml");
    }

    @FXML
    public void logout() {
        SceneManager.load("login.fxml");
    }
}
