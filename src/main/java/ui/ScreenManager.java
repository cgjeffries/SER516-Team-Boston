package ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import ui.components.Screen;

import java.util.HashMap;

public class ScreenManager {
    private final HashMap<String, Screen<? extends Parent>> screenHashMap;
    private Scene scene;

    public ScreenManager() {
        this.screenHashMap = new HashMap<>();
    }

    public void addStartScreen(Screen<? extends  Parent> screen) {
        addScreen(screen);
        screen.load();
        scene = new Scene(screen.getRoot(), 800, 600);
    }

    public Scene getScene() {
        return scene;
    }

    public void addScreen(Screen<? extends Parent> screen) {
        addScreen(screen, false);
    }

    public void addScreen(Screen<? extends Parent> screen, boolean shouldSwitch) {
        screenHashMap.put(screen.getName(), screen);
        if (shouldSwitch) {
            switchScreen(screen.getName());
        }
    }

    public Screen<? extends Parent> getScreen(String screen) {
        return this.screenHashMap.get(screen);
    }

    public void switchScreen(String screen) {
        Screen<? extends Parent> targetScreen = getScreen(screen);
        targetScreen.load();
        scene.setRoot(targetScreen.getRoot());
    }
}
