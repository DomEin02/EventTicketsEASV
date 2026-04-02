package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.DAL.EventDAO;
import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import dk.easv.easvticketsystem.model.Event;
import java.time.LocalDateTime;

public class EventEditorController {

    public static Event selectedEvent;

    @FXML
    private TextField titleField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField maxTicketsField;

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
            //Get input
            String title = titleField.getText();
            String date = dateField.getText();
            String time = timeField.getText();
            String location = locationField.getText();
            String maxTicketsStr = maxTicketsField.getText().trim();

            //Check empty fields
            if (title.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty() || maxTicketsStr.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("All fields must be filled in");
                alert.showAndWait();
                return;
            }

            //Parse maxTickets and check if > 0
            int maxTickets;
            try {
                maxTickets = Integer.parseInt(maxTicketsStr);
                if (maxTickets <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Max tickets must be positive");
                alert.showAndWait();
                return;
            }

            //Parse date and time
            LocalDateTime startDateTime;
            try {
                startDateTime = LocalDateTime.parse(date + "T" + time + ":00");
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Invalid date or time format");
                alert.setContentText("Expected format: YYYY-MM-DD for date and HH:MM for time");
                alert.showAndWait();
                return;
            }

            //Save in DB
            EventDAO eventDAO = new EventDAO();

            if (selectedEvent != null) {
                selectedEvent.setTitle(title);
                selectedEvent.setStartDateTime(startDateTime);
                selectedEvent.setLocation(location);
                selectedEvent.setMaxCapacity(maxTickets);
                eventDAO.updateEvent(selectedEvent);
            } else {
                Event newEvent = new Event(
                        0, title,
                        startDateTime,
                        null,
                        location,
                        "",
                        "",
                        maxTickets);
                int generatedId = eventDAO.createEvent(newEvent);
                newEvent.setEventId(generatedId);
            }

            selectedEvent = null;
            SceneManager.load("coordinator.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Could not save event");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelChanges() {
        //Go back without saving
        selectedEvent = null;
        SceneManager.load("coordinator.fxml");
    }
}