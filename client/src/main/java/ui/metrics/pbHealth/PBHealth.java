package ui.metrics.pbHealth;

import atlantafx.base.controls.Spacer;
import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ui.services.PBHealthService;

import java.text.NumberFormat;

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
    private final ReadOnlyObjectProperty<Double> poorThreshold;
    private final ReadOnlyObjectProperty<Double> fairThreshold;
    private final ReadOnlyObjectProperty<Double> goodThreshold;

    public PBHealth(ReadOnlyObjectProperty<Double> poorThreshold, ReadOnlyObjectProperty<Double> fairThreshold, ReadOnlyObjectProperty<Double> goodThreshold) {
        this.service = new PBHealthService();
        this.poorThreshold = poorThreshold;
        this.fairThreshold = fairThreshold;
        this.goodThreshold = goodThreshold;
        this.init();
    }

    private void init() {
        VBox.setVgrow(this, Priority.ALWAYS);
        StackPane root = new StackPane();
        VBox.setVgrow(root, Priority.ALWAYS);

        Text health = new Text();
        health.getStyleClass().add(Styles.TITLE_1);
        health.textProperty().bind(Bindings.createStringBinding(() -> {
            NumberFormat percentageFormat = NumberFormat.getPercentInstance();
            percentageFormat.setMinimumFractionDigits(2);
            return percentageFormat.format(this.service.getPbHealthRatio().get());
        }, this.service.getPbHealthRatio()));

        Text healthLabel = new Text();
        healthLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            double healthRatio = this.service.getPbHealthRatio().get();
            System.out.println(healthRatio);
            System.out.println(this.goodThreshold.get());
            if (healthRatio >= this.goodThreshold.get()) {
                health.getStyleClass().setAll(Styles.TITLE_1, Styles.TEXT, Styles.SUCCESS);
                healthLabel.getStyleClass().setAll(Styles.TITLE_3, Styles.TEXT, Styles.SUCCESS);
                return "Good";
            }
            if (healthRatio >= this.fairThreshold.get()) {
                health.getStyleClass().setAll(Styles.TITLE_1, Styles.TEXT, Styles.WARNING);
                healthLabel.getStyleClass().setAll(Styles.TITLE_3, Styles.TEXT, Styles.WARNING);
                return "Fair";
            }
            health.getStyleClass().setAll(Styles.TITLE_1, Styles.TEXT, Styles.DANGER);
            healthLabel.getStyleClass().setAll(Styles.TITLE_3, Styles.TEXT, Styles.DANGER);
            return "Poor";
        }, this.service.getPbHealthRatio(), this.goodThreshold, this.fairThreshold, this.poorThreshold));

        Spacer spacer = new Spacer(10);

        Text groomedLabel = new Text();
        groomedLabel.getStyleClass().add(Styles.TITLE_4);
        groomedLabel.textProperty().bind(Bindings.createStringBinding(() -> this.service.getGroomedStoryCount().get() + " stories groomed", this.service.getGroomedStoryCount()));

        Text totalLabel = new Text();
        totalLabel.getStyleClass().add(Styles.TITLE_4);
        totalLabel.textProperty().bind(Bindings.createStringBinding(() -> this.service.getTotalStoryCount().get() + " total stories", this.service.getTotalStoryCount()));

        VBox container = new VBox(
                health,
                healthLabel,
                spacer,
                groomedLabel,
                totalLabel
        );
        container.setPrefHeight(VBox.USE_PREF_SIZE);
        container.setAlignment(Pos.CENTER);
        container.visibleProperty().bind(this.service.runningProperty().not());
        VBox.setVgrow(container, Priority.ALWAYS);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        root.getChildren().add(progress);
        root.getChildren().add(container);

        getChildren().add(root);

        this.service.start();
    }

    public void calculate(int projectId) {
        this.service.recalculate(projectId);
    }
}