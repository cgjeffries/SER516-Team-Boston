package ui.metrics.pbchange;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Tweaks;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.boxicons.BoxiconsSolid;
import taiga.model.query.common.Epic;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.userstories.UserStory;
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
        Tab pbChangeStoriesTab = createPBChangeTab(
                "User Stories",
                new Icon(BoxiconsRegular.BOOK),
                null, // TODO: replace with data from service
                new PBChangeCell<UserStory>(new PBChangeCellRender<>() {
                    @Override
                    public String getTitle(UserStory item) {
                        return item.getSubject();
                    }

                    @Override
                    public String getDescription(UserStory item) {
                        return item.getDescription();
                    }
                })

        );
        Tab pbChangeEpicsTab = createPBChangeTab(
                "Epics",
                new Icon(BoxiconsSolid.FLAG),
                null, // TODO: replace with data from service
                new PBChangeCell<Epic>(new PBChangeCellRender<>() {
                    @Override
                    public String getTitle(Epic item) {
                        return item.getSubject();
                    }

                    @Override
                    public String getDescription(Epic item) {
                        // TODO: INCORRECT IMPLEMENTATION. We need to use the description field when the data tasks are pushed
                        // TODO: Ignoring for now to ensure stability
                        return item.getSubject();
                    }
                })
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().setAll(pbChangeStoriesTab, pbChangeEpicsTab);
        getChildren().add(tabPane);
        this.service.start();
    }

    private <T> Tab createPBChangeTab(String name, Icon icon, ObservableList<T> items, PBChangeCell<T> cellFactory) {
        StackPane root = new StackPane();

        Tab tab = new Tab(name);
        tab.setGraphic(icon);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        ListView<T> pbChangeList = new ListView<>();
        pbChangeList.setCellFactory(c -> cellFactory);
        pbChangeList.getStyleClass().add(Tweaks.EDGE_TO_EDGE);
        pbChangeList.setItems(items);
        pbChangeList.visibleProperty().bind(this.service.runningProperty().not());

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

    private static class PBChangeCell<T> extends ListCell<T> {

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

}
