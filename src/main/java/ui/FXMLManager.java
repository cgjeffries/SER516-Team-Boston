package ui;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class FXMLManager {
    private static FXMLManager manager;

    private FXMLManager() {

    }

    public static FXMLManager getInstance() {
        if (manager == null) {
            manager = new FXMLManager();
        }
        return manager;
    }

    public void load(String fxmlPath, Object o) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        loader.setRoot(o);
        loader.setController(o);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
