package ui.services;

import bostonclient.BostonClient;
import bostonmodel.taskinertia.TaskInertiaMetrics;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.http.HttpStatus;
import ui.util.DateUtil;

import java.util.Date;

public class TaskInertiaService extends Service<Object> {

    private int projectId;
    private Date start;
    private Date end;

    private ObjectProperty<TaskInertiaMetrics> metrics;

    public ObjectProperty<TaskInertiaMetrics> getMetrics() {
        return metrics;
    }

    public void recalculate(int projectId, Date start, Date end) {
        this.projectId = projectId;
        this.start = start;
        this.end = end;
        restart();
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                BostonClient.getTaskInertiaAPI().getTaskInertia(
                        projectId,
                        DateUtil.toLocal(start).toString(),
                        DateUtil.toLocal(end).toString(),
                        result -> {
                            if (result.getStatus() != HttpStatus.SC_OK) {
                                metrics.set(new TaskInertiaMetrics(null));
                                return;
                            }
                            Platform.runLater(() -> metrics.set(result.getContent()));
                        }
                ).join();

                return null;
            }
        };
    }

    @Override
    protected void failed() {
        super.failed();
        Throwable exception = getException();
        if (exception != null) {
            exception.printStackTrace();
        }
    }
}
