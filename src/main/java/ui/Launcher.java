package ui;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.stage.Stage;
import settings.Settings;
import ui.components.screens.ScreenManager;
import ui.screens.MetricSelection;
import ui.screens.ProjectSelection;
import ui.screens.BurndownScreen;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        Settings.get().load();
    }

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        ScreenManager screenManager = new ScreenManager();
        screenManager.initialize(new MetricSelection(screenManager, "metric_selection"));
        screenManager.addScreen(new ProjectSelection(screenManager, "project_selection"));
        screenManager.addScreen(new BurndownScreen(screenManager, "metric_configuration"));
        stage.setTitle("SER516 Team Boston");
        stage.setScene(screenManager.getScene());
        stage.show();
    }

    @Override
    public void stop() {
        Settings.get().save();
    }
}