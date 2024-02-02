package ui.util;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class FXMLManager {

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
