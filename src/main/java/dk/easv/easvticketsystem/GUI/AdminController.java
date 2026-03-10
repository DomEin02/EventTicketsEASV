package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminController {

    @FXML private VBox userContainer;
    @FXML private VBox eventContainer;
    @FXML private Label userLabel;

    @FXML
    public void initialize() {

        // USERS
        addUser("Admin User","admin","admin@easv.dk","Admin","2024-01-01");
        addUser("John Coordinator","coord1","john@easv.dk","Coordinator","2024-01-15");
        addUser("Sarah Events","coord2","sarah@easv.dk","Coordinator","2024-02-01");

        // EVENTS
        addEvent("Spring Party","2026-04-15","Main Hall","John","145/200");
        addEvent("Wine Tasting","2026-03-20","EASV Bar","John","32/40");
        addEvent("Tech Conference","2026-05-10","Conference Center","Sarah","287/300");
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

}