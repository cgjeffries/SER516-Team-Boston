package bostonmodel.burndown;

import java.util.List;

public class BurndownCharts {
    private List<BurnDownEntry> taskBurndown;
    private List<BurnDownEntry> userStoryBurndown;
    private List<BurnDownEntry> businessValueBurndown;

    public BurndownCharts(List<BurnDownEntry> taskBurndown, List<BurnDownEntry> userStoryBurndown, List<BurnDownEntry> businessValueBurndown) {
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
