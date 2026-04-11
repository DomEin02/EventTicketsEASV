package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;

public class BaseController {

    @FXML
    protected void logout() {
        SceneManager.load("login.fxml");
    }
}
