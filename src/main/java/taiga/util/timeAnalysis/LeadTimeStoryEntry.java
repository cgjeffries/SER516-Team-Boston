package taiga.util.timeAnalysis;

import taiga.model.query.userstories.UserStoryInterface;

import java.util.Date;

public class LeadTimeStoryEntry {
    private final Date startDate;
    private final Date endDate;


    /**
     * Create a lead time entry for a user story. Different from {@link LeadTimeEntry} in that
     * this class only tracks the time elapsed between creation and completion, not its status history.
     *
     * @param userStory
     */
    public LeadTimeStoryEntry(UserStoryInterface userStory) {
        this.startDate = userStory.getCreatedDate();
        this.endDate = userStory.getFinishDate();
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

    @Override
    public String toString() {
        return "LeadTimeStoryEntry{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
