package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.BLL.CoordinatorManager;
import dk.easv.easvticketsystem.BLL.EventManager;
import dk.easv.easvticketsystem.model.Event;
import dk.easv.easvticketsystem.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class EventsOverviewController extends BaseController {

    private EventManager eventManager;
    private CoordinatorManager coordinatorManager;

    @FXML
    private VBox eventContainer;

    @FXML
    public void initialize() {
        try {
            eventManager = new EventManager();
            coordinatorManager = new CoordinatorManager();

            List<Event> events = eventManager.getAllEvents();

            eventContainer.getChildren().clear();

            for (Event e : events) {

                addEvent(e);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void addEvent(Event event) {

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("row");

        Label l1 = new Label(event.getTitle());
        l1.setPrefWidth(180);

        Label l2 = new Label(event.getStartDateTime().toLocalDate().toString());
        l2.setPrefWidth(120);

        Label l3 = new Label(event.getLocation());
        l3.setPrefWidth(220);

        // Coordinator navn
        String coordinatorName = "Not Assigned";

        try {
            coordinatorName = coordinatorManager.getCoordinatorName(event.getEventId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Label l4 = new Label(coordinatorName);
        l4.setPrefWidth(140);

        Label l5 = new Label(event.getMaxCapacity() + " tickets");
        l5.setPrefWidth(100);

        // ======================
        // ASSIGN BUTTON
        // ======================
        Button assign = new Button();

        Image assignImg = new Image(getClass().getResource("/Icons/edit.png").toExternalForm());
        ImageView assignView = new ImageView(assignImg);

        assignView.setFitWidth(16);
        assignView.setFitHeight(16);
        assignView.setPreserveRatio(true);

        assign.setGraphic(assignView);
        assign.getStyleClass().add("secondary-btn");

        assign.setOnAction(e -> openAssignCoordinator(event));

        // ======================
        // DELETE BUTTON
        // ======================
        Button delete = new Button();

        Image deleteImg = new Image(getClass().getResource("/Icons/delete.png").toExternalForm());
        ImageView deleteView = new ImageView(deleteImg);

        deleteView.setFitWidth(16);
        deleteView.setFitHeight(16);
        deleteView.setPreserveRatio(true);

        delete.setGraphic(deleteView);
        delete.getStyleClass().add("secondary-btn");

        delete.setOnAction(e -> {

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete Event");
            confirm.setHeaderText("Are you sure?");
            confirm.setContentText("This event will be permanently deleted.");

            confirm.showAndWait().ifPresent(response -> {

                if (response == ButtonType.OK) {
                    try {
                        EventManager manager = new EventManager();

                        manager.deleteEvent(event.getEventId());

                        eventContainer.getChildren().remove(row);

                    } catch (Exception ex) {

                        ex.printStackTrace();

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Database Error");
                        alert.setHeaderText("Could not delete event");
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                    }
                }
            });

        });

        HBox actions = new HBox(8, assign, delete);
        actions.setPrefWidth(160);

        row.getChildren().addAll(l1, l2, l3, l4, l5, actions);

        eventContainer.getChildren().add(row);
    }

    private void openAssignCoordinator(Event event) {

        AssignCoordinatorController.selectedEvent = event;

        SceneManager.load("assignCoordinator.fxml");
    }

    @FXML
    public void goToUserManagement(ActionEvent actionEvent) {
        SceneManager.load("UserManagement.fxml");
    }

    @FXML
    private void goToDashboard() {
        SceneManager.load("adminDashboard.fxml");
    }
}
