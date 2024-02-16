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

    public MetricSelection(ScreenManager screenManager, String name) {
        super(screenManager, name);
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
    }

    @Override
    protected void onFocused() {

    }
}
