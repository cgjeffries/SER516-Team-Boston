package taiga.models.taskhistory;

import com.google.gson.annotations.SerializedName;

public class ItemHistoryDiff {
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
