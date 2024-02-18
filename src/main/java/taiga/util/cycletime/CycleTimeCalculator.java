package taiga.util.cycletime;

import taiga.model.query.sprint.Sprint;

import java.util.List;

/**
 * Interface for cycle time calculations
 */
public interface CycleTimeCalculator {
    /**
     * Calculate cycle time metrics for a given sprint
     *
     * @param sprint The sprint to calculate cycle time for
     * @return The cycle time data as a list of {@link CycleTimeEntry} data points
     */
    List<CycleTimeCalculator> calculate(Sprint sprint);
}
