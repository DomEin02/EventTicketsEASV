package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.DAL.EventDAO;
import dk.easv.easvticketsystem.model.Event;
import dk.easv.easvticketsystem.model.User;
import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.time.LocalDateTime;
import dk.easv.easvticketsystem.DAL.EventDAO;

public class AdminController {

    @FXML private VBox userContainer;
    @FXML private VBox eventContainer;
    @FXML private Label userLabel;

    @FXML
    public void initialize() {

        // USERS – kan stadig hardcode for nu, eller hent fra DB senere
        addUser("Admin User","admin","admin@easv.dk","Admin","2024-01-01");
        addUser("John Coordinator","coord1","john@easv.dk","Coordinator","2024-01-15");
        addUser("Sarah Events","coord2","sarah@easv.dk","Coordinator","2024-02-01");

        // EVENTS – Get from DB
        try {
            EventDAO eventDAO = new EventDAO();
            for (Event event : eventDAO.getAllEvents()) {
                addEvent(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Could not get events");
            alert.setContentText("An error occurred while loading events from the database.:\n" + e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void createUser() {
        SceneManager.load("createCoordinator.fxml");
    }

    @FXML
    private void logout() {
        SceneManager.load("login.fxml");
    }

    private void openEditUser(User user) {
        UserEditorController.selectedUser = user;
        SceneManager.load("userEditor.fxml");
    }

    private void addUser(String name, String username, String email, String role, String created) {

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("row");

        Label l1 = new Label(name);     l1.setPrefWidth(160);
        Label l2 = new Label(username); l2.setPrefWidth(120);
        Label l3 = new Label(email);    l3.setPrefWidth(200);
        Label l4 = new Label(role);     l4.setPrefWidth(120);
        Label l5 = new Label(created);  l5.setPrefWidth(120);

        Button edit = new Button("Edit");
        edit.getStyleClass().add("secondary-btn");
        edit.setOnAction(e -> SceneManager.load("userEditor.fxml"));

        Button delete = new Button("Delete");
        delete.getStyleClass().add("secondary-btn");
        delete.setOnAction(e -> userContainer.getChildren().remove(row));

        HBox actions = new HBox(8, edit, delete);
        actions.setPrefWidth(140);

        row.getChildren().addAll(l1,l2,l3,l4,l5,actions);
        userContainer.getChildren().add(row);
    }

    private void addEvent(Event event) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("row");

        Label l1 = new Label(event.getTitle());
        Label l2 = new Label(event.getStartDateTime().toLocalDate().toString());
        Label l3 = new Label(event.getLocation());
        Label l4 = new Label("Coordinator"); // hent fra event.getCoordinator(), hvis du har det
        Label l5 = new Label(event.getMaxCapacity() + " tickets");

        Button delete = new Button("Delete");
        delete.getStyleClass().add("secondary-btn");
        delete.setOnAction(e -> {
            eventContainer.getChildren().remove(row);
            try {
                new EventDAO().deleteEvent(event.getEventId());
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText("Could not delete event");
                alert.setContentText("An error occurred while deleting the event:\n" + ex.getMessage());
                alert.showAndWait();
            }
        });

        Button edit = new Button("Edit");
        edit.getStyleClass().add("secondary-btn");
        edit.setOnAction(e -> {
            EventEditorController.selectedEvent = event;
            SceneManager.load("eventeditor.fxml");
        });

        HBox actions = new HBox(8, edit, delete);
        actions.setPrefWidth(120);

        row.getChildren().addAll(l1, l2, l3, l4, l5, actions);
        eventContainer.getChildren().add(row);
    }
}