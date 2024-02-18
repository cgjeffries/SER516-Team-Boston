package taiga.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import taiga.api.TaskHistoryAPI;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.model.query.taskhistory.ItemHistoryValuesDiff;
import taiga.util.timeAnalysis.CycleTimeEntry;
import taiga.model.query.tasks.Task;

public class TaskUtils {
    private static final TaskHistoryAPI taskHistoryAPI = new TaskHistoryAPI();

    /**
     * Gets the cycleTime for the specified Task.
     * @param task The Task to get the cycleTime for
     * @return a CycleTimeEntry object
     */
    public static CycleTimeEntry getCycleTimeForTask(Task task){
        return getCycleTimeForTask(task.getId(), task.getIsClosed());
    }

    /**
     * Gets the cycleTime for the specified Task.
     * @param taskId The ID of the Task to get the cycleTime for
     * @param isClosed whether or not the speicifed task is *currently* closed
     * @return a CycleTimeEntry object
     */
    private static CycleTimeEntry getCycleTimeForTask(int taskId, boolean isClosed){
        AtomicReference<List<ItemHistory>> historyListReference = new AtomicReference<>();
        taskHistoryAPI.getTaskHistory(taskId, result ->{
            historyListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<ItemHistory> historyList = historyListReference.get();
        Collections.sort(historyList);

        //get first time moved to "In Progress"
        Date startDate = null;
        for(ItemHistory history : historyList){
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();
            if(valuesDiff.getStatus() == null){
                continue;
            }

            if(valuesDiff.getStatus()[1].equalsIgnoreCase("In progress")){
                startDate = history.getCreatedAt();
                break;
            }
        }

        if(startDate == null){
            return new CycleTimeEntry(null, null, false);
        }

        //get last time moved to "Done"
        Collections.reverse(historyList);
        Date endDate = null;
        for(ItemHistory history : historyList){
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();
            if(valuesDiff.getStatus() == null){
                continue;
            }

            if(valuesDiff.getStatus()[1].equalsIgnoreCase("done")){
                endDate = history.getCreatedAt();
                break;
            }
        }

        if(endDate == null || !isClosed){
            return new CycleTimeEntry(startDate, null, false);
        }

        return new CycleTimeEntry(startDate, endDate);
    }
}
