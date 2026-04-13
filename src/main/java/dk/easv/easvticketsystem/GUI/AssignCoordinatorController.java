package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.BLL.CoordinatorManager;
import dk.easv.easvticketsystem.BLL.UserManager;
import dk.easv.easvticketsystem.SceneManager;
import dk.easv.easvticketsystem.model.Event;
import dk.easv.easvticketsystem.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.List;

public class AssignCoordinatorController {

    public static Event selectedEvent;

    @FXML
    private ComboBox<User> coordinatorCombo;

    @FXML
    public void initialize() {

        try {
            UserManager manager = new UserManager();
            List<User> users = manager.getAllUsers();
            for (User u : users) {
                if (u.getRole().equals("Coordinator")) {
                    coordinatorCombo.getItems().add(u);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void assign() {
        try {
            User selected = coordinatorCombo.getValue();

            if (selected != null) {
                CoordinatorManager manager = new CoordinatorManager();

                manager.assignCoordinator(selectedEvent.getEventId(), selected.getUserId());
            }
            SceneManager.load("eventsOverview.fxml");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() {
        SceneManager.load("eventsOverview.fxml");
    }
}
