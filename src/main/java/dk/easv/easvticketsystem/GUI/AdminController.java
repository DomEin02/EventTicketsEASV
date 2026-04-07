package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.DAL.EventDAO;
import dk.easv.easvticketsystem.DAL.UserDAO;
import dk.easv.easvticketsystem.model.Event;
import dk.easv.easvticketsystem.model.User;
import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

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
            for (Event e : events) {
                int sold = eventDAO.getTicketCountForEvent(e.getEventId());

                addEvent(e.getTitle(), e.getStartDateTime().toLocalDate().toString(), e.getLocation(), "Coordinator", sold + "/" + e.getMaxCapacity());
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

    private void addEvent(String title, String date, String location, String coordinator, String tickets) {

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("row");

        Label l1 = new Label(title);       l1.setPrefWidth(180);
        Label l2 = new Label(date);        l2.setPrefWidth(120);
        Label l3 = new Label(location);    l3.setPrefWidth(220);
        Label l4 = new Label(coordinator); l4.setPrefWidth(140);
        Label l5 = new Label(tickets);     l5.setPrefWidth(100);

        Button delete = new Button("Delete");
        delete.getStyleClass().add("secondary-btn");
        delete.setOnAction(e -> eventContainer.getChildren().remove(row));

        HBox actions = new HBox(delete);
        actions.setPrefWidth(120);

        row.getChildren().addAll(l1,l2,l3,l4,l5,actions);
        eventContainer.getChildren().add(row);
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