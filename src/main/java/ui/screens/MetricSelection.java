package ui.screens;

import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;

import atlantafx.base.controls.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import settings.Settings;
import ui.components.Icon;
import ui.components.screens.Screen;
import ui.components.screens.ScreenManager;

public class MetricSelection extends Screen<VBox> {
    private static final VBox root = new VBox();
    @FXML
    private Tile burndown_tile;
    @FXML
    private Tile cycletime_tile;
    @FXML
    private Tile leadtime_tile;

    public MetricSelection(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    public VBox getRoot() {
        return root;
    }

    @Override
    public Object getController() {
        return this;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        burndown_tile.setGraphic(new Icon(BoxiconsRegular.LINE_CHART_DOWN, 48));
        burndown_tile.setActionHandler(() -> {
            Settings.get().getAppModel().setSelectedMetric("Burndown");
            screenManager.switchScreen("project_selection");
        });
        // Timer icon conveys cycle time
        cycletime_tile.setGraphic(new Icon(BoxiconsRegular.TIMER, 48));
        cycletime_tile.setActionHandler(() -> {
            Settings.get().getAppModel().setSelectedMetric("Cycle Time");
            screenManager.switchScreen("project_selection");
        });
        // Calendar icon conveys times entering and leaving
        leadtime_tile.setGraphic(new Icon(BoxiconsRegular.CALENDAR, 48));
        leadtime_tile.setActionHandler(() -> {
            Settings.get().getAppModel().setSelectedMetric("Lead Time");
            screenManager.switchScreen("project_selection");
        });
    }

    @Override
    protected void onFocused() {

    }
}
