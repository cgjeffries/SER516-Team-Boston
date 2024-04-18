package ui.screens;

import atlantafx.base.controls.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.boxicons.BoxiconsSolid;
import settings.Settings;
import ui.components.Icon;
import ui.components.screens.Screen;
import ui.components.screens.ScreenManager;

public class MetricSelection extends Screen {
    private static final StackPane root = new StackPane();
    @FXML
    private Tile burndown_tile;
    @FXML
    private Tile cycletime_tile;
    @FXML
    private Tile leadtime_tile;
    @FXML
    private Tile pbchange_title;
    @FXML
    private Tile pbhealth_tile;
    @FXML
    private Tile groomrate_tile;
    @FXML
    private Tile scopechange_tile;
    @FXML
    private Tile taskexcess_tile;

    public MetricSelection(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
    }

    @Override
    public StackPane getRoot() {
        return root;
    }

    @Override
    public Object getController() {
        return this;
    }

    protected void setup() {
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
        // Calendar icon conveys times entering and leaving
        pbchange_title.setGraphic(new Icon(BoxiconsRegular.LIST_UL, 48));
        pbchange_title.setActionHandler(() -> {
            Settings.get().getAppModel().setSelectedMetric("Backlog Changes");
            screenManager.switchScreen("project_selection");
        });
        // Health icon for PB Health
        pbhealth_tile.setGraphic(new Icon(BoxiconsRegular.HEART, 48));
        pbhealth_tile.setActionHandler(() -> {
            Settings.get().getAppModel().setSelectedMetric("PB Health");
            screenManager.switchScreen("project_selection");
        });
        groomrate_tile.setGraphic(new Icon(BoxiconsSolid.EDIT_ALT, 48));
        groomrate_tile.setActionHandler(() -> {
            Settings.get().getAppModel().setSelectedMetric("Groom Rate");
            screenManager.switchScreen("project_selection");
        });
        scopechange_tile.setGraphic(new Icon(BoxiconsRegular.PLUS_CIRCLE, 48)); // Choose an appropriate icon
        scopechange_tile.setActionHandler(() -> {
            Settings.get().getAppModel().setSelectedMetric("Scope Change");
            screenManager.switchScreen("project_selection");
        });

        taskexcess_tile.setGraphic(new Icon(BoxiconsSolid.TRASH, 48));
        taskexcess_tile.setActionHandler(() -> {
            // TODO enable once screen is in place
             Settings.get().getAppModel().setSelectedMetric("Task Excess");
             screenManager.switchScreen("project_selection");
        });
    }

    @Override
    protected void onFocused() {

    }
}
