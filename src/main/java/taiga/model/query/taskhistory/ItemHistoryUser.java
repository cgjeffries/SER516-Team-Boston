package taiga.model.query.taskhistory;

import com.google.gson.annotations.SerializedName;

public class ItemHistoryUser {
    private Integer pk;
    private String username;
    private String name;
    private String photo;
    private Boolean isActive;

    @SerializedName("gravatar_id")
    private String gravatarId;

    public Integer getPk() {
        return pk;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public Boolean getActive() {
        return isActive;
    }

    public String getGravatarId() {
        return gravatarId;
    }
}
