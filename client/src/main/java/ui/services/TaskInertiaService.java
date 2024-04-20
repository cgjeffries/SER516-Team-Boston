package ui.services;

import bostonclient.BostonClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.http.HttpStatus;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TaskInertiaService extends Service<Object> {

    private int projectId;
    private LocalDate start;
    private LocalDate end;

    private final ObservableMap<String, Double> inertia;

    public TaskInertiaService() {
        this.inertia = FXCollections.observableHashMap();
    }

    public ObservableMap<String, Double> getInertia() {
        return inertia;
    }

    public void recalculate(int projectId, LocalDate start, LocalDate end) {
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
                Platform.runLater(inertia::clear);
                BostonClient.getTaskInertiaAPI().getTaskInertia(
                        projectId,
                        start.toString(),
                        end.toString(),
                        result -> {
                            if (result.getStatus() != HttpStatus.SC_OK) {
                                return;
                            }
                            TreeMap<LocalDate, Double> inertiaResult = result.getContent().getInertia();
                            Map<String, Double> formattedInertia = inertiaResult
                                    .entrySet()
                                    .stream()
                                    .collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue));
                            Platform.runLater(() -> inertia.putAll(formattedInertia));
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
