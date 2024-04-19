package ui.services;

import java.util.concurrent.atomic.AtomicReference;

import bostonclient.BostonClient;
import bostonclient.apis.TaskExcessAPI;
import bostonmodel.pbhealth.PBHealthMetrics;
import bostonmodel.taskexcess.TaskExcessMetrics;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class TaskExcessService extends Service<Object> {
    private int sprintId;
    private final IntegerProperty totalTaskCount;
    private final IntegerProperty newTaskCount;
    //Not sure where the ratio needs to be calculated
    private final DoubleProperty taskExcessRatio;
    private TaskExcessAPI taskExcessAPI;

    public TaskExcessService(){
        this.totalTaskCount = new SimpleIntegerProperty();
        this.newTaskCount = new SimpleIntegerProperty();
        this.taskExcessRatio = new SimpleDoubleProperty();
        this.taskExcessAPI= BostonClient.getTaskExcessAPI();
    }
    public DoubleProperty getTaskExcessRatio() {
        return taskExcessRatio;
    }

    public IntegerProperty getTotalTaskCount() {
        return totalTaskCount;
    }

    public IntegerProperty getNewTaskCount() {
        return newTaskCount;
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() {

                AtomicReference<TaskExcessMetrics> metricsReference = new AtomicReference<>();
                taskExcessAPI.getTaskExcess(sprintId, (foo) ->{
                    if(foo.getStatus() == 200){
                        metricsReference.set(foo.getContent());
                    }
                    else{
                        System.out.println("Error: Task excess service returned bad response code: " + foo.getStatus());
                        metricsReference.set(new TaskExcessMetrics(0,0,0));
                    }

                }).join();

                TaskExcessMetrics metrics = metricsReference.get();

                Platform.runLater(() -> {
                    taskExcessRatio.set(metrics.gettaskExcessRatio());
                    totalTaskCount.set(metrics.getTotalTasks());
                    newTaskCount.set(metrics.getNewTasks());
                });

                return null;
            }
        };
    }


    public void recalculate(int sprintId) {
        this.sprintId = sprintId;
        this.restart();
    }

}
