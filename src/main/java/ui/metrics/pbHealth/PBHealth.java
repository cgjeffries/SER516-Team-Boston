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
    private final TabPane tabPane;

    public PBHealth() {
        this.service = new PBHealthService();
        this.tabPane = new TabPane();
        this.init();
    }

    private void init() {
        Tab pbHealthTab = createPBHealthTab(
                "Configure weights for user story statuses below",
                new Icon(BoxiconsRegular.SLIDER));

        tabPane.getTabs().setAll(pbHealthTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        getChildren().add(tabPane);
        this.service.start(); 
    }

    private Tab createPBHealthTab(String name, Icon icon) {
        VBox contentBox = new VBox(10);
        contentBox.setPadding(new Insets(10));

        contentBox.getChildren().addAll(
                createWeightConfiguration("New"),
                createWeightConfiguration("In Progress"),
                createWeightConfiguration("Testing"),
                createWeightConfiguration("Done")
        );

        Tab tab = new Tab(name, contentBox);
        tab.setGraphic(icon);
        return tab;
    }

    private HBox createWeightConfiguration(String status) {
        Label label = new Label(status + " status weight:");
        TextField textField = new TextField("50"); // Default value
        textField.setPrefWidth(50); // Set preferred width
    
        // Input should be numbers 0-100
        textField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
    
        Slider slider = createWeightSlider(); // Use your method to create a slider
        // Bind slider and text field
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField.setText(String.format("%.0f", newVal.doubleValue()));
        });
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                textField.setText(newVal.replaceAll("[^\\d]", ""));
            }
            try {
                double value = Double.parseDouble(textField.getText());
                slider.setValue(value);
            } catch (NumberFormatException e) {
                // Reset to slider's current value if the text field input is not a number
                textField.setText(String.format("%.0f", slider.getValue()));
            }
        });
    
        HBox hbox = new HBox(10); // Spacing between elements
        hbox.getChildren().addAll(label, textField, slider);
        HBox.setHgrow(slider, Priority.ALWAYS); // Make the slider take up all available space
    
        return hbox;
    }
    
    private Slider createWeightSlider() {
        Slider slider = new Slider(0, 100, 50); // Adjusted min, max, initial value to match your range
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10); // Adjust for a range of 0-100
        slider.setBlockIncrement(1); // Smoother sliding
        return slider;
    }
    
    public void switchSprint(Sprint sprint) {
        this.service.recalculate(sprint);
    }
}