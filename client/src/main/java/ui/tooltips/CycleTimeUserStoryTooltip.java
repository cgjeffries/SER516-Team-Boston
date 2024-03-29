package ui.tooltips;

import taiga.models.userstories.UserStoryInterface;
import taigaold.util.timeAnalysis.CycleTimeEntry;

public class CycleTimeUserStoryTooltip implements TooltipPoint.TooltipTextCallback<CycleTimeEntry<UserStoryInterface>> {
    @Override
    public String getText(CycleTimeEntry<UserStoryInterface> item) {
        UserStoryInterface story = item.get();
        return story.getSubject() + " (#" + story.getRef() + ")"
                + "\nStarted on: " + item.getStartDate()
                + "\nCompleted on: " + item.getEndDate()
                + "\nCycle Time: " + item.getDaysTaken();
    }
}
