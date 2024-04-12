package bostonmodel.taskexcess;

public class TaskExcessMetrics {
    private int totalTasks;
    private int newTasks;

    public TaskExcessMetrics(int totalTasks, int newTasks) {
        this.totalTasks = totalTasks;
        this.newTasks = newTasks;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getNewTasks() {
        return newTasks;
    }
}
