package ui.screens;

import atlantafx.base.controls.Tile;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import ui.FXMLManager;
import ui.components.Icon;

public class MetricSelection extends VBox {

    @FXML
    private Tile burndown_tile;

    public MetricSelection() {
        FXMLManager.getInstance().load("/fxml/metric_selection.fxml", this);
        burndown_tile.setGraphic(new Icon("bx-line-chart-down", 48));
        burndown_tile.setActionHandler(() -> System.out.println("Hello"));
    }
}
