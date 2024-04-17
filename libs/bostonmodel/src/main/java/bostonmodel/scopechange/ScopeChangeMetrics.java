package bostonmodel.scopechange;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScopeChangeMetrics {
    @SerializedName("scope_change_items")
    @Expose
    private List<ScopeChangeItem> scopeChangeItems;

    public ScopeChangeMetrics(List<ScopeChangeItem> scopeChangeItems) {
        this.scopeChangeItems = scopeChangeItems;
    }

    public List<ScopeChangeItem> getScopeChangeItems() {
        return scopeChangeItems;
    }
}
