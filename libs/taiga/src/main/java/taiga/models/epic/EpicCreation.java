package taiga.models.epic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpicCreation {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("project")
    @Expose
    private Integer project;

    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    // just for testing/debug
    @Override
    public String toString() {
        return "EpicCreation{" +
                "id=" + id +
                ", createdDate='" + createdDate + '\'' +
                ", subject='" + subject + '\'' +
                ", project=" + project +
                ", isClosed=" + isClosed +
                '}';
    }
}
