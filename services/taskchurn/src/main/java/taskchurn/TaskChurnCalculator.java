package taskchurn;

import bostonmodel.scopechange.ScopeChangeItem;
import bostonmodel.taskchurn.TaskChurnItem;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import java.util.concurrent.atomic.AtomicReference;
import org.apache.http.HttpStatus;

import serviceutil.DateUtil;
import spark.Response;
import taiga.TaigaClient;
import taiga.models.sprint.Sprint;
import taiga.models.taskhistory.ItemHistory;
import taiga.models.taskhistory.ItemHistoryValuesDiff;
import taiga.models.tasks.Task;

public class TaskChurnCalculator {
    public static List<TaskChurnItem> calculate(Response response, int sprintId) {
        TreeMap<LocalDate, Integer> taskCount = new TreeMap<>();
        TreeMap<LocalDate, Integer> taskModifiedCount = new TreeMap<>();
        TreeMap<LocalDate, Integer> taskChurn = new TreeMap<>();

        AtomicReference<Sprint> sprintReference = new AtomicReference<>();
        TaigaClient.getSprintAPI().getSprint(sprintId, result -> {
            if (result.getStatus() != 200) {
                return;
            }
            sprintReference.set(result.getContent());
        }).join(); //TODO: parallelize this and below


        AtomicReference<List<Task>> allTasksReference = new AtomicReference<>();
        TaigaClient.getTasksAPI().listTasksByMilestone(sprintId,
            result -> {
                if (result.getStatus() != 200) {
                    return;
                }
                allTasksReference.set(List.of(result.getContent()));
            }).join();


        List<Task> allTasks = allTasksReference.get();

        List<ItemHistory> taskHistoryList = new ArrayList<>();
        //find number of tasks per day in sprint
        allTasks.parallelStream()
            .forEach(task ->{
                    AtomicReference<List<ItemHistory>> itemHistoryReference = new AtomicReference<>();
                    TaigaClient.getTaskHistoryAPI().getTaskHistory(task.getId(), result -> {
                        if (result.getStatus() != 200) {
                            return;
                        }
                        itemHistoryReference.set(List.of(result.getContent()));
                    }).join();
                    taskHistoryList.addAll(itemHistoryReference.get());
                });

        for (Task task : allTasks) {
            //populate task count
            LocalDate createdDate = DateUtil.toLocal(task.getCreatedDate());
            if (taskCount.containsKey(createdDate)) {
                Integer temp = taskCount.get(createdDate);
                taskCount.put(createdDate, ++temp);
            } else {
                taskCount.put(createdDate, 1);
            }
        }
        Integer baseline = taskCount.get(taskCount.firstKey());
        Integer dayOne = baseline;

        for (ItemHistory taskHistory : taskHistoryList) {
            if (taskHistory.getValuesDiff() != null) { //null check
                ItemHistoryValuesDiff valuesDiff = taskHistory.getValuesDiff();
                LocalDate createdAt = DateUtil.toLocal(taskHistory.getCreatedAt());
                if (valuesDiff.getStatus() == null) {
                    if (taskModifiedCount.containsKey(createdAt)) {
                        Integer temp = taskModifiedCount.get(createdAt);
                        taskModifiedCount.put(createdAt, ++temp);
                    } else {
                        taskModifiedCount.put(createdAt, 1);
                    }
                }
            }
        }

        taskModifiedCount.put(taskCount.firstKey(), 0);
        TreeSet<LocalDate> allKeys = new TreeSet<>();
        allKeys.addAll(taskCount.keySet());
        allKeys.addAll(taskModifiedCount.keySet());
        //TODO: Fix this to be a count of changes rather than a percentage
        for (LocalDate date : allKeys) {

            Integer churn = (int) (((float) (Math.abs(taskCount.getOrDefault(date, 0) - baseline) + taskModifiedCount.getOrDefault(date, 0)) / (float) dayOne) * 100);
            if (taskCount.get(date) != null) {
                baseline = taskCount.get(date);
            }
            taskChurn.put(date, Math.abs(churn));
        }


        //put days with no churn to 0
        LocalDate start = DateUtil.toLocal(sprintReference.get().getEstimatedStart());
        LocalDate end = DateUtil.toLocal(sprintReference.get().getEstimatedFinish());
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList();

        for(LocalDate date : dates){
            if(!taskChurn.containsKey(date)){
                taskChurn.put(date, 0);
            }
        }

        List<TaskChurnItem> taskChurnItemList = new ArrayList<>();
        for(LocalDate date : taskChurn.keySet()){
            taskChurnItemList.add(new TaskChurnItem(date, taskChurn.get(date)));
        }
        return taskChurnItemList;
    }
}
