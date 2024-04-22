package taskdefectdensity;

import bostonmodel.taskdefectdensity.TaskDefectDensityMetrics;
import org.apache.http.HttpStatus;
import spark.Request;
import spark.Response;
import taiga.TaigaClient;
import taiga.models.tasks.Task;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TaskDefectDensityCalculator {

    public static TaskDefectDensityMetrics calculate(Request request, Response response, int sprintId) {

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

        int closedTasks = tasks.get()
                .stream()
                .filter(t -> t.getStatusExtraInfo().getIsClosed())
                .toList()
                .size();
        
                int unfinishedTasks = totalTasks - closedTasks;

        
        double tddRatio = (double) unfinishedTasks / totalTasks * 100.0;

        return new TaskDefectDensityMetrics(totalTasks, unfinishedTasks, tddRatio, closedTasks);
    }
}
