package ui.metrics.pbchange;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import taiga.model.query.sprint.Sprint;
import ui.components.Icon;

public class PBChange extends StackPane {

    private final PBChangeService service;
    private final TabPane tabPane;

    public PBChange() {
        this.service = new PBChangeService();
        this.tabPane = new TabPane();
        this.init();
    }

    private void init() {
        this.service.start();
    }

    public void switchSprint(Sprint sprint) {
        this.service.recalculate(sprint);
    }

    public void focusFirstTab() {
        tabPane.getSelectionModel().selectFirst();
    }
}

