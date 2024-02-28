package ui.metrics.pbHealth;

import javafx.geometry.Insets;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
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

    public PBHealth() {
        this.service = new PBHealthService();
        this.init();
    }

    private void init() {
        this.service.start(); 
    }
    
    public void switchSprint(Sprint sprint) {
        this.service.recalculate(sprint);
    }
}