package ui.util;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class FXMLManager {

    /**
     * Utility function for loading FXML and attaching it to a root and controller
     *
     * @param fxmlPath   The resource path to the FXML file
     * @param root       The root to attach the loaded FXML to
     * @param controller The controller to set for the FXML
     */
    public static void load(String fxmlPath, Object root, Object controller) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(FXMLManager.class.getResource(fxmlPath)));
        loader.setRoot(root);
        loader.setController(controller);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
