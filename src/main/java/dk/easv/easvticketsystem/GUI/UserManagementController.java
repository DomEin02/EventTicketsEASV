package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.BLL.UserManager;
import dk.easv.easvticketsystem.model.User;
import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class UserManagementController extends BaseController {

    @FXML private VBox userContainer;
    @FXML private TextField userSearchField;
    @FXML private ComboBox<String> roleFilter;

    private List<User> allUsers;
    private UserManager userManager;

    @FXML
    public void initialize() {

        try {
            userManager = new UserManager();

            allUsers = userManager.getAllUsers();
            loadUsers(allUsers);

            roleFilter.getItems().addAll("Admin", "Coordinator");

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
        SceneManager.load("createUser.fxml");
    }

    private void openEditUser(User user) {
        UserEditorController.selectedUser = user;
        SceneManager.load("userEditor.fxml");
    }

    private void deleteUser(User user, HBox row) {

        try {
            userManager.deleteUser(user.getUserId());

            allUsers = userManager.getAllUsers();
            loadUsers(allUsers);

            showSuccess("User deleted successfully!");

        } catch (Exception ex) {
            ex.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not delete user");
            alert.setContentText("Could not delete user. Please try again");
            alert.showAndWait();
        }
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

        Label l5 = new Label(
                user.getCreated() != null
                        ? user.getCreated().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        : ""
        );
        l5.setPrefWidth(120);

        // =========================
        // EDIT ICON
        // =========================
        Button edit = new Button();

        Image editImg = new Image(getClass().getResource("/icons/edit.png").toExternalForm());
        ImageView editView = new ImageView(editImg);

        editView.setFitWidth(16);
        editView.setFitHeight(16);
        editView.setPreserveRatio(true);

        edit.setGraphic(editView);
        edit.getStyleClass().add("secondary-btn");

        edit.setOnAction(e -> openEditUser(user));

        // =========================
        // DELETE ICON
        // =========================
        Button delete = new Button();

        Image deleteImg = new Image(getClass().getResource("/icons/delete.png").toExternalForm());
        ImageView deleteView = new ImageView(deleteImg);

        deleteView.setFitWidth(16);
        deleteView.setFitHeight(16);
        deleteView.setPreserveRatio(true);

        delete.setGraphic(deleteView);
        delete.getStyleClass().add("secondary-btn");

        delete.setOnAction(e -> {

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete User");
            confirm.setHeaderText("Are you sure?");
            confirm.setContentText("This user will be permanently deleted.");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    deleteUser(user, row);
                }
            });
        });

        HBox actions = new HBox(8, edit, delete);
        actions.setPrefWidth(140);

        row.getChildren().addAll(l1, l2, l3, l4, l5, actions);

        userContainer.getChildren().add(row);
    }

    @FXML
    private void filterUsers() {

        String search = userSearchField.getText().toLowerCase();
        String role = roleFilter.getValue();

        List<User> filtered = new ArrayList<>();

        for (User u : allUsers) {

            boolean matchesSearch =
                    u.getName().toLowerCase().contains(search) ||
                            u.getUsername().toLowerCase().contains(search);

            boolean matchesRole =
                    role == null || u.getRole().equals(role);

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

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
