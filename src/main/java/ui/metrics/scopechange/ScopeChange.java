package ui.metrics.scopechange;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import taiga.model.query.sprint.Sprint;
import ui.metrics.pbchange.PBChange;
import ui.services.ScopeChangeService;

public class ScopeChange extends StackPane {
    private final ScopeChangeService service;

    public ScopeChange() {
        // Initialize the test service
        this.service = new ScopeChangeService();
        initUI();
    }

    private void initUI() {
        VBox.setVgrow(this, Priority.ALWAYS);
        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        ListView<ScopeChangeItem> pbChangeList = new ListView<>();
        pbChangeList.getStyleClass().add(Styles.STRIPED);
        pbChangeList.setCellFactory(c -> new ScopeChangeCell<>(new ScopeChangeCellRender<>() {
            @Override
            public String getTitle(ScopeChangeItem item) {
                return "(#" + item.getStoryDetail().getRef() + ") " + item.getStoryDetail().getSubject();
            }

            @Override
            public String getDescription(ScopeChangeItem item) {
                return "Added to sprint on " + item.getChangeDate();
            }
        }));
        pbChangeList.getStyleClass().add(Tweaks.EDGE_TO_EDGE);
        pbChangeList.visibleProperty().bind(this.service.runningProperty().not());
        pbChangeList.setItems(this.service.getScopeChangeStories());
        VBox.setVgrow(pbChangeList, Priority.ALWAYS);

        StackPane root = new StackPane(progress, pbChangeList);
        VBox.setVgrow(root, Priority.ALWAYS);

        getChildren().add(root);
    }

    public void recalculate(Sprint sprint) {
        this.service.recalculate(sprint);
    }

    protected static class ScopeChangeCell<T> extends ListCell<T> {

        private final ScopeChange.ScopeChangeCellRender<T> render;

        public ScopeChangeCell(ScopeChange.ScopeChangeCellRender<T> render) {
            this.render = render;
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
                return;
            }
            setStyle(getStyle() + "-fx-padding: 0px;");
            Tile root = new Tile();
            root.setTitle(render.getTitle(item));
            root.setDescription(render.getDescription(item));
            setGraphic(root);
        }
    }

    protected interface ScopeChangeCellRender<T> {
        String getTitle(T item);

        String getDescription(T item);
    }
}
