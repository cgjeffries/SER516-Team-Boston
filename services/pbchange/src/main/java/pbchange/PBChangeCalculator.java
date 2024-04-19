package pbchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.http.HttpStatus;

import bostonmodel.pbchange.PBChangeItem;
import bostonmodel.pbchange.PBChangeMetrics;
import spark.Response;
import taiga.TaigaClient;
import taiga.models.sprint.Sprint;
import taiga.models.sprint.UserStoryDetail;

public class PBChangeCalculator {

    private static Date getRemovedFromPbAfterStartDate(UserStoryDetail userStory, Sprint sprint) {
        AtomicReference<Date> removedDate = new AtomicReference<>();
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
                                && removedDate.get() == null) {
                            removedDate.set(historyItem.getCreatedAt());
                        }
                    });
        }).join();
        return removedDate.get();
    }

    private static List<UserStoryDetail> getUserStories(int projectId) {
        List<UserStoryDetail> stories = new ArrayList<>();
        TaigaClient.getUserStoryAPI().listProjectUserStories(projectId, result -> {
            if (result.getStatus() == HttpStatus.SC_OK) {
                stories.addAll(List.of(result.getContent()));
            }
        }).join();
        return stories;
    }

    public static PBChangeMetrics calculate(Response response, int sprintId) {
        AtomicReference<Sprint> atomicSprint = new AtomicReference<>();
        TaigaClient.getSprintAPI().getSprint(sprintId, result -> {
            if (result.getStatus() == HttpStatus.SC_OK) {
                atomicSprint.set(result.getContent());
            }
        }).join();
        if (atomicSprint.get() == null) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            return null;
        }
        Sprint sprint = atomicSprint.get();

        List<UserStoryDetail> stories = getUserStories(sprint.getProject());
        if (stories.size() == 0) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            return null;
        }

        List<PBChangeItem> addedAfterSprint = stories
                .parallelStream()
                .filter(s -> s.getCreatedDate().after(sprint.getEstimatedStart()))
                .map(s -> new PBChangeItem(s.getCreatedDate(), s, true))
                .toList();

        List<PBChangeItem> removedFromPbAfterStart = stories
                .parallelStream()
                .map(story -> {
                    Date removedDate = getRemovedFromPbAfterStartDate(story, sprint);
                    if (removedDate == null) {
                        return null;
                    }
                    return new PBChangeItem(removedDate, story, false);
                })
                .filter(Objects::nonNull)
                .toList();

        List<PBChangeItem> items = new ArrayList<>(addedAfterSprint);
        items.addAll(removedFromPbAfterStart);
        return new PBChangeMetrics(items);
    }
}
