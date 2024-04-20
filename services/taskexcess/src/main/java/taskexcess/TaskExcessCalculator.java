package taskexcess;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.http.HttpStatus;

import bostonmodel.taskexcess.TaskExcessMetrics;
import spark.Request;
import spark.Response;
import taiga.TaigaClient;
import taiga.models.tasks.Task;

public class TaskExcessCalculator {
    public static TaskExcessMetrics calculate(Request request, Response response, int sprintId) {
        AtomicReference<List<Task>> tasks = new AtomicReference<>(null);
        TaigaClient.getTasksAPI().listTasksByMilestone(sprintId, result -> {
            if (result.getStatus() == HttpStatus.SC_OK) {
                tasks.set(List.of(result.getContent()));
            }
        }).join();

        if (tasks.get() == null) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            return null;
        }

        int totalTasks = tasks.get().size();
        int newTasks = tasks.get()
                .stream()
                .filter(t -> t.getStatusExtraInfo().getName().equalsIgnoreCase("new"))
                .toList()
                .size();

        double taskExcess;
        
        try {
            taskExcess = (double) newTasks / totalTasks;
        } catch (ArithmeticException e) {
            e.printStackTrace();
            taskExcess = Double.NaN;
        }

        return new TaskExcessMetrics(totalTasks, newTasks, taskExcess);
    }
}
