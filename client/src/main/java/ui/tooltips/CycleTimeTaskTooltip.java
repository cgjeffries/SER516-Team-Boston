package ui.tooltips;

import taiga.models.tasks.Task;
import taigaold.util.timeAnalysis.CycleTimeEntry;

public class CycleTimeTaskTooltip implements TooltipPoint.TooltipTextCallback<CycleTimeEntry<Task>> {
    @Override
    public String getText(CycleTimeEntry<Task> item) {
        Task task = item.get();
        return task.getSubject() + " (#" + task.getRef() + ")"
                + "\nStarted on: " + item.getStartDate()
                + "\nCompleted on: " + item.getEndDate()
                + "\nCycle Time: " + item.getDaysTaken();
    }
}
