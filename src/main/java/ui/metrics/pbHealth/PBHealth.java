package ui.metrics.pbHealth;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import taiga.model.query.sprint.Sprint;
import ui.components.Icon;
import ui.services.PBHealthService;

/**
 * PBHealth visualizes the health of the Product Backlog by analyzing the distribution of User Stories 
 * across different states (New, In Progress, Done, and optionally Testing) within Sprints. This visualization
 * assists in understanding the readiness of the backlog for future sprints, identifying bottlenecks, and 
 * ensuring a balanced workload throughout the development process.
 * For calculation and parameter details see:
 * src/main/java/ui/services/PBHealthService.java
 */
public class PBHealth extends StackPane {

    private final PBHealthService service;
    private final TabPane tabPane;

    public PBHealth() {
        this.service = new PBHealthService();
        this.tabPane = new TabPane();
        this.init();
    }

    private void init() {
        Tab pbHealthTab = createPBHealthTab(
                "Product Backlog",
                new Icon(BoxiconsRegular.LIST_CHECK));

        tabPane.getTabs().setAll(pbHealthTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        getChildren().add(tabPane);
        this.service.start(); 
    }

    private Tab createPBHealthTab(String name, Icon icon) {
        StackPane root = new StackPane();
        Tab tab = new Tab(name);
        tab.setGraphic(icon);

        // Example of a simple progress indicator and text placeholder for configuration
        VBox contentBox = new VBox();
        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        Text placeholderText = new Text("Configuration Options Go Here");
        contentBox.getChildren().addAll(progress, placeholderText);
        
        root.getChildren().add(contentBox);
        tab.setContent(root);

        return tab;
    }
    
    public void switchSprint(Sprint sprint) {
        this.service.recalculate(sprint);
    }
}