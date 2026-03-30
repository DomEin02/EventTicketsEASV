package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import dk.easv.easvticketsystem.GUI.model.Event;

public class EventEditorController {

    @FXML private TextField titleField;
    @FXML private TextField dateField;
    @FXML private TextField timeField;
    @FXML private TextField locationField;
    @FXML private TextField maxTicketsField;

    @FXML
    private void saveEvent() {

        String title = titleField.getText();
        String date = dateField.getText();
        String time = timeField.getText();
        String location = locationField.getText();
        int maxTickets;

        try {
            maxTickets = Integer.parseInt(maxTicketsField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
            return;
        }

        Event event = new Event(title, date, time, location, maxTickets);

        System.out.println("EVENT CREATED:");
        System.out.println(title + " | " + date + " " + time + " | " + location + " | " + maxTickets);

        SceneManager.load("coordinator.fxml");
    }

    @FXML
    private void cancel() {
        SceneManager.load("coordinator.fxml");
    }
}