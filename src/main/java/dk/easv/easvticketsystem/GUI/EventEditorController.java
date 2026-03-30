package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.DAL.EventDAO;
import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import dk.easv.easvticketsystem.model.Event;
import java.time.LocalDateTime;

public class EventEditorController {

    public static Event selectedEvent;

    @FXML private TextField titleField;
    @FXML private TextField dateField;
    @FXML private TextField timeField;
    @FXML private TextField locationField;
    @FXML private TextField maxTicketsField;

    @FXML
    public void initialize() {
        if (selectedEvent != null) {
            titleField.setText(selectedEvent.getTitle());
            dateField.setText(selectedEvent.getStartDateTime().toLocalDate().toString());
            timeField.setText(selectedEvent.getStartDateTime().toLocalTime().toString());
            locationField.setText(selectedEvent.getLocation());
            maxTicketsField.setText(String.valueOf(selectedEvent.getMaxCapacity()));
        }
    }

    @FXML
    private void saveChanges() {

        try {
            String title = titleField.getText();
            String date = dateField.getText();
            String time = timeField.getText();
            String location = locationField.getText();
            int maxTickets = Integer.parseInt(maxTicketsField.getText());

            // Convert to LocalDateTime
            LocalDateTime startDateTime = LocalDateTime.parse(date + "T" + time);

            // Update selected event
            selectedEvent.setTitle(title);
            selectedEvent.setStartDateTime(startDateTime);
            selectedEvent.setLocation(location);
            selectedEvent.setMaxCapacity(maxTickets);

            EventDAO dao = new EventDAO();

            dao.updateEvent(selectedEvent);

            // Reset selected event
            selectedEvent = null;

            SceneManager.load("coordinator.fxml");
        }
        catch (Exception e) {

            e.printStackTrace();

        }
    }

    @FXML
    private void cancel() {
        SceneManager.load("coordinator.fxml");
    }
}