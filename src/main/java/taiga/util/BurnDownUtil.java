package taiga.util;


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
            tempTotal += userStoryUtils.extractBusinessValue(story);
        }
        return tempTotal;
    }

    public void instantiateUserStoryDetailList() {
        api.listMilestoneUserStories(sprint.getId(), result -> {
            userStories = result.getContent();
        });
    }

    private void calculateUserStoryBurndown() {

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
