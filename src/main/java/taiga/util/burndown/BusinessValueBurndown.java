package taiga.util.burndown;

import taiga.api.UserStoryAPI;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.util.UserStoryUtils;
import ui.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BusinessValueBurndown implements BurndownCalculator {
    private final HashMap<Integer, Double> businessValues;
    private final UserStoryAPI userStoryAPI;

    public BusinessValueBurndown() {
        this.userStoryAPI = new UserStoryAPI();
        this.businessValues = new HashMap<>();

    }
    private double calculateTotalBusinessValue(Sprint sprint) {
        double tempTotal = 0;
        sprint.getUserStories()
                .parallelStream()
                .forEach(story -> {
                            Double bv = UserStoryUtils.getBusinessValueForUserStory(story);
                            businessValues.put(story.getId(), bv);
                        }
                );
        for(Double bv : businessValues.values()) {
            tempTotal += bv;
        }
        return tempTotal;
    }
    @Override
    public List<BurnDownEntry> calculate(Sprint sprint) {
        double businessValueTotal = calculateTotalBusinessValue(sprint);

        AtomicReference<List<UserStoryDetail>> userStories = new AtomicReference<>();

        CompletableFuture<Void> future = this.userStoryAPI.listMilestoneUserStories(sprint.getId(), result -> {
            userStories.set(List.of(result.getContent()));
        });

        future.join(); //wait for the request to complete

        List<BurnDownEntry> burndown = new ArrayList<>();

        LocalDate start = DateUtil.toLocal(sprint.getEstimatedStart());
        LocalDate end =  DateUtil.toLocal(sprint.getEstimatedFinish());

        if(userStories.get() == null){
            return start.datesUntil(end.plusDays(1)).map(d -> new BurnDownEntry(0, 0, DateUtil.toDate(d))).collect(Collectors.toList());
        }

        List<LocalDate> sprintDates = start.datesUntil(end.plusDays(1)).toList();

        for(int i = 0; i < sprintDates.size(); i++){
            double value = businessValueTotal;
            if(i != 0){
                value = burndown.get(i-1).getCurrent();
            }
            for(UserStoryDetail userStoryDetail : userStories.get()){
                if(userStoryDetail.getFinishDate() != null && DateUtil.toLocal(userStoryDetail.getFinishDate()).equals(sprintDates.get(i))){
                    value = value - businessValues.get(userStoryDetail.getId());
                }
            }
            double ideal = businessValueTotal - ((businessValueTotal/sprintDates.size()) * (i + 1));
            burndown.add(new BurnDownEntry(Math.max(0, ideal), value, DateUtil.toDate(sprintDates.get(i))));
        }

        burndown.forEach(System.out::println);
        return burndown;
    }
}
