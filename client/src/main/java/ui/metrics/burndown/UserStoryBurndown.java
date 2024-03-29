package ui.metrics.burndown;

import taiga.TaigaClient;
import taiga.models.history.History;
import taiga.models.history.ValuesDiff;
import taiga.models.sprint.Sprint;
import taiga.models.sprint.UserStory;
import ui.util.DateUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class UserStoryBurndown implements BurndownCalculator {
    private final HashMap<Integer, List<History>> histories;

    public UserStoryBurndown() {
        this.histories = new HashMap<>();

    }

    private History findDoneHistoryEntry(int id) {
        List<History> entries = histories.get(id);
        if (entries == null) {
            return null;
        }
        Optional<History> doneEntry = entries
                .stream()
                .filter(e -> {
                    ValuesDiff diff = e.getValuesDiff();
                    if (diff == null) {
                        return false;
                    }
                    List<String> status = diff.getStatus();
                    if (status == null || status.size() != 2) {
                        return false;
                    }
                    return status.get(1).equals("Done");
                })
                .findFirst();
        return doneEntry.orElse(null);
    }

    private void populateAllUserStoryHistories(Sprint sprint) {
        List<CompletableFuture<Void>> futures = sprint
                .getUserStories()
                .parallelStream()
                .filter(s -> !histories.containsKey(s.getId())) // TODO: technically caching, should really be handled by the api class
                .map(s ->
                        TaigaClient.getHistoryAPI().getUserStoryHistory(s.getId(), result -> {
                            if (result.getStatus() != 200) {
                                return;
                            }
                            histories.put(s.getId(), new ArrayList<>(List.of(result.getContent())));
                        })
                )
                .toList();
        futures.forEach(CompletableFuture::join);
    }

    private List<DoneUserStory> getDoneUserStories(Sprint sprint) {
        List<DoneUserStory> completed = new ArrayList<>();
        sprint.getUserStories().forEach(story -> {
            History history = findDoneHistoryEntry(story.getId());
            if (history == null) {
                return;
            }
            completed.add(new DoneUserStory(story, history.getCreatedAt()));
        });
        return completed;
    }

    @Override
    public List<BurnDownEntry> calculate(Sprint sprint) {
        populateAllUserStoryHistories(sprint);

        double total = sprint
                .getUserStories()
                .stream()
                .map(u -> {
                    if (u.getTotalPoints() != null) {
                        return u.getTotalPoints();
                    }
                    return 0d;
                })
                .reduce(0d, Double::sum);

        LocalDate start = DateUtil.toLocal(sprint.getEstimatedStart());
        LocalDate end = DateUtil.toLocal(sprint.getEstimatedFinish());

        List<BurnDownEntry> entries = new ArrayList<>();
        List<DoneUserStory> completed = getDoneUserStories(sprint);

        long length = ChronoUnit.DAYS.between(start, end);
        double idealPerDay = total / length;
        double idealRemaining = total;
        double current = total;

        for (LocalDate date : start.datesUntil(end.plusDays(1)).toList()) {
            List<DoneUserStory> doneStories = completed
                    .stream()
                    .filter(story -> DateUtil.isSameDay(DateUtil.toLocal(story.getCompletedOn()), date))
                    .toList();
            if (!doneStories.isEmpty()) {
                double totalPointsDone = doneStories.stream().map(s -> s.getUserStory().getTotalPoints()).reduce(0d, Double::sum);
                current -= totalPointsDone;
            }
            entries.add(new BurnDownEntry(idealRemaining, current, DateUtil.toDate(date)));
            idealRemaining = Math.max(0, idealRemaining - idealPerDay);
        }

        return entries;
    }

    private static class DoneUserStory {
        private final UserStory userStory;
        private final Date completedOn;

        public DoneUserStory(UserStory userStory, Date completedOn) {
            super();
            this.userStory = userStory;
            this.completedOn = completedOn;
        }

        public Date getCompletedOn() {
            return completedOn;
        }

        public UserStory getUserStory() {
            return userStory;
        }
    }
}

