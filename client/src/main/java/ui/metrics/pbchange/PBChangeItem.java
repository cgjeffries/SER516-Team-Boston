package ui.metrics.pbchange;


import taiga.models.sprint.UserStoryDetail;

import java.util.Date;

public class PBChangeItem {
    private final UserStoryDetail storyDetail;
    private final boolean addedAfterSprint;
    private final boolean removedAfterSprint;
    private final Date changeDate;

    public PBChangeItem(Date changeDate, UserStoryDetail storyDetail, boolean added) {
        this.changeDate = changeDate;
        this.storyDetail = storyDetail;
        this.addedAfterSprint = added;
        this.removedAfterSprint = !added;
    }

    public boolean isAddedAfterSprint() {
        return addedAfterSprint;
    }

    public boolean isRemovedAfterSprint() {
        return removedAfterSprint;
    }

    public UserStoryDetail getStoryDetail() {
        return storyDetail;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    @Override
    public String toString() {
        return "PBChangeItem{" +
                "storyDetail=" + storyDetail +
                ", addedAfterSprint=" + addedAfterSprint +
                ", removedAfterSprint=" + removedAfterSprint +
                '}';
    }
}
