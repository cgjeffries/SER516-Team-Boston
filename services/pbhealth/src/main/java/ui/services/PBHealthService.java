package ui.services;

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
     * was old javafx
     */
    public double getPbHealthRatio() {
        return pbHealthRatio;
    }

    public int getGroomedStoryCount() {
        return groomedStoryCount;
    }

    public int getTotalStoryCount() {
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
