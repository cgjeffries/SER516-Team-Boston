package bostonmodel.pbhealth;

public class PBHealthMetrics {
    private final double pbHealthRatio;
    private final int groomedStoryCount;
    private final int totalStoryCount;

    public PBHealthMetrics(double pbHealthRatio, int groomedStoryCount, int totalStoryCount) {
        this.pbHealthRatio = pbHealthRatio;
        this.groomedStoryCount = groomedStoryCount;
        this.totalStoryCount = totalStoryCount;
    }

    // Getters
    public double getPbHealthRatio() {
        return pbHealthRatio;
    }

    public int getGroomedStoryCount() {
        return groomedStoryCount;
    }

    public int getTotalStoryCount() {
        return totalStoryCount;
    }
}
