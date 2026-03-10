package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CoordinatorController {

    @FXML private VBox eventContainer;

    @FXML
    public void initialize() {

        addEvent("Dog Show","2026-04-05","EASV Field","54/100");
        addEvent("Wine Tasting","2026-03-20","EASV Bar","32/40");
        addEvent("Tech Conference","2026-05-10","Conference Center","287/300");
    }

    @FXML
    private void createEvent() {
        SceneManager.load("eventEditor.fxml");
    }

    @FXML
    private void logout() {
        SceneManager.load("login.fxml");
    }

    private void addEvent(String title, String date, String location, String tickets) {

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("row");

        Label l1 = new Label(title);
        l1.setPrefWidth(180);

        Label l2 = new Label(date);
        l2.setPrefWidth(120);

        Label l3 = new Label(location);
        l3.setPrefWidth(220);

        Label l4 = new Label(tickets);
        l4.setPrefWidth(100);

        Button edit = new Button("Edit");
        edit.getStyleClass().add("secondary-btn");
        edit.setOnAction(e -> SceneManager.load("eventEditor.fxml"));

        Button delete = new Button("Delete");
        delete.getStyleClass().add("secondary-btn");
        delete.setOnAction(e -> eventContainer.getChildren().remove(row));

        Button ticketsBtn = new Button("Tickets");
        ticketsBtn.getStyleClass().add("primary-btn");
        ticketsBtn.setOnAction(e -> openTickets(title));

        HBox actions = new HBox(8, edit, delete, ticketsBtn);
        actions.setPrefWidth(180);

        row.getChildren().addAll(l1,l2,l3,l4,actions);
        eventContainer.getChildren().add(row);
    }

    private void openTickets(String eventName) {
        TicketController.selectedEventName = eventName;
        SceneManager.load("ticket.fxml");
    }
}