package ui.services;

import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.util.pbAnalysis.PBHealthHelper;

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
    private SimpleObjectProperty<Double> pbHealthRatio;

    public PBHealthService() {
        this.pbHealthRatio = new SimpleObjectProperty<>();
    }
    
    public SimpleObjectProperty<Double> getPbHealthRatio(){
        return pbHealthRatio;
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() {

                PBHealthHelper pbHealthHelper = new PBHealthHelper(sprint.getProject());
                List<UserStoryDetail> groomedUserStories = pbHealthHelper.getGroomedPB();
                List<UserStoryDetail> notGroomedUserStories = pbHealthHelper.getNotGroomedPB();

                Double ratio = groomedUserStories.size()/(double)(groomedUserStories.size() + notGroomedUserStories.size());


                Platform.runLater(() -> {
                    pbHealthRatio.set(ratio);
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
