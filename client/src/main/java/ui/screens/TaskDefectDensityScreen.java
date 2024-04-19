package ui.screens;

import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import ui.components.screens.ScreenManager;

public class TaskDefectDensityScreen extends BaseMetricConfiguration {
    private PieChart pieChart;

    public TaskDefectDensityScreen(ScreenManager screenManager, String id, String fxmlFilename) {
        super(screenManager, id, fxmlFilename);
        initializeUI();
    }

    private void initializeUI() {
        pieChart = new PieChart();
        pieChart.setTitle("Task Defect Density");
        pieChart.getData().addAll(
            new PieChart.Data("Finished", 30),
            new PieChart.Data("Unfinished", 20),
            new PieChart.Data("Deleted", 50)
        );
    }

    @Override
    protected Pane visualization() {
        StackPane pane = new StackPane(pieChart);
        return pane;
    }

    @Override
    protected Pane parameters() {
        // just hard-coded rn
        return null;
    }

    @Override
    protected void onFocused() {
        // just hard-coded rn
    }
}
