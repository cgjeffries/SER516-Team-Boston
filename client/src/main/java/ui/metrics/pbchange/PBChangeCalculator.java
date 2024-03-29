package ui.metrics.pbchange;


import taiga.TaigaClient;
import taiga.models.epic.EpicDetail;
import taiga.models.project.Project;
import taiga.models.sprint.Sprint;
import taiga.models.sprint.UserStoryDetail;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PBChangeCalculator {
    private List<UserStoryDetail> userStories;

    public PBChangeCalculator() {
    }

    public static List<EpicDetail> filterEpicAfterSprintStart(List<EpicDetail> epics, Sprint sprint) {
        List<EpicDetail> addedAfterStart = new ArrayList<>();
        for (EpicDetail epic : epics) {
            if (epic.getCreatedDate().after(sprint.getEstimatedStart())) {
                addedAfterStart.add(epic);
            }
        }
        return addedAfterStart;
    }

    protected Date getRemovedFromPbAfterStartDate(UserStoryDetail userStory, Sprint sprint) {
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
                        if (afterStart && milestoneDiff.get(0) == null && milestoneDiff.get(1) != null && removedDate.get() == null) {
                            removedDate.set(historyItem.getCreatedAt());
                        }
                    });
        }).join();
        return removedDate.get();
    }

    private List<UserStoryDetail> getUserStories(int projectId) {
        TaigaClient.getUserStoryAPI().listProjectUserStories(projectId, result -> {
            userStories = new ArrayList<>(List.of(result.getContent()));
        }).join();
        return userStories;
    }

    public List<PBChangeItem> calculate(Project project, Sprint sprint) {
        return calculate(project.getId(), sprint);
    }

    public List<PBChangeItem> calculate(int projectId, Sprint sprint) {
        List<UserStoryDetail> stories = getUserStories(projectId);

        List<PBChangeItem> addedAfterSprint = stories
                .parallelStream()
                .filter(s -> s.getCreatedDate().after(sprint.getEstimatedStart()))
                .map(s -> new PBChangeItem(s.getCreatedDate(), s, true))
                .toList();

        List<PBChangeItem> removedFromPbAfterStart = stories
                .parallelStream()
                .map(s -> {
                    Date removedDate = getRemovedFromPbAfterStartDate(s, sprint);
                    if (removedDate == null) {
                        return null;
                    }
                    return new PBChangeItem(removedDate, s, false);
                })
                .filter(Objects::nonNull)
                .toList();

        List<PBChangeItem> items = new ArrayList<>(addedAfterSprint);
        items.addAll(removedFromPbAfterStart);
        return items;
    }
}
