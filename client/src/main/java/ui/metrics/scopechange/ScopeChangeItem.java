package ui.metrics.scopechange;


import taiga.models.sprint.UserStory;

import java.util.Date;

public class ScopeChangeItem {
    private final UserStory story;
    private final boolean addedToSprintLate;
    private final boolean removedFromSprintLate;
    private final Date changeDate;

    public ScopeChangeItem(Date changeDate, UserStory story, boolean added) {
        this.changeDate = changeDate;
        this.story = story;
        this.addedToSprintLate = added;
        this.removedFromSprintLate = !added;
    }

    public boolean isAddedToSprintLate() {
        return addedToSprintLate;
    }

    public boolean isRemovedFromSprintLate() {
        return removedFromSprintLate;
    }

    public UserStory getStoryDetail() {
        return story;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    @Override
    public String toString() {
        return "ScopeChangeItem{" +
                "story=" + story +
                ", AddedToSprintLate=" + addedToSprintLate +
                ", RemovedFromSprintLate=" + removedFromSprintLate +
                '}';
    }
}
