package ui.tooltips;

import taiga.model.query.sprint.UserStory;
import taiga.model.query.userstories.UserStoryInterface;
import taiga.util.timeAnalysis.CycleTimeEntry;

public class CycleTimeUserStoryTooltip implements TooltipPoint.TooltipTextCallback<CycleTimeEntry<UserStoryInterface>> {
    @Override
    public String getText(CycleTimeEntry<UserStoryInterface> item) {
        UserStory story = (UserStory) item.get();
        return story.getSubject() + " (#" + story.getRef() + ")"
                + "\nStarted on: " + item.getStartDate()
                + "\nCompleted on: " + item.getEndDate()
                + "\nCycle Time: " + item.getDaysTaken();
    }
}
