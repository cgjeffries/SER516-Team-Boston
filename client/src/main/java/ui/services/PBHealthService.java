package ui.services;

import bostonclient.BostonClient;
import bostonclient.apis.PBHealthAPI;
import bostonmodel.pbhealth.PBHealthMetrics;
import java.util.concurrent.atomic.AtomicReference;
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
    private PBHealthAPI pbHealthAPI;

    public PBHealthService() {
        this.pbHealthRatio = new SimpleDoubleProperty();
        this.groomedStoryCount = new SimpleIntegerProperty();
        this.totalStoryCount = new SimpleIntegerProperty();
        this.pbHealthAPI = BostonClient.getPBChangeAPI();
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

                AtomicReference<PBHealthMetrics> metricsReference = new AtomicReference<>();
                pbHealthAPI.getPBHealth(projectId, (foo) ->{
                    if(foo.getStatus() == 200){
                        metricsReference.set(foo.getContent());
                    }
                    else{
                        System.out.println("Error: PBHealth service returned bad response code: " + foo.getStatus());
                        metricsReference.set(new PBHealthMetrics(0,0,0));
                    }

                }).join();

                PBHealthMetrics metrics = metricsReference.get();

                Platform.runLater(() -> {
                    pbHealthRatio.set(metrics.getPbHealthRatio());
                    groomedStoryCount.set(metrics.getGroomedStoryCount());
                    totalStoryCount.set(metrics.getTotalStoryCount());
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
