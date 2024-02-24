package ui.metrics.pbchange;

import atlantafx.base.controls.Tile;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
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

    private Tab createPBChangeTab(String name, Icon icon) {
        return null;
    }

    private static class PBChangeCell<T> extends ListCell<T> {

        public PBChangeCell() {

        }

        @Override
        protected void updateItem(T project, boolean empty) {
            super.updateItem(project, empty);
            if (empty || project == null) {
                setGraphic(null);
                return;
            }
            setStyle(getStyle() + "-fx-padding: 0px;");
            Tile root = new Tile();
            root.setTitle(getTitle());
            root.setDescription(getDescription());
            setGraphic(root);
        }

        protected String getTitle() {
            return "";
        }

        protected String getDescription() {
            return "";
        }
    }
}
