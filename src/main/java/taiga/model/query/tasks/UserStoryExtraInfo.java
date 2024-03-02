package taiga.model.query.tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import taiga.model.query.common.Epic;

public class UserStoryExtraInfo {

    @SerializedName("epics")
    @Expose
    private List<Epic> epics = null;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("ref")
    @Expose
    private Integer ref;

    @SerializedName("subject")
    @Expose
    private String subject;

    public List<Epic> getEpics() {
        return epics;
    }

    public void setEpics(List<Epic> epics) {
        this.epics = epics;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
