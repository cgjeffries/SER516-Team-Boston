package taiga.model.query.taskhistory;

import com.google.gson.annotations.SerializedName;

public class TaskHistoryDiff {
    private Integer[] status;

    @SerializedName("assigned_to")
    private Integer[] assignedTo;

    public Integer[] getStatus() {
        return status;
    }

    public Integer[] getAssignedTo() {
        return assignedTo;
    }
}
