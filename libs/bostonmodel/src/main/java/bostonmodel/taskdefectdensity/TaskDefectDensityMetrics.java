package bostonmodel.taskdefectdensity;

public class TaskDefectDensityMetrics {
    private int totalTasks;
    private int unfinisedTasks;
    private int closedTasks;
    private double tddRatio;

    public TaskDefectDensityMetrics(int totalTasks, int unfinisedTasks, double tddRatio, int closedTasks) {
        this.totalTasks = totalTasks;
        this.unfinisedTasks = unfinisedTasks;
        this.closedTasks = closedTasks;
        this.tddRatio = tddRatio;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getNewTasks() {
        return unfinisedTasks;
    }

    public double getTddRatio() {
        return tddRatio;
    }

    public int getClosedTasks() {
        return closedTasks;
    }

}
