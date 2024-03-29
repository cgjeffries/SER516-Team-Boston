package ui.metrics.burndown;


import taiga.models.sprint.Sprint;

import java.util.List;

/**
 * Interface for burndown calculations
 */
public interface BurndownCalculator {
    /**
     * Calculate burndown metrics for a given sprint
     *
     * @param sprint The sprint to calculate burndown for
     * @return The burndown data as a list of {@link BurnDownEntry} data points
     */
    List<BurnDownEntry> calculate(Sprint sprint);
}
