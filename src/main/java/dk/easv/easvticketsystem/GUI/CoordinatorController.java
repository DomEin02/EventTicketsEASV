package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.BLL.EventManager;
import dk.easv.easvticketsystem.SceneManager;
import dk.easv.easvticketsystem.model.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CoordinatorController {

    @FXML private VBox eventContainer;
    @FXML private TextField searchField;
    @FXML private TextField locationFilter;
    @FXML private TextField capacityFilter;
    @FXML private DatePicker dateFilter;
    @FXML private CheckBox futureCheck;

    private List<Event> allEvents;

    @FXML
    public void initialize() {
        try {
            EventManager manager = new EventManager();
            allEvents = manager.getAllEvents();
            loadEvents(allEvents);
        }
        catch (Exception e) {

            e.printStackTrace();

        }
    }

    private void loadEvents(List<Event> events) {

        eventContainer.getChildren().clear();

        try {
            EventManager manager = new EventManager();

            for (Event e : events) {

                int sold = manager.getTicketCount(e.getEventId());

                addEvent(e, sold + "/" + e.getMaxCapacity());
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clearFilters() {
        // Reset fields
        searchField.clear();
        locationFilter.clear();
        capacityFilter.clear();
        dateFilter.setValue(null);
        futureCheck.setSelected(false);
        // Reload all events
        loadEvents(allEvents);
    }

    @FXML
    private void filterEvents() {

        String search = searchField.getText().toLowerCase();

        String locationText = locationFilter.getText().toLowerCase();

        String capacityText = capacityFilter.getText();

        LocalDate selectedDate = dateFilter.getValue();

        boolean futureOnly = futureCheck.isSelected();

        List<Event> filtered = new ArrayList<>();

        for (Event e : allEvents) {

            boolean matches = true;

            //  Title search
            if (!search.isEmpty()) {
                if (!e.getTitle().toLowerCase().contains(search)) {
                    matches = false;
                }
            }

            //  Location filter
            if (!locationText.isEmpty()) {
                if (!e.getLocation().toLowerCase().contains(locationText)) {
                    matches = false;
                }
            }

            //  Date filter
            if (selectedDate != null) {
                if (!e.getStartDateTime().toLocalDate().equals(selectedDate)) {
                    matches = false;
                }
            }

            //  Capacity filter
            if (!capacityText.isEmpty()) {
                try {
                    int minCapacity = Integer.parseInt(capacityText);
                    if (e.getMaxCapacity() < minCapacity) {
                        matches = false;
                    }
                }
                catch (NumberFormatException ignored) {}
            }

            //  Future events only
            if (futureOnly) {
                if (e.getStartDateTime().toLocalDate().isBefore(LocalDate.now())) {
                    matches = false;
                }
            }
            if (matches) {
                filtered.add(e);

            }
        }
        loadEvents(filtered);
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

        Label l4 = new Label(event.getNotes());
        l4.setPrefWidth(120);

        Label l5 = new Label(tickets);
        l5.setPrefWidth(100);


        //Edit button
        Button edit = new Button("Edit");
        edit.getStyleClass().add("secondary-btn");
        edit.setOnAction(e ->openEditor(event));

        //Delete button
        Button delete = new Button("Delete");
        delete.getStyleClass().add("secondary-btn");
        delete.setOnAction(e -> {
            try {
                EventManager manager = new EventManager();
                manager.deleteEvent(event.getEventId());   // Slet fra DB
                eventContainer.getChildren().remove(row); // Fjern fra GUI
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not delete event");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
                });

        //Ticket button
        Button ticketsBtn = new Button("Tickets");
        ticketsBtn.getStyleClass().add("primary-btn");
        ticketsBtn.setOnAction(e -> openTickets(event.getTitle()));

        HBox actions = new HBox(8, edit, delete, ticketsBtn);
        actions.setPrefWidth(180);

        row.getChildren().addAll(l1, l2, l3, l4, l5, actions);

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