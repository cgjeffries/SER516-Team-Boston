package taiga.util.burndown;

import taiga.model.query.sprint.UserStory;

import java.util.Date;

public class DoneUserStory {
    private final UserStory userStory;
    private final Date completedOn;

    public DoneUserStory(UserStory userStory, Date completedOn) {
        super();
        this.userStory = userStory;
        this.completedOn = completedOn;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public UserStory getUserStory() {
        return userStory;
    }
}
