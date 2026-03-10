package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.UUID;

public class TicketController {

    @FXML private ComboBox<String> type;
    @FXML private Label ticketId;
    @FXML private Label eventNameLabel;

    @FXML
    public void initialize() {
        type.getItems().addAll("VIP", "Food", "Backstage", "Front Row");

        if(selectedEventName != null)
            eventNameLabel.setText("Event: " + selectedEventName);
    }

    @FXML
    private void generate() {
        ticketId.setText(UUID.randomUUID().toString());
    }

    @FXML
    private void openFreeTicket() {
        SceneManager.load("freeTicket.fxml");
    }

    public static String selectedEventName;
}