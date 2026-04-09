package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.DAL.EventCoordinatorDAO;
import dk.easv.easvticketsystem.DAL.UserDAO;
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
import dk.easv.easvticketsystem.DAL.EventDAO;

import java.util.List;

public class AdminController {

    @FXML private VBox userContainer;
    @FXML private VBox eventContainer;
    @FXML private Label userLabel;
    @FXML private javafx.scene.control.TextField userSearchField;
    @FXML private javafx.scene.control.ComboBox<String> roleFilter;

    private List<User> allUsers;

    @FXML
    public void initialize() {
        try {
            UserDAO dao = new UserDAO();
            allUsers = dao.getAllUsers();
            loadUsers(allUsers);
            roleFilter.getItems().addAll("Admin", "Coordinator");

            EventDAO eventDAO = new EventDAO();
            List<Event> events = eventDAO.getAllEvents();
            eventContainer.getChildren().clear();

            for (Event e : events) {

                addEvent(e);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUsers(List<User> users) {

        userContainer.getChildren().clear();

        for (User u : users) {
          addUser(u);
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

    private void addUser(User user) {

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("row");

        Label l1 = new Label(user.getName());
        l1.setPrefWidth(160);

        Label l2 = new Label(user.getUsername());
        l2.setPrefWidth(120);

        Label l3 = new Label(user.getEmail());
        l3.setPrefWidth(200);

        Label l4 = new Label(user.getRole());
        l4.setPrefWidth(120);

        Label l5 = new Label(user.getCreated());
        l5.setPrefWidth(120);

        Button edit = new Button("Edit");

        edit.getStyleClass().add("secondary-btn");

        edit.setOnAction(e -> openEditUser(user));

        Button delete = new Button("Delete");

        delete.getStyleClass().add("secondary-btn");

        delete.setOnAction(e -> userContainer.getChildren().remove(row));

        HBox actions = new HBox(8, edit, delete);

        actions.setPrefWidth(140);

        row.getChildren().addAll(l1,l2,l3,l4,l5, actions);

        userContainer.getChildren().add(row);
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

        String coordinatorName = "Not Assigned";
        try {
            EventCoordinatorDAO dao = new EventCoordinatorDAO();

            coordinatorName = dao.getCoordinatorName(event.getEventId());
        }
        catch (Exception ex) {

            ex.printStackTrace();

        }

        Label l4 = new Label(coordinatorName);
        l4.setPrefWidth(140);

        Label l5 = new Label(event.getMaxCapacity() + " tickets");
        l5.setPrefWidth(100);

        // ASSIGN BUTTON (Admin requirement)
        Button assign = new Button("Assign");
        assign.getStyleClass().add("secondary-btn");

        assign.setOnAction(e -> openAssignCoordinator(event));

        // DELETE BUTTON
        Button delete = new Button("Delete");
        delete.getStyleClass().add("secondary-btn");

        delete.setOnAction(e -> {

            try {
                new EventDAO().deleteEvent(event.getEventId());
                eventContainer.getChildren().remove(row);

            }

            catch (Exception ex) {

                ex.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText("Could not delete event");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

        });

        HBox actions = new HBox(8, assign, delete);
        actions.setPrefWidth(160);
        row.getChildren().addAll(l1,l2,l3,l4,l5,actions);

        eventContainer.getChildren().add(row);
    }

    private void openAssignCoordinator(Event event) {

        AssignCoordinatorController.selectedEvent = event;

        SceneManager.load("assignCoordinator.fxml");

    }

    @FXML
    private void filterUsers() {

        String search = userSearchField.getText().toLowerCase();

        String role = roleFilter.getValue();

        List<User> filtered = new java.util.ArrayList<>();

        for (User u : allUsers) {
            boolean matchesSearch = u.getName().toLowerCase().contains(search) || u.getUsername().toLowerCase().contains(search);

            boolean matchesRole = role == null || u.getRole().equals(role);

            if (matchesSearch && matchesRole) {
                filtered.add(u);
            }
        }
        loadUsers(filtered);
    }

    @FXML
    private void clearUserFilters() {
        userSearchField.clear();
        roleFilter.setValue(null);
        loadUsers(allUsers);
    }
}