package taiga.util;


import static taiga.util.UserStoryUtils.extractBusinessValue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.util.Pair;
import taiga.api.UserStoryAPI;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStory;
import taiga.model.query.sprint.UserStoryDetail;

public class BurnDownUtil {

    private Sprint sprint;
    private double storyPointTotal;
    private double businessValueTotal;
    private UserStoryAPI api;
    private UserStoryDetail[] userStories;


    public BurnDownUtil() {
        storyPointTotal = 0;
        businessValueTotal = 0;
    }

    public BurnDownUtil(Sprint theSprint) {
        sprint = theSprint;
        storyPointTotal = sprint.getTotalPoints();
        businessValueTotal = calculateBusinessValue();
        this.api = new UserStoryAPI();
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
        api.listMilestoneUserStories(sprint.getId(), result -> {
            userStories = result.getContent();
        });
    }

    private LocalDate convertToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    class BurndownEntry{
        BurndownEntry(LocalDate date, Double value){
            this.date = date;
            this.value = value;
        }
        LocalDate date;
        Double value;
    }

    private List<BurndownEntry> calculateUserStoryBurndown() {
        List<UserStoryDetail> userStories = null; //TODO: actually get the list of user stories

        LocalDate sprintStartLocalDate = convertToLocalDate(this.sprint.getEstimatedStart());

        LocalDate sprintEndLocalDate = convertToLocalDate(this.sprint.getEstimatedFinish());

        List<LocalDate> sprintDates = sprintStartLocalDate.datesUntil(sprintEndLocalDate).toList();

        List<BurndownEntry> burndown = new ArrayList<>();

        for(int i = 0; i < sprintDates.size(); i++){
            Double value = this.businessValueTotal;
            if(i != 0){
                value = burndown.get(i-1).value;
            }
            for(UserStoryDetail userStoryDetail : userStories){
                if(convertToLocalDate(userStoryDetail.getFinishDate()).equals(sprintDates.get(i))){
                    value = value - extractBusinessValue(userStoryDetail);
                }
            }
            burndown.add(new BurndownEntry(sprintDates.get(i), value));
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
