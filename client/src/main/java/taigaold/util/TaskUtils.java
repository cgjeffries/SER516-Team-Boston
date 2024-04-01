package taigaold.util;

import taiga.TaigaClient;
import taiga.models.taskhistory.ItemHistory;
import taiga.models.taskhistory.ItemHistoryValuesDiff;
import taiga.models.tasks.Task;
import taigaold.util.timeAnalysis.CycleTimeEntry;
import ui.tooltips.CycleTimeTaskTooltip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TaskUtils {

    /**
     * Gets the cycleTime for the specified Task.
     *
     * @param task The Task to get the cycleTime for
     * @return a CycleTimeEntry object
     */
    public static CycleTimeEntry<Task> getCycleTimeForTask(Task task) {
        AtomicReference<List<ItemHistory>> historyListReference = new AtomicReference<>();
        TaigaClient.getTaskHistoryAPI().getTaskHistory(task.getId(), result -> {
            historyListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();

        List<ItemHistory> historyList = historyListReference.get();
        Collections.sort(historyList);

        //get first time moved to "In Progress"
        Date startDate = null;
        for (ItemHistory history : historyList) {
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();
            if (valuesDiff.getStatus() == null) {
                continue;
            }

            if (valuesDiff.getStatus()[1].equalsIgnoreCase("In progress")) {
                startDate = history.getCreatedAt();
                break;
            }
        }

        if (startDate == null) {
            CycleTimeEntry<Task> entry = new CycleTimeEntry<>(null, null, null, false);
            entry.setTooltipCallback(new CycleTimeTaskTooltip());
            return entry;
        }

        //get last time moved to "Done"
        Collections.reverse(historyList);
        Date endDate = null;
        for (ItemHistory history : historyList) {
            ItemHistoryValuesDiff valuesDiff = history.getValuesDiff();
            if (valuesDiff.getStatus() == null) {
                continue;
            }

            if (valuesDiff.getStatus()[1].equalsIgnoreCase("done")) {
                endDate = history.getCreatedAt();
                break;
            }
        }

        if (endDate == null || !task.getIsClosed()) {
            CycleTimeEntry<Task> entry = new CycleTimeEntry<>(null, startDate, null, false);
            entry.setTooltipCallback(new CycleTimeTaskTooltip());
            return entry;
        }

        CycleTimeEntry<Task> entry = new CycleTimeEntry<>(task, startDate, endDate);
        entry.setTooltipCallback(new CycleTimeTaskTooltip());
        return entry;
    }
}
