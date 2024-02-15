package taiga.util.burndown;

import taiga.model.query.sprint.Sprint;


public class BurnDownUtil {

    private Sprint sprint;

    private final TaskBurndown taskBurndown;
    private final UserStoryBurndown userStoryBurndown;
    private final BusinessValueBurndown businessValueBurndown;


    public BurnDownUtil(Sprint sprint) {
        this.sprint = sprint;

        this.taskBurndown = new TaskBurndown();
        this.userStoryBurndown = new UserStoryBurndown();
        this.businessValueBurndown = new BusinessValueBurndown();

        this.taskBurndown.calculate(sprint);
        this.userStoryBurndown.calculate(sprint);
        this.businessValueBurndown.calculate(sprint);
    }


    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }
}
