package calculators;

import bostonmodel.burndown.BurndownCharts;
import spark.Response;

public class BurndownAggregator {
    public static BurndownCharts calculate(Response response, int sprintId) {
        return new BurndownCharts(
                new TaskBurndown().calculate(response, sprintId),
                new UserStoryBurndown().calculate(response, sprintId),
                new BusinessValueBurndown().calculate(response, sprintId)
        );
    }
}
