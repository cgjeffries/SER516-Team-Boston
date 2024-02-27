package ui.services;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import taiga.model.query.sprint.Sprint;
import taiga.util.pbAnalysis.PBHealthHelper;
import taiga.util.pbAnalysis.PBHealthStats;

/**
 * PBHealth class is responsible for calculating and visualizing the health of the Product Backlog.
 * The health is determined by analyzing the distribution of User Stories across different states
 * (New, In Progress, Done, and Testing) within Sprints. This analysis helps in understanding
 * the readiness of the backlog for future sprints, identifying bottlenecks, and ensuring a balanced
 * workload throughout the development process.
 * 
 * Calculation Model:
 * - Health Score = (WeightedSum(Done) + WeightedSum(InProgress) + WeightedSum(New)) / TotalUserStories
 *   - Where WeightedSum(State) = Sum(UserStoriesInState * StateWeight)
 *   - StateWeights reflect the priority of having stories in certain states, e.g., Done > InProgress > New
 * - Additionally, the age of User Stories in each state can be analyzed to identify stale items.
 *
 * Parameters:
 * - Sprint: The current Sprint context to analyze User Stories' distribution.
 * - UserStoriesInNew: ObservableList tracking User Stories in the 'New' state.
 * - UserStoriesInProgress: ObservableList tracking User Stories in 'In Progress'.
 * - UserStoriesInDone: ObservableList tracking User Stories marked as 'Done'.
 * - UserStoriesInTesting: Tracks User Stories in 'Testing' if applicable.
 */
public class PBHealthService extends Service<Object> {
    private Sprint sprint;
    private final ObservableList<XYChart.Data<String, Number>> newStories;
    private final ObservableList<XYChart.Data<String, Number>> inProgressStories;
    private final ObservableList<XYChart.Data<String, Number>> testingStories;
    private final ObservableList<XYChart.Data<String, Number>> doneStories;

    public PBHealthService() {
        this.newStories = FXCollections.observableArrayList();
        this.inProgressStories = FXCollections.observableArrayList();
        this.testingStories = FXCollections.observableArrayList();
        this.doneStories = FXCollections.observableArrayList();
    }
    
    // Getters for each story list
    public ObservableList<XYChart.Data<String, Number>> getNewStories() { return newStories; }
    public ObservableList<XYChart.Data<String, Number>> getInProgressStories() { return inProgressStories; }
    public ObservableList<XYChart.Data<String, Number>> getTestingStories() { return testingStories; }
    public ObservableList<XYChart.Data<String, Number>> getDoneStories() { return doneStories; }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() {
                // Example of commenting out the original logic
                // PBHealthStats stats = PBHealthHelper.calculateHealth(sprint);
    
                // Hard-coded values for demonstration purposes
                ObservableList<XYChart.Data<String, Number>> hardCodedNewStories = FXCollections.observableArrayList(new XYChart.Data<>("Story 1", 1));
                ObservableList<XYChart.Data<String, Number>> hardCodedInProgressStories = FXCollections.observableArrayList(new XYChart.Data<>("Story 2", 2));
                ObservableList<XYChart.Data<String, Number>> hardCodedTestingStories = FXCollections.observableArrayList(new XYChart.Data<>("Story 3", 3));
                ObservableList<XYChart.Data<String, Number>> hardCodedDoneStories = FXCollections.observableArrayList(new XYChart.Data<>("Story 4", 4));
    
                Platform.runLater(() -> {
                    newStories.setAll(hardCodedNewStories);
                    inProgressStories.setAll(hardCodedInProgressStories);
                    testingStories.setAll(hardCodedTestingStories);
                    doneStories.setAll(hardCodedDoneStories);
                });
    
                return null;
            }
        };
    }

    public void recalculate(Sprint sprint) {
        this.sprint = sprint;
        this.restart();
    }

}
