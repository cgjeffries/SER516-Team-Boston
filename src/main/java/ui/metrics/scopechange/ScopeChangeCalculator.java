package ui.metrics.scopechange;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import taiga.api.HistoryAPI;
import taiga.model.query.common.Project;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStory;

public class ScopeChangeCalculator {

    private final HistoryAPI historyAPI;

    public ScopeChangeCalculator() {
        this.historyAPI = new HistoryAPI();
    }

    protected Date getAddedAfterStartDate(UserStory userStory, Sprint sprint) {
        AtomicReference<Date> addedDate = new AtomicReference<>();
        historyAPI.getUserStoryHistory(userStory.getId(), result -> {
            if (result.getStatus() != 200) {
                return;
            }
            Arrays.stream(result.getContent())
                    .forEach(historyItem -> {
                        List<Long> milestoneDiff = historyItem.getDiff().getMilestone();
                        if (milestoneDiff == null) {
                            return;
                        }
                        boolean afterStart = historyItem.getCreatedAt().after(sprint.getEstimatedStart());
                        if (afterStart && milestoneDiff.get(0) == null && milestoneDiff.get(1) != null
                                && addedDate.get() == null) {
                            addedDate.set(historyItem.getCreatedAt());
                        }
                    });
        }).join();
        return addedDate.get();
    }

    public List<ScopeChangeItem> calculate(Project project, Sprint sprint) {
        return calculate(project.getId(), sprint);
    }

    public List<ScopeChangeItem> calculate(int projectId, Sprint sprint) {
        List<UserStory> stories = sprint.getUserStories();

        List<ScopeChangeItem> addedAfterStart = stories
                .parallelStream()
                .map(s -> {
                    Date addedDate = getAddedAfterStartDate(s, sprint);
                    if (addedDate == null) {
                        return null;
                    }
                    return new ScopeChangeItem(addedDate, s, false);
                })
                .filter(Objects::nonNull)
                .toList();

        return addedAfterStart;
    }

}