package ui.metrics.taskexcess;

import javafx.beans.binding.Bindings;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TaskExcess extends StackPane {
    // TODO add javafx serice

    public TaskExcess() {
        init();
    }

    private void init() {
        VBox.setVgrow(this, Priority.ALWAYS);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        // TODO bind service to visible property
        progress.visibleProperty().bind(null);

        PieChart chart = new PieChart();
        
        chart.dataProperty().bind(null);
        // TODO bind new tasks and other tasks data to chart

        Label ratio = new Label();
        ratio.textProperty().bind(Bindings.createStringBinding(() -> {
            // TODO bindd
            return "";
        }, null));

        VBox metricBox = new VBox(ratio, chart);
        StackPane root = new StackPane(metricBox, progress);
        VBox.setVgrow(root, Priority.ALWAYS);

        getChildren().add(root);

    }
}
