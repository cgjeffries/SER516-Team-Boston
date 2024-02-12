package taiga.model.query.sprint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Epic {

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("project")
    @Expose
    private Project project;

    @SerializedName("ref")
    @Expose
    private Integer ref;

    @SerializedName("subject")
    @Expose
    private String subject;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
