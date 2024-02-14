package taiga.model.query.taskhistory;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

// https://docs.taiga.io/api.html#history-get
public class ItemHistory implements Comparable<ItemHistory> {
    private String id;

    private ItemHistoryUser user;

    @SerializedName("created_at")
    private Date createdAt;

    private String key;

    private ItemHistoryDiff diff;

    private ItemHistoryValues values;

    @SerializedName("values_diff")
    private ItemHistoryValuesDiff valuesDiff;

    public String getId() {
        return id;
    }

    public ItemHistoryUser getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getKey() {
        return key;
    }

    public ItemHistoryDiff getDiff() {
        return diff;
    }

    public ItemHistoryValues getValues() {
        return values;
    }

    public ItemHistoryValuesDiff getValuesDiff() {
        return valuesDiff;
    }

    @Override
    public int compareTo(ItemHistory history){
        return this.createdAt.compareTo(history.getCreatedAt());
    }
}
