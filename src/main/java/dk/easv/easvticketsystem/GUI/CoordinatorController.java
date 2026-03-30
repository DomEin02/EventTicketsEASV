package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.DAL.EventDAO;
import dk.easv.easvticketsystem.SceneManager;
import dk.easv.easvticketsystem.model.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CoordinatorController {

    @FXML private VBox eventContainer;

    @FXML
    public void initialize() {
        try {
            EventDAO dao = new EventDAO();

            List<Event> events = dao.getAllEvents();

            for (Event e : events) {

                int sold = dao.getTicketCountForEvent(e.getEventId());
                addEvent(e, sold + "/" + e.getMaxCapacity());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createEvent() {
        EventEditorController.selectedEvent = null;
        SceneManager.load("eventEditor.fxml");
    }

    @FXML
    private void logout() {
        SceneManager.load("login.fxml");
    }

    private void addEvent(Event event, String tickets) {

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("row");

        Label l1 = new Label(event.getTitle());
        l1.setPrefWidth(180);

        Label l2 = new Label(event.getStartDateTime().toLocalDate().toString());
        l2.setPrefWidth(120);

        Label l3 = new Label(event.getLocation());
        l3.setPrefWidth(220);

        Label l4 = new Label(tickets);
        l4.setPrefWidth(100);

        Button edit = new Button("Edit");
        edit.getStyleClass().add("secondary-btn");
        edit.setOnAction(e -> openEditor(event));

        Button delete = new Button("Delete");
        delete.getStyleClass().add("secondary-btn");
        delete.setOnAction(e -> eventContainer.getChildren().remove(row));

        Button ticketsBtn = new Button("Tickets");
        ticketsBtn.getStyleClass().add("primary-btn");
        ticketsBtn.setOnAction(e -> openTickets(event.getTitle()));

        HBox actions = new HBox(8, edit, delete, ticketsBtn);
        actions.setPrefWidth(180);

        row.getChildren().addAll(l1, l2, l3, l4, actions);

        eventContainer.getChildren().add(row);
    }

    private void openEditor(Event event) {

        EventEditorController.selectedEvent = event;

        SceneManager.load("eventEditor.fxml");
    }

    private void openTickets(String eventName) {
        TicketController.selectedEventName = eventName;
        SceneManager.load("ticket.fxml");
    }
}