package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
        String maxTickets = maxTicketsField.getText();

        System.out.println("EVENT CREATED:");
        System.out.println(title + " | " + date + " " + time + " | " + location + " | " + maxTickets);

        SceneManager.load("coordinator.fxml");
    }

    @FXML
    private void cancel() {
        SceneManager.load("coordinator.fxml");
    }
}