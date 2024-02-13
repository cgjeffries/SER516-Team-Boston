package taiga.util.burndown;

import taiga.model.query.sprint.Sprint;

import java.util.List;

public interface BurndownCalculator {
    List<BurnDownEntry> calculate(Sprint sprint);
}
