package ui.metrics.pbchange;

import taiga.model.query.sprint.UserStoryDetail;

public class PBChangeItem {
    private final UserStoryDetail storyDetail;
    private final boolean addedAfterSprint;
    private final boolean removedAfterSprint;

    public PBChangeItem(UserStoryDetail storyDetail, boolean added) {
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

    @Override
    public String toString() {
        return "PBChangeItem{" +
                "storyDetail=" + storyDetail +
                ", addedAfterSprint=" + addedAfterSprint +
                ", removedAfterSprint=" + removedAfterSprint +
                '}';
    }
}
