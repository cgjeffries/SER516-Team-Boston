package ui.services;

import bostonclient.BostonClient;
import bostonclient.apis.TaskDefectDensityAPI;
import bostonmodel.taskdefectdensity.TaskDefectDensityMetrics;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class TaskDefectDensityService extends Service<Object> {
    private int projectId;
    private final IntegerProperty totalTasks;
    private final IntegerProperty unfinishedTasks;
    private final TaskDefectDensityAPI taskddAPI;

    public TaskDefectDensityService() {
        this.totalTasks = new SimpleIntegerProperty();
        this.unfinishedTasks = new SimpleIntegerProperty();
        this.taskddAPI = BostonClient.getTaskDefectDensityAPI();
    }

    public IntegerProperty totalTasksCount() {
        return totalTasks;
    }

    public IntegerProperty unfinishedTasksCount() {
        return unfinishedTasks;
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() {

                AtomicReference<TaskDefectDensityMetrics> metricsReference = new AtomicReference<>();
                taskddAPI.getTaskDD(projectId, (foo) ->{
                    if(foo.getStatus() == 200){
                        metricsReference.set(foo.getContent());
                    }
                    else{
                        System.out.println("Error: Task Defect Density service returned bad response code: " + foo.getStatus());
                        metricsReference.set(new TaskDefectDensityMetrics(0,0));
                    }

                }).join();

                TaskDefectDensityMetrics metrics = metricsReference.get();

                Platform.runLater(() -> {
                    totalTasks.set(metrics.getTotalTasks());
                    unfinishedTasks.set(metrics.getNewTasks());
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
