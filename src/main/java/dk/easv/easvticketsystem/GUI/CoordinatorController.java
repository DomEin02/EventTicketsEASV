package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.BLL.EventManager;
import dk.easv.easvticketsystem.SceneManager;
import dk.easv.easvticketsystem.model.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.image.ImageView;

public class CoordinatorController extends BaseController {

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
        } catch (Exception e) {
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clearFilters() {
        searchField.clear();
        locationFilter.clear();
        capacityFilter.clear();
        dateFilter.setValue(null);
        futureCheck.setSelected(false);
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

            if (!search.isEmpty() && !e.getTitle().toLowerCase().contains(search)) {
                matches = false;
            }

            if (!locationText.isEmpty() && !e.getLocation().toLowerCase().contains(locationText)) {
                matches = false;
            }

            if (selectedDate != null &&
                    !e.getStartDateTime().toLocalDate().equals(selectedDate)) {
                matches = false;
            }

            if (!capacityText.isEmpty()) {
                try {
                    int minCapacity = Integer.parseInt(capacityText);
                    if (e.getMaxCapacity() < minCapacity) {
                        matches = false;
                    }
                } catch (NumberFormatException ignored) {}
            }

            if (futureOnly &&
                    e.getStartDateTime().toLocalDate().isBefore(LocalDate.now())) {
                matches = false;
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

    private void addEvent(Event event, String tickets) {

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("row");

        Label l1 = createLabel(event.getTitle(), 180);
        Label l2 = createLabel(event.getStartDateTime().toLocalDate().toString(), 120);
        Label l3 = createLabel(event.getLocation(), 220);
        Label l4 = createLabel(event.getNotes(), 120);
        Label l5 = createLabel(tickets, 100);

        HBox actions = createButtons(event, row);

        row.getChildren().addAll(l1, l2, l3, l4, l5, actions);
        eventContainer.getChildren().add(row);
    }

    private Label createLabel(String text, int width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        return label;
    }

    private HBox createButtons(Event event, HBox row) {

        Button edit = createIconButton("/icons/edit.png", () -> openEditor(event));

        Button delete = createIconButton("/icons/delete.png", () -> confirmDelete(event, row));

        Button ticketsBtn = new Button("Tickets");
        ticketsBtn.getStyleClass().add("primary-btn");
        ticketsBtn.setOnAction(e -> openTickets(event.getTitle()));

        HBox actions = new HBox(8, edit, delete, ticketsBtn);
        actions.setPrefWidth(180);

        return actions;
    }

    private Button createIconButton(String path, Runnable action) {

        ImageView icon = new ImageView(
                new Image(getClass().getResource(path).toExternalForm())
        );

        icon.setFitWidth(16);
        icon.setFitHeight(16);
        icon.setPreserveRatio(true);

        Button btn = new Button();
        btn.setGraphic(icon);
        btn.getStyleClass().add("secondary-btn");

        btn.setOnAction(e -> action.run());

        return btn;
    }

    private void confirmDelete(Event event, HBox row) {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete event?");
        confirm.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                EventManager manager = new EventManager();
                manager.deleteEvent(event.getEventId());
                eventContainer.getChildren().remove(row);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
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