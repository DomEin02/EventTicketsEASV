package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.BLL.EventManager;
import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import dk.easv.easvticketsystem.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventEditorController {

    public static Event selectedEvent;

    @FXML
    private TextField titleField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField maxTicketsField;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField endTimeField;

    @FXML
    private TextArea notesField;

    @FXML
    private TextArea guidanceField;

    @FXML
    private Label titleLabel;

    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {

        if (selectedEvent != null) {
            // EDIT MODE
            titleLabel.setText("Edit Event");
            saveButton.setText("Save Changes");
            titleField.setText(selectedEvent.getTitle());
            datePicker.setValue(selectedEvent.getStartDateTime().toLocalDate());
            timeField.setText(selectedEvent.getStartDateTime().toLocalTime().toString());
            // END DATETIME
            if (selectedEvent.getEndDateTime() != null) {
                endDatePicker.setValue(selectedEvent.getEndDateTime().toLocalDate());
                endTimeField.setText(selectedEvent.getEndDateTime().toLocalTime().toString());
            }
            locationField.setText(selectedEvent.getLocation());
            notesField.setText(selectedEvent.getNotes());
            guidanceField.setText(selectedEvent.getLocationGuidance());
            maxTicketsField.setText(String.valueOf(selectedEvent.getMaxCapacity()));
        }
        else {
            // CREATE MODE
            titleLabel.setText("Create Event");
            saveButton.setText("Create Event");
        }
    }

    @FXML
    private void saveChanges() {

        try {
            // GET INPUT
            String title = titleField.getText();
            LocalDate date = datePicker.getValue();
            String time = timeField.getText();
            String location = locationField.getText();
            String maxTicketsStr = maxTicketsField.getText().trim();
            String notes = notesField.getText();
            String guidance = guidanceField.getText();
            // VALIDATION
            if (title.isEmpty() || date == null || time.isEmpty() || location.isEmpty() || maxTicketsStr.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("All required fields must be filled");
                alert.showAndWait();
                return;
            }
            // PARSE CAPACITY
            int maxTickets;

            try {
                maxTickets = Integer.parseInt(maxTicketsStr);
                if (maxTickets <= 0)
                    throw new NumberFormatException();
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Max tickets must be positive");
                alert.showAndWait();
                return;
            }
            // PARSE START DATETIME
            LocalDateTime startDateTime;
            try {
                LocalTime parsedTime = LocalTime.parse(time);
                startDateTime = LocalDateTime.of(date, parsedTime);
            }
            catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Invalid start time format (HH:mm)");
                alert.showAndWait();
                return;
            }
            // PARSE END DATETIME
            LocalDateTime endDateTime = null;
            if (endDatePicker.getValue() != null && !endTimeField.getText().isEmpty()) {
                try {
                    LocalTime endTime = LocalTime.parse(endTimeField.getText());
                    endDateTime = LocalDateTime.of(endDatePicker.getValue(), endTime);
                }
                catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText("Invalid end time format (HH:mm)");
                    alert.showAndWait();
                    return;
                }
            }
            // SAVE TO DATABASE
            EventManager manager = new EventManager();
            if (selectedEvent != null) {
                // UPDATE EXISTING
                selectedEvent.setTitle(title);
                selectedEvent.setStartDateTime(startDateTime);
                selectedEvent.setEndDateTime(endDateTime);
                selectedEvent.setLocation(location);
                selectedEvent.setNotes(notes);
                selectedEvent.setLocationGuidance(guidance);
                selectedEvent.setMaxCapacity(maxTickets);

                manager.updateEvent(selectedEvent);
            }
            else {
                // CREATE NEW
                Event newEvent = new Event(0, title, startDateTime, endDateTime, location, notes, guidance, maxTickets);
                int generatedId = manager.createEvent(newEvent);
                newEvent.setEventId(generatedId);
            }
            // RETURN TO SCREEN
            selectedEvent = null;

            SceneManager.load("coordinator.fxml");
        }
        catch (Exception e) {
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