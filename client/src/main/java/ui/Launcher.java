package ui;

import atlantafx.base.theme.PrimerLight;
import bostonclient.BostonClient;
import bostonclient.BostonClientOptions;
import bostonhttp.api.APIWrapperBehaviors;
import bostonhttp.models.AuthToken;
import bostonhttp.util.TokenStore;
import javafx.application.Application;
import javafx.stage.Stage;
import settings.Settings;
import taiga.TaigaClient;
import ui.components.screens.ScreenManager;
import ui.dialogs.DialogManager;
import ui.dialogs.LoginDialog;
import ui.dialogs.SettingsDialog;
import ui.screens.*;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        TokenStore.setTokenRetriever(() -> Settings.get().getAppModel().getTokens());
        TokenStore.setTokenSaver(tokens -> Settings.get().getAppModel().setTokens(tokens));
        Settings.get().load();
        if(Settings.get().getAppModel().getTokens() != null) {
            TaigaClient.setDefaultAPIBehaviors(new APIWrapperBehaviors().withBaseApiUrlResolver(
                    () -> Settings.get().getAppModel().getApiURL())
                .withAuthToken(new AuthToken(Settings.get().getAppModel().getTokens().getAuth())));
        }
        else{
            TaigaClient.setDefaultAPIBehaviors(new APIWrapperBehaviors().withBaseApiUrlResolver(
                    () -> Settings.get().getAppModel().getApiURL()));
        }
        BostonClient.buildClient(new BostonClientOptions().withRouter("http://localhost:8000/"));
        Settings.get().getAppModel().loadUser();
    }

    @Override
    public void start(Stage stage) {

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        DialogManager.add(new SettingsDialog());
        DialogManager.add(new LoginDialog(getHostServices()));

        ScreenManager screenManager = new ScreenManager();
        screenManager.initialize(new MetricSelection(screenManager, "Metric Selection", "metric_selection"));
        screenManager.addScreen(new ProjectSelection(screenManager, "project_selection", "project_selection"));
        screenManager.addScreen(new BurndownScreen(screenManager, "Burndown", "metric_configuration"));
        screenManager.addScreen(new CycleTimeScreen(screenManager, "Cycle Time", "metric_configuration"));
        screenManager.addScreen(new LeadTimeScreen(screenManager, "Lead Time", "metric_configuration"));
        screenManager.addScreen(new PBChangeScreen(screenManager, "Backlog Changes", "metric_configuration"));
        screenManager.addScreen(new PBHealthScreen(screenManager, "PB Health", "metric_configuration"));
        screenManager.addScreen(new GroomRateScreen(screenManager, "Groom Rate", "metric_configuration"));
        screenManager.addScreen(new ScopeChangeScreen(screenManager, "Scope Change", "metric_configuration"));
        screenManager.addScreen(new TaskExcessScreen(screenManager, "Task Excess", "metric_configuration"));
        screenManager.addScreen(new TaskInertiaScreen(screenManager, "Task Inertia", "metric_configuration"));
        screenManager.addScreen(new TaskChurnScreen(screenManager, "Task Churn", "metric_configuration"));
        screenManager.addScreen(new TaskDefectDensityScreen(screenManager, "Task Defect Density", "metric_configuration"));

        stage.setTitle("SER516 Team Boston-Louisville");
        stage.setScene(screenManager.getScene());
        stage.show();
    }

    @Override
    public void stop() {
        Settings.get().save();
    }
}