package bostonmodel.pbchange;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PBChangeMetrics {
    @SerializedName("pb_change_items")
    @Expose
    private List<PBChangeItem> pbChangeItems;

    public PBChangeMetrics(List<PBChangeItem> items) {
        this.pbChangeItems = items;
    }

    public List<PBChangeItem> getPbChangeItems() {
        return pbChangeItems;
    }
}
