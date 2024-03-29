package ui.metrics.pbchange;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import taiga.models.sprint.Sprint;
import ui.components.Icon;
import ui.services.PBChangeService;

public class PBChange extends StackPane {

    private final PBChangeService service;
    private final TabPane tabPane;

    public PBChange() {
        this.service = new PBChangeService();
        this.tabPane = new TabPane();
        this.init();
    }

    private void init() {
        Tab pbChangeAddedAfter = createPBChangeTab(
                "Added to PB",
                new Icon(BoxiconsRegular.BOOK),
                this.service.getAddedUserStories(),
                () ->
                        new PBChangeCell<>(new PBChangeCellRender<>() {
                            @Override
                            public String getTitle(PBChangeItem item) {
                                return item.getStoryDetail().getSubject();
                            }

                            @Override
                            public String getDescription(PBChangeItem item) {
                                return "Added on " + item.getChangeDate();
                            }
                        })

        );
        Tab pbChangeRemovedAfter = createPBChangeTab(
                "Removed from PB",
                new Icon(BoxiconsRegular.BOOK),
                this.service.getRemovedUserStories(),
                () ->
                        new PBChangeCell<>(new PBChangeCellRender<>() {
                            @Override
                            public String getTitle(PBChangeItem item) {
                                return item.getStoryDetail().getSubject();
                            }

                            @Override
                            public String getDescription(PBChangeItem item) {
                                return "Removed on " + item.getChangeDate();
                            }
                        })

        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().setAll(pbChangeAddedAfter, pbChangeRemovedAfter);
        getChildren().add(tabPane);
        this.service.start();
    }

    private <T> Tab createPBChangeTab(String name, Icon icon, ObservableList<T> items, PBChangeCellCreator<T> cellCreator) {
        StackPane root = new StackPane();

        Tab tab = new Tab(name);
        tab.setGraphic(icon);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        ListView<T> pbChangeList = new ListView<>();
        pbChangeList.getStyleClass().add(Styles.STRIPED);
        pbChangeList.setCellFactory(c -> cellCreator.create());
        pbChangeList.getStyleClass().add(Tweaks.EDGE_TO_EDGE);
        pbChangeList.visibleProperty().bind(this.service.runningProperty().not());
        pbChangeList.setItems(items);

        root.getChildren().add(progress);
        root.getChildren().add(pbChangeList);

        tab.setContent(root);

        return tab;
    }

    public void switchSprint(Sprint sprint) {
        this.service.recalculate(sprint);
    }

    public void focusFirstTab() {
        tabPane.getSelectionModel().selectFirst();
    }

    protected static class PBChangeCell<T> extends ListCell<T> {

        private final PBChangeCellRender<T> render;

        public PBChangeCell(PBChangeCellRender<T> render) {
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

    protected interface PBChangeCellRender<T> {
        String getTitle(T item);

        String getDescription(T item);
    }

    protected interface PBChangeCellCreator<T> {
        PBChangeCell<T> create();
    }

}
