package burndown.calculators;

import bostonmodel.burndown.BurndownMetrics;
import spark.Response;

public class BurndownAggregator {
    public static BurndownMetrics calculate(Response response, int sprintId) {
        return new BurndownMetrics(
                new TaskBurndown().calculate(response, sprintId),
                new UserStoryBurndown().calculate(response, sprintId),
                new BusinessValueBurndown().calculate(response, sprintId)
        );
    }
}
