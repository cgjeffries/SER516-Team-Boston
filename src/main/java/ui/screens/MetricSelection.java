package ui.screens;

import atlantafx.base.controls.Tile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import ui.ScreenManager;
import ui.components.Icon;
import ui.components.Screen;

import java.net.URL;
import java.util.ResourceBundle;

public class MetricSelection extends Screen<VBox> implements Initializable {
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
        burndown_tile.setGraphic(new Icon("bx-line-chart-down", 48));
        burndown_tile.setActionHandler(() -> System.out.println("Hello"));
    }
}
