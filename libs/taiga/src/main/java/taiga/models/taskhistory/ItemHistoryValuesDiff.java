package taiga.models.taskhistory;

import com.google.gson.annotations.SerializedName;

public class ItemHistoryValuesDiff {
    private String[] status;

    @SerializedName("assigned_to")
    private String[] assignedTo;

    private String[] milestone;

    public String[] getStatus() {
        return status;
    }

    public String[] getAssignedTo() {
        return assignedTo;
    }

    public String[] getMilestone() {
        return milestone;
    }
}
