import taiga.models.sprint.UserStory;

import java.util.Date;

public class ScopeChangeItem {
    private final UserStory story;
    private final boolean addedToSprintLate;
    private final Date changeDate;

    public ScopeChangeItem(Date changeDate, UserStory story, boolean addedAfterStart) {
        this.changeDate = changeDate;
        this.story = story;
        this.addedToSprintLate = addedAfterStart;
    }

    public boolean isAddedToSprintLate() {
        return addedToSprintLate;
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
                '}';
    }
}
