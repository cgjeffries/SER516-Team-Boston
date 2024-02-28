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

public class PBHealthService extends Service<Object> {
    private Sprint sprint;
    private SimpleObjectProperty<Double> pbHealthRatio;

    public PBHealthService() {
        this.pbHealthRatio = new SimpleObjectProperty<>();
    }

    /**
     * Gets an observable object containing the ratio of groomed to total PB user stories.
     * @return a SimpleObjectProperty<Double> containing the ratio of groomed to total PB user stories.
     */
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
