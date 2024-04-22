package ui.services;

import bostonclient.BostonClient;
import bostonmodel.taskchurn.TaskChurnItem;
import bostonmodel.taskchurn.TaskChurnMetrics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import taiga.models.sprint.Sprint;
import java.time.format.DateTimeFormatter;


public class TaskChurnService extends Service<Object> {
    private Sprint sprint;

    private final ObservableList<XYChart.Data<String, Number>> taskChurnData;

    public TaskChurnService() {
        this.taskChurnData = FXCollections.observableArrayList();
    }

    public ObservableList<XYChart.Data<String, Number>> getTaskChurnItems(){
        return taskChurnData;
    }

    private void updateTaskChurns(TaskChurnMetrics taskChurnMetrics){
        List<TaskChurnItem> sortedChurn = taskChurnMetrics.getTaskChurnItems().stream()
            .sorted(Comparator.comparing(TaskChurnItem::getChurnDate))
            .toList();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd");

        taskChurnData.setAll(
            sortedChurn.stream()
                .map(t -> {
                    return new XYChart.Data<>(format.format(t.getChurnDate()),
                        (Number) t.getChurnCount());
                })
                .toList());


    }

    public void recalculate(Sprint sprint) {
        if (sprint != null && sprint.getId() > 0) {
            this.sprint = sprint;
            restart();
        }
    }
    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() {
                try {
                    if (sprint == null) {
                        return null;
                    }
                    AtomicReference<TaskChurnMetrics> metricsReference = new AtomicReference<>();
                    BostonClient.getTaskChurnAPI().getTaskChurn(sprint.getId(), result -> {
                        if (result.getStatus() != 200) {
                            System.out.println(
                                "Error: TaskChurn service returned bad response code: " +
                                    result.getStatus());
                            metricsReference.set(new TaskChurnMetrics(
                                new ArrayList<>())); //set to empty (instead of null) so things don't explode
                        }
                        metricsReference.set(result.getContent());
                    }).join();

                    TaskChurnMetrics taskChurnMetrics = metricsReference.get();

                    Platform.runLater(() -> {
                        updateTaskChurns(taskChurnMetrics);
                    });
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                return null;
            }
        };
    }
}
