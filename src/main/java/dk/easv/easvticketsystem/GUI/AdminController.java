package dk.easv.easvticketsystem.GUI;

import dk.easv.easvticketsystem.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AdminController extends BaseController {

    @FXML private VBox sidebar;
    @FXML private StackPane contentPane;

    private Pane userView;
    private Pane eventView;

    private boolean sidebarOpen = true;

    @FXML
    public void initialize() {

        // load views
        userView = loadView("/fxml/UserManagementView.fxml");
        eventView = loadView("/fxml/EventsOverviewView.fxml");

        // default view
        contentPane.getChildren().setAll(userView);
    }

    private Pane loadView(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Could not load: " + path, e);
        }
    }

    @FXML
    private void showUsers() {
        contentPane.getChildren().setAll(userView);
    }

    @FXML
    private void showEvents() {
        contentPane.getChildren().setAll(eventView);
    }

    @FXML
    private void toggleSidebar() {

        sidebarOpen = !sidebarOpen;

        if (sidebarOpen) {
            sidebar.setPrefWidth(200);
            sidebar.getChildren().forEach(node -> node.setVisible(true));
        } else {
            sidebar.setPrefWidth(40);

            // behold kun toggle-knap
            for (int i = 1; i < sidebar.getChildren().size(); i++) {
                sidebar.getChildren().get(i).setVisible(false);
            }
        }
    }
}