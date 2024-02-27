package ui.metrics.pbchange;

import settings.Settings;
import taiga.api.HistoryAPI;
import taiga.api.UserStoryAPI;
import taiga.model.query.epic.EpicDetail;
import taiga.model.query.project.Project;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class PBChangeCalculator {
    private List<UserStoryDetail> userStories;
    private final UserStoryAPI userStoryAPI;
    private final HistoryAPI historyAPI;

    public PBChangeCalculator() {
        this.userStoryAPI = new UserStoryAPI();
        this.historyAPI = new HistoryAPI();
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

    private boolean wasRemovedFromPbAfterStart(UserStoryDetail userStory, Sprint sprint) {
        AtomicBoolean added = new AtomicBoolean(false);
        historyAPI.getUserStoryHistory(userStory.getId(), result -> {
            if (result.getStatus() != 200) {
                return;
            }
            int addedEntryCount = Arrays.stream(result.getContent())
                    .filter(historyItem -> {
                        List<Long> milestoneDiff = historyItem.getDiff().getMilestone();
                        boolean afterStart = historyItem.getCreatedAt().after(sprint.getEstimatedStart());
                        return afterStart && milestoneDiff.get(0) == null && milestoneDiff.get(1) != null;
                    })
                    .toList()
                    .size();
            added.set(addedEntryCount != 0);
        }).join();
        return added.get();
    }

    private List<UserStoryDetail> getUserStories() {
        if (userStories != null) {
            return userStories;
        }
        userStoryAPI.listProjectUserStories(Settings.get().getAppModel().getCurrentProject().get().getId(), result -> {
            userStories = new ArrayList<>(List.of(result.getContent()));
        }).join();
        return userStories;
    }

    public List<PBChangeItem> calculate(Project project, Sprint sprint) {
        return calculate(project.getId(), sprint);
    }

    public List<PBChangeItem> calculate(int projectId, Sprint sprint) {
        List<UserStoryDetail> stories = getUserStories();

        List<PBChangeItem> addedAfterSprint = stories
                .stream()
                .filter(s -> s.getCreatedDate().after(sprint.getEstimatedStart()))
                .map(s -> new PBChangeItem(true))
                .toList();

        List<PBChangeItem> removedFromPbAfterStart = stories
                .stream()
                .filter(s -> wasRemovedFromPbAfterStart(s, sprint))
                .map(s -> new PBChangeItem(false))
                .toList();

        List<PBChangeItem> items = new ArrayList<>(addedAfterSprint);
        items.addAll(removedFromPbAfterStart);
        return items;
    }
}
