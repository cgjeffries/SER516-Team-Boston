package scopechange;

import org.apache.http.HttpStatus;
import spark.Request;
import spark.Response;
import taiga.TaigaClient;
import taiga.models.sprint.Sprint;
import taiga.models.sprint.UserStory;

import bostonmodel.scopechange.ScopeChangeItem;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ScopeChangeCalculator {

    public ScopeChangeCalculator() {

    }

    /**
     * Makes api calls to the HistoryAPI to find out if a sprint's user story was
     * added before or after the start date
     *
     * @param userStory The userstory being analyzed
     * @param sprint    The sprint the userstory belongs too
     * @return The date the userstory was added if added after the sprint start,
     * else returns null.
     */
    public static Date getAddedAfterStartDate(UserStory userStory, Sprint sprint) {
        AtomicReference<Date> addedDate = new AtomicReference<>();
        TaigaClient.getHistoryAPI().getUserStoryHistory(userStory.getId(), result -> {
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

    /**
     * Analyzes the sprints User Stories and returns a list of those that were added
     * after the start of the sprint
     *
     * @param request
     * @param response
     * @param sprintId
     * @return A list of ScopeChangeItems which contain the added UserStories
     */
    public static List<ScopeChangeItem> calculate(Request request, Response response, int sprintId) {
        AtomicReference<Sprint> sprint = new AtomicReference<>();
        TaigaClient.getSprintAPI().getSprint(sprintId, result -> {
            if (result.getStatus() == HttpStatus.SC_OK) {
                sprint.set(result.getContent());
            }
        }).join();

        if (sprint.get() == null) {
            response.status(HttpStatus.SC_NOT_FOUND);
            return null;
        }

        return sprint
                .get()
                .getUserStories()
                .parallelStream()
                .map(s -> {
                    Date addedDate = ScopeChangeCalculator.getAddedAfterStartDate(s, sprint.get());
                    if (addedDate == null) {
                        return null;
                    }
                    return new ScopeChangeItem(addedDate, s, false);
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
