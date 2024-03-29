package taiga.models.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomAttributes {

    @SerializedName("new")
    @Expose
    private List<Object> _new;
    @SerializedName("changed")
    @Expose
    private List<Object> changed;
    @SerializedName("deleted")
    @Expose
    private List<Object> deleted;

    public List<Object> getNew() {
        return _new;
    }

    public void setNew(List<Object> _new) {
        this._new = _new;
    }

    public List<Object> getChanged() {
        return changed;
    }

    public void setChanged(List<Object> changed) {
        this.changed = changed;
    }

    public List<Object> getDeleted() {
        return deleted;
    }

    public void setDeleted(List<Object> deleted) {
        this.deleted = deleted;
    }

}
