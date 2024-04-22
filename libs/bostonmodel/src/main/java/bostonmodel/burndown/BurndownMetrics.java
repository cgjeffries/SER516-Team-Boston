package bostonmodel.burndown;

import java.util.List;

public class BurndownMetrics {
    private final List<BurnDownEntry> taskBurndown;
    private final List<BurnDownEntry> userStoryBurndown;
    private final List<BurnDownEntry> businessValueBurndown;

    public BurndownMetrics(List<BurnDownEntry> taskBurndown, List<BurnDownEntry> userStoryBurndown, List<BurnDownEntry> businessValueBurndown) {
        this.taskBurndown = taskBurndown;
        this.userStoryBurndown = userStoryBurndown;
        this.businessValueBurndown = businessValueBurndown;
    }

    public List<BurnDownEntry> getBusinessValueBurndown() {
        return businessValueBurndown;
    }

    public List<BurnDownEntry> getTaskBurndown() {
        return taskBurndown;
    }

    public List<BurnDownEntry> getUserStoryBurndown() {
        return userStoryBurndown;
    }
}
