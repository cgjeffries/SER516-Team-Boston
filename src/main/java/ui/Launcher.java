package ui;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import settings.Settings;
import ui.components.screens.ScreenManager;
import ui.dialogs.DialogManager;
import ui.dialogs.LoginDialog;
import ui.dialogs.SettingsDialog;
import ui.screens.*;

public class Launcher extends Application {
    private static HostServices hostServices;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        hostServices = getHostServices();
        Settings.get().load();
    }

    @Override
    public void start(Stage stage) {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        DialogManager.add(new SettingsDialog());
        DialogManager.add(new LoginDialog());

        ScreenManager screenManager = new ScreenManager();
        screenManager.initialize(new MetricSelection(screenManager, "Metric Selection", "metric_selection"));
        screenManager.addScreen(new ProjectSelection(screenManager, "project_selection", "project_selection"));
        screenManager.addScreen(new BurndownScreen(screenManager, "Burndown", "metric_configuration"));
        screenManager.addScreen(new CycleTimeScreen(screenManager, "Cycle Time", "metric_configuration"));
        screenManager.addScreen(new LeadTimeScreen(screenManager, "Lead Time", "metric_configuration"));
        screenManager.addScreen(new PBHealthScreen(screenManager, "PB Health", "metric_configuration"));

        stage.setTitle("SER516 Team Boston");
        stage.setScene(screenManager.getScene());
        stage.show();
    }

    @Override
    public void stop() {
        Settings.get().save();
    }

    public static void openUrl(String url) {
        hostServices.showDocument(url);
    }
}