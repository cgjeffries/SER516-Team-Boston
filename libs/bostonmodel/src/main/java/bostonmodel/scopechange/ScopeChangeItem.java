package bostonmodel.scopechange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import taiga.models.sprint.UserStory;

import java.util.Date;

public class ScopeChangeItem {
    @SerializedName("changeDate")
    @Expose
    private final Date changeDate;

    @SerializedName("story")
    @Expose
    private final UserStory story;

    @SerializedName("addedAfterStart")
    @Expose
    private final boolean addedToSprintLate;

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
        return "scopechange.ScopeChangeItem{" +
                "story=" + story +
                ", AddedToSprintLate=" + addedToSprintLate +
                '}';
    }
}