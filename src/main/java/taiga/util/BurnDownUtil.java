package taiga.util;

import taiga.model.query.sprint.Sprint;

public class BurnDownUtil {

    private Sprint sprint;
    private double storyPointTotal;
    private double businessValueTotal;

    public BurnDownUtil() {
        storyPointTotal = 0;
        businessValueTotal = 0;
    }

    public BurnDownUtil(Sprint theSprint) {
        sprint = theSprint;
        storyPointTotal = sprint.getTotalPoints();
        businessValueTotal = calculateBusinessValue();
    }

    private double calculateBusinessValue() {
        // TODO Auto-generated method stub, needs to be done
        throw new UnsupportedOperationException("Unimplemented method 'calculateBusinessValue'");
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
