package taskinertia;

import org.apache.http.HttpStatus;
import serviceutil.DateUtil;
import spark.Response;
import taiga.TaigaClient;
import taiga.models.taskhistory.ItemHistory;
import taiga.models.taskhistory.ItemHistoryValuesDiff;
import taiga.models.tasks.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskInertiaCalculator {
    public static TreeMap<LocalDate, Double> calculate(Response response, int projectId, Date startDate, Date endDate) {
        LocalDate startDateLocal = DateUtil.toLocal(startDate);
        LocalDate endDateLocal = DateUtil.toLocal(endDate);

        List<Task> tasks = new ArrayList<>();
        TaigaClient.getTasksAPI().listTasksByProject(projectId, result -> {
            if (result.getStatus() == HttpStatus.SC_OK) {
                tasks.addAll(List.of(result.getContent()));
            }
        }).join();

        if (tasks.isEmpty()) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            return null;
        }

        TreeMap<LocalDate, Integer> taskCounts = new TreeMap<>();
        TreeMap<LocalDate, Double> taskInertia = new TreeMap<>();

        tasks
                .parallelStream()
                .forEach(t -> {
                    List<ItemHistory> taskHistory = new ArrayList<>();
                    TaigaClient.getTaskHistoryAPI().getTaskHistory(t.getId(), result -> {
                        if (result.getStatus() == HttpStatus.SC_OK) {
                            taskHistory.addAll(List.of(result.getContent()));
                        }
                    }).join();
                    AtomicBoolean moved = new AtomicBoolean(false);
                    taskHistory.forEach(entry -> {
                        ItemHistoryValuesDiff valuesDiff = entry.getValuesDiff();
                        if (valuesDiff.getStatus() == null) {
                            return;
                        }
                        if (!moved.get()) {
                            taskCounts.putIfAbsent(DateUtil.toLocal(entry.getCreatedAt()), 0);
                            taskCounts.put(DateUtil.toLocal(entry.getCreatedAt()), taskCounts.get(DateUtil.toLocal(entry.getCreatedAt())) + 1);
                            moved.set(true);
                        }
                    });
                });

        for (LocalDate date = startDateLocal; !date.isAfter(endDateLocal); date = date.plusDays(1)) {
            int taskCount = totalTasksAtGivenDate(tasks, date);
            if (taskCounts.containsKey(date)) {
                double percent = ((double) (taskCount - taskCounts.get(date)) / taskCount);
                taskInertia.put(date, percent);
            } else {
                taskInertia.put(date, 1.0d);
            }
        }

        response.status(HttpStatus.SC_OK);
        return taskInertia;
    }

    private static int totalTasksAtGivenDate(List<Task> tasks, LocalDate date) {
        return tasks
                .stream()
                .filter(t -> {
                    LocalDate createdDate = DateUtil.toLocal(t.getCreatedDate());
                    boolean createdBeforeDate = createdDate.isBefore(date) || createdDate.isEqual(date);
                    if (t.getFinishedDate() == null) {
                        return createdBeforeDate;
                    }
                    LocalDate finishedDate = DateUtil.toLocal(t.getCreatedDate());
                    return createdBeforeDate && finishedDate.isAfter(date) || finishedDate.isEqual(date);
                })
                .toList()
                .size();
    }
}
