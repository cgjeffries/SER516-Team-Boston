package taiga.util.burndown;

import java.util.concurrent.atomic.AtomicReference;
import taiga.api.HistoryAPI;
import taiga.api.SprintAPI;
import taiga.api.SprintStatsAPI;
import taiga.api.UserStoryAPI;
import taiga.model.query.history.History;
import taiga.model.query.history.ValuesDiff;
import taiga.model.query.sprint.Days;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.SprintStats;
import taiga.model.query.sprint.UserStory;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.sprinthistory.Day;
import taiga.util.UserStoryUtils;
import ui.util.DateUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

//import static taiga.util.UserStoryUtils.extractBusinessValue;

public class BurnDownUtil {

    private Sprint sprint;
    private double storyPointTotal;
    private double businessValueTotal;
    private HistoryAPI historyAPI;
    private UserStoryAPI userStoryAPI;
    private SprintStatsAPI sprintStatsAPI;
    private HashMap<Integer, List<History>> histories;
    private HashMap<Integer, Double> businessValues;

    private UserStoryUtils userStoryUtils;

    public BurnDownUtil() {
        storyPointTotal = 0;
        businessValueTotal = 0;
    }

    public BurnDownUtil(Sprint sprint) {
        this.sprint = sprint;
        this.historyAPI = new HistoryAPI();
        this.userStoryAPI = new UserStoryAPI();
        this.sprintStatsAPI = new SprintStatsAPI();
        this.histories = new HashMap<>();
        this.userStoryUtils = new UserStoryUtils(sprint.getProject());
        this.businessValues = new HashMap<>();

        storyPointTotal = this.sprint.getTotalPoints();
        businessValueTotal = calculateTotalBusinessValue();
        populateAllUserStoryHistories();
    }

    private double calculateTotalBusinessValue() {
        double tempTotal = 0;
        this.sprint.getUserStories()
            .parallelStream()
            .forEach(story -> {
                    Double bv = userStoryUtils.getBusinessValueForUserStory(story.getId());
                    businessValues.put(story.getId(), bv);
                }
            );
        for(Double bv : businessValues.values()) {
            tempTotal += bv;
        }
        return tempTotal;
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
        System.out.println();
    }

    public List<BurnDownEntry> calculateBvBurndown() {

        AtomicReference<List<UserStoryDetail>> userStories = new AtomicReference<>();

        CompletableFuture<Void> future = this.userStoryAPI.listMilestoneUserStories(this.sprint.getId(), result -> {
            userStories.set(List.of(result.getContent()));
        });

        future.join(); //wait for the request to complete

        List<BurnDownEntry> burndown = new ArrayList<>();

        LocalDate start = DateUtil.toLocal(this.sprint.getEstimatedStart());
        LocalDate end =  DateUtil.toLocal(this.sprint.getEstimatedFinish());

        if(userStories.get() == null){
            return start.datesUntil(end.plusDays(1)).map(d -> new BurnDownEntry(0, 0, DateUtil.toDate(d))).collect(Collectors.toList());
        }

        List<LocalDate> sprintDates = start.datesUntil(end.plusDays(1)).toList();

        for(int i = 0; i < sprintDates.size(); i++){
            double value = this.businessValueTotal;
            if(i != 0){
                value = burndown.get(i-1).getCurrent();
            }
            for(UserStoryDetail userStoryDetail : userStories.get()){
                if(userStoryDetail.getFinishDate() != null && DateUtil.toLocal(userStoryDetail.getFinishDate()).equals(sprintDates.get(i))){
                    value = value - businessValues.get(userStoryDetail.getId());
                }
            }
            burndown.add(new BurnDownEntry(this.businessValueTotal - ((this.businessValueTotal/sprintDates.size()) * (i + 1)), value, DateUtil.toDate(sprintDates.get(i))));
        }

        burndown.forEach(System.out::println);
        return burndown;
    }

    public List<BurnDownEntry> getTaigaBurndown(){
        AtomicReference<List<Days>> days = new AtomicReference<>();

        CompletableFuture<Void> future = this.sprintStatsAPI.getSprintStats(this.sprint.getId(), result -> {
            days.set(List.of(result.getContent().getDays()));
        });

        future.join(); //wait for the request to complete

        List<BurnDownEntry> burndown = new ArrayList<>();

        days.get().forEach(d -> burndown.add(new BurnDownEntry(Math.max(0, d.getOptimalPoints()), d.getOpenPoints(), d.getDay())));

        burndown.forEach(System.out::println);
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
