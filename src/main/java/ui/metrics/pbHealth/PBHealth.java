package ui.metrics.pbHealth;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
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

    }

}
