package ui.metrics.scopechange;

import taiga.model.query.sprint.UserStoryDetail;

import java.util.Date;

public class ScopeChangeItem {
    private final UserStoryDetail storyDetail;
    private final boolean addedToSprintLate;
    private final boolean removedFromSprintLate;
    private final Date changeDate;

    public ScopeChangeItem(Date changeDate, UserStoryDetail storyDetail, boolean added) {
        this.changeDate = changeDate;
        this.storyDetail = storyDetail;
        this.addedToSprintLate = added;
        this.removedFromSprintLate = !added;
    }

    public boolean isAddedToSprintLate() {
        return addedToSprintLate;
    }

    public boolean isRemovedFromSprintLate() {
        return removedFromSprintLate;
    }

    public UserStoryDetail getStoryDetail() {
        return storyDetail;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    @Override
    public String toString() {
        return "ScopeChangeItem{" +
                "storyDetail=" + storyDetail +
                ", AddedToSprintLate=" + addedToSprintLate +
                ", RemovedFromSprintLate=" + removedFromSprintLate +
                '}';
    }
}
