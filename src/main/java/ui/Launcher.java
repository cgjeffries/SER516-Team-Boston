package ui;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.stage.Stage;
import settings.Settings;
import ui.screens.MetricSelection;
import ui.screens.ProjectSelection;
import ui.util.ScreenManager;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        ScreenManager screenManager = new ScreenManager();
        screenManager.addStartScreen(new MetricSelection(screenManager, "metric_selection"));
        screenManager.addScreen(new ProjectSelection(screenManager, "project_selection"));
        Settings.get().load();
        stage.setTitle("SER516 Team Boston");
        stage.setScene(screenManager.getScene());
        stage.show();
    }
}