package taiga.util;

import taiga.api.UserStoryAPI;
import taiga.model.query.sprint.Sprint;

public class BurnDownUtil {

    private Sprint sprint;
    private double storyPointTotal;
    private double businessValueTotal;
    private UserStoryAPI api;

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
        // TODO Auto-generated method stub, needs to be done
        throw new UnsupportedOperationException("Unimplemented method 'calculateBusinessValue'");
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
