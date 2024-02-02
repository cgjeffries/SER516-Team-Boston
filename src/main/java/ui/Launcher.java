package ui;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.screens.MetricSelection;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage)  {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        Scene scene = new Scene(new MetricSelection(), 800, 600);
        stage.setTitle("SER516 Team Boston");
        stage.setScene(scene);
        stage.show();
    }
}