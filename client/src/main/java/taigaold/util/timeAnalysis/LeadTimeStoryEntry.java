package taigaold.util.timeAnalysis;


import taiga.models.userstories.UserStoryInterface;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LeadTimeStoryEntry {
    private final UserStoryInterface story;
    private final Date startDate;
    private final Date endDate;
    private final boolean valid;


    /**
     * Create a lead time entry for a user story. Different from {@link LeadTimeEntry} in that
     * this class only tracks the time elapsed between creation and completion, not its status history.
     */
    public LeadTimeStoryEntry(UserStoryInterface story, Date start, Date end, boolean valid) {
        this.story = story;
        this.startDate = start;
        this.endDate = end;
        this.valid = valid;
    }

    public LeadTimeStoryEntry(UserStoryInterface story, Date start, Date end) {
        this(story, start, end, true);
    }

    public UserStoryInterface get() {
        return story;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Long getTimeTaken() {
        if (startDate == null || endDate == null) {
            return 0L;
        }
        return endDate.getTime() - startDate.getTime();
    }

    public Long getDaysTaken() {
        return TimeUnit.MILLISECONDS.toDays(getTimeTaken());
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return "LeadTimeStoryEntry{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", valid=" + valid +
                ", daysTaken=" + getDaysTaken() +
                '}';
    }
}
