package taiga.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import taiga.api.TaskHistoryAPI;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.model.query.taskhistory.ItemHistoryValuesDiff;
import taiga.util.timeAnalysis.TimeEntry;

public class TaskUtils {
    private static final TaskHistoryAPI taskHistoryAPI = new TaskHistoryAPI();
    public static TimeEntry getCycleTimeForTask(int taskId){
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

            if(valuesDiff.getStatus()[1].equals("In progress")){
                startDate = history.getCreatedAt();
                break;
            }
        }

        if(startDate == null){
            return new TimeEntry(null, null);
        }

        //get last time moved to "Done"
        Collections.reverse(historyList);
        Date endDate = null;
        for(ItemHistory history : historyList){
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();
            if(valuesDiff.getStatus() == null){
                continue;
            }

            if(valuesDiff.getStatus()[1].equals("Done")){
                endDate = history.getCreatedAt();
                break;
            }
        }

        if(endDate == null){
            return new TimeEntry(startDate, null);
        }

        return new TimeEntry(startDate, endDate);
    }
}
