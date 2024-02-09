package taiga.model.query.taskhistory;

import com.google.gson.annotations.SerializedName;

public class TaskHistoryValuesDiff {
    private String[] status;

    @SerializedName("assigned_to")
    private String[] assignedTo;

    public String[] getStatus() {
        return status;
    }

    public String[] getAssignedTo() {
        return assignedTo;
    }
}
