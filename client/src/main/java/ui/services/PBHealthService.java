package ui.services;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import taiga.models.sprint.UserStoryDetail;
import ui.metrics.pbHealth.PBHealthHelper;

import java.util.List;

public class PBHealthService extends Service<Object> {
    private int projectId;
    private final DoubleProperty pbHealthRatio;
    private final IntegerProperty groomedStoryCount;
    private final IntegerProperty totalStoryCount;

    public PBHealthService() {
        this.pbHealthRatio = new SimpleDoubleProperty();
        this.groomedStoryCount = new SimpleIntegerProperty();
        this.totalStoryCount = new SimpleIntegerProperty();
    }

    /**
     * Gets an observable object containing the ratio of groomed to total PB user stories.
     *
     * @return a SimpleObjectProperty<Double> containing the ratio of groomed to total PB user stories.
     */
    public DoubleProperty getPbHealthRatio() {
        return pbHealthRatio;
    }

    public IntegerProperty getGroomedStoryCount() {
        return groomedStoryCount;
    }

    public IntegerProperty getTotalStoryCount() {
        return totalStoryCount;
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() {

                PBHealthHelper pbHealthHelper = new PBHealthHelper(projectId);
                List<UserStoryDetail> groomedUserStories = pbHealthHelper.getGroomedPB();
                List<UserStoryDetail> notGroomedUserStories = pbHealthHelper.getNotGroomedPB();

                int totalUserStoryCount = groomedUserStories.size() + notGroomedUserStories.size();
                double ratio;
                if (totalUserStoryCount > 0) {
                    ratio = (double) groomedUserStories.size() / totalUserStoryCount;
                } else {
                    ratio = 0;
                }

                Platform.runLater(() -> {
                    pbHealthRatio.set(ratio);
                    groomedStoryCount.set(groomedUserStories.size());
                    totalStoryCount.set(totalUserStoryCount);
                });

                return null;
            }
        };
    }

    public void recalculate(int projectId) {
        this.projectId = projectId;
        this.restart();
    }

}
