package taiga.model.query.sprint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusExtraInfo {

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;

    @SerializedName("name")
    @Expose
    private String name;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
