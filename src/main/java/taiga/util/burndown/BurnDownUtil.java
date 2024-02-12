package taiga.util.burndown;

import taiga.api.HistoryAPI;
import taiga.api.UserStoryAPI;
import taiga.model.query.history.History;
import taiga.model.query.history.ValuesDiff;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStory;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.util.UserStoryUtils;
import ui.util.DateUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static taiga.util.UserStoryUtils.extractBusinessValue;

public class BurnDownUtil {

    private Sprint sprint;
    private double storyPointTotal;
    private double businessValueTotal;
    private HistoryAPI historyAPI;
    private UserStoryAPI userStoryAPI;
    private HashMap<Integer, List<History>> histories;
    private UserStoryDetail[] userStories;

    public BurnDownUtil() {
        storyPointTotal = 0;
        businessValueTotal = 0;
    }

    public BurnDownUtil(Sprint sprint) {
        this.sprint = sprint;
        this.historyAPI = new HistoryAPI();
        this.userStoryAPI = new UserStoryAPI();
        this.histories = new HashMap<>();
        storyPointTotal = this.sprint.getTotalPoints();
        businessValueTotal = calculateBusinessValue();
        populateAllUserStoryHistories();
    }

    private double calculateBusinessValue() {
        UserStoryUtils userStoryUtils = new UserStoryUtils();
        double tempTotal = 0;
        for(UserStoryDetail story : userStories) {
            tempTotal += extractBusinessValue(story);
        }
        return tempTotal;
    }

    public void instantiateUserStoryDetailList() {
        userStoryAPI.listMilestoneUserStories(sprint.getId(), result -> {
            userStories = result.getContent();
        });
    }

    private History findDoneHistoryEntry(int id) {
        List<History> entries = histories.get(id);
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


    // TODO: call this via a javafx Observable* when the sprint is changed
    private void populateAllUserStoryHistories() {
        List<CompletableFuture<Void>> futures = sprint
                .getUserStories()
                .parallelStream()
                .filter(s -> !histories.containsKey(s.getId())) // TODO: technically caching, should really be handled by the api class
                .map(s ->
                        historyAPI.getUserStoryHistory(s.getId(), result -> {
                            if (result.getStatus() != 200) {
                                return;
                            }
                            histories.put(s.getId(), new ArrayList<>(List.of(result.getContent())));
                        })
                )
                .toList();
        futures.forEach(CompletableFuture::join);
    }

    private List<DoneUserStory> getDoneUserStories() {
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

    public void calculateUserStoryBurndown() {
        double total = sprint
                .getUserStories()
                .stream()
                .map(UserStory::getTotalPoints)
                .reduce(0d, Double::sum);

        LocalDate start = DateUtil.toLocal(sprint.getEstimatedStart());
        LocalDate end = DateUtil.toLocal(sprint.getEstimatedFinish());

        List<BurnDownEntry> entries = new ArrayList<>();
        List<DoneUserStory> completed = getDoneUserStories();

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

        entries.forEach(System.out::println);
    }

    // TODO: adapt to use BurnDownEntry
    class BvBurndownEntry {
        BvBurndownEntry(LocalDate date, Double value){
            this.date = date;
            this.value = value;
        }
        LocalDate date;
        Double value;
    }

    private List<BvBurndownEntry> calculateBvBurndown() {
        List<UserStoryDetail> userStories = null; //TODO: actually get the list of user stories

        LocalDate sprintStartLocalDate = DateUtil.toLocal(this.sprint.getEstimatedStart());

        LocalDate sprintEndLocalDate =  DateUtil.toLocal(this.sprint.getEstimatedFinish());

        List<LocalDate> sprintDates = sprintStartLocalDate.datesUntil(sprintEndLocalDate).toList();

        List<BvBurndownEntry> burndown = new ArrayList<>();

        for(int i = 0; i < sprintDates.size(); i++){
            Double value = this.businessValueTotal;
            if(i != 0){
                value = burndown.get(i-1).value;
            }
            for(UserStoryDetail userStoryDetail : userStories){
                if(DateUtil.toLocal(userStoryDetail.getFinishDate()).equals(sprintDates.get(i))){
                    value = value - extractBusinessValue(userStoryDetail);
                }
            }
            burndown.add(new BvBurndownEntry(sprintDates.get(i), value));
        }

        return burndown;
    }


    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public double getStoryPointTotal() {
        return storyPointTotal;
    }

    public void setStoryPointTotal(double storyPointTotal) {
        this.storyPointTotal = storyPointTotal;
    }

    public double getBusinessValueTotal() {
        return businessValueTotal;
    }

    public void setBusinessValueTotal(double businessValueTotal) {
        this.businessValueTotal = businessValueTotal;
    }


}
