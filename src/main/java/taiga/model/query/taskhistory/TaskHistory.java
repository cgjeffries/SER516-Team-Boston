package taiga.model.query.taskhistory;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

// https://docs.taiga.io/api.html#history-get
public class TaskHistory {
    private String id;

    private TaskHistoryUser user;

    @SerializedName("created_at")
    private Date createdAt;

    private String key;

    private TaskHistoryDiff diff;

    private TaskHistoryValues values;

    @SerializedName("values_diff")
    private TaskHistoryValuesDiff valuesDiff;

    public String getId() {
        return id;
    }

    public TaskHistoryUser getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getKey() {
        return key;
    }

    public TaskHistoryDiff getDiff() {
        return diff;
    }

    public TaskHistoryValues getValues() {
        return values;
    }

    public TaskHistoryValuesDiff getValuesDiff() {
        return valuesDiff;
    }
}
