package taiga.model.query.epic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdjacentEpic {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ref")
    @Expose
    private Integer ref;
    @SerializedName("subject")
    @Expose
    private String subject;

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
