package bostonmodel.taskchurn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TaskChurnMetrics {
    @SerializedName("task_churn_items")
    @Expose
    private List<TaskChurnItem> taskChurnItems;

    public TaskChurnMetrics(List<TaskChurnItem> taskChurnItems) {
        this.taskChurnItems = taskChurnItems;
    }

    public List<TaskChurnItem> getTaskChurnItems() {
        return taskChurnItems;
    }
}
