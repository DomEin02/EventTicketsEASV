package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.UUID;

public class FreeTicketController {

    @FXML private Label ticketId;

    @FXML
    private void generate() {
        ticketId.setText("FREE-" + UUID.randomUUID());
    }

    @FXML
    private void back() {
        SceneManager.load("coordinator.fxml");
    }
}