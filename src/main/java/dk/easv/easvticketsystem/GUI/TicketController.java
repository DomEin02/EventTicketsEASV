package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import dk.easv.easvticketsystem.DAL.TicketDAO;

import java.util.UUID;

public class TicketController {

    @FXML private ComboBox<String> type;
    @FXML private Label ticketId;
    @FXML private Label eventNameLabel;
    private TicketDAO ticketDAO = new TicketDAO();

    @FXML
    public void initialize() {
        type.getItems().addAll("VIP", "Food", "Backstage", "Front Row");

        if(selectedEventName != null)
            eventNameLabel.setText("Event: " + selectedEventName);
    }

    @FXML
    private void generate() {
        String id = UUID.randomUUID().toString();
        ticketId.setText(id);

        String selectedType = type.getValue();
        String eventName = selectedEventName;

        System.out.println("Type: " + selectedType);
        System.out.println("Event " + eventName);

        try {
            int eventId = 1;
            int userId = 1;

            ticketDAO.createTicket(eventId, userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openFreeTicket() {
        SceneManager.load("freeTicket.fxml");
    }

    public static String selectedEventName;
}