package taiga.model.query.customattributes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class UserStoryCustomAttributesValues {

    @SerializedName("attributes_values")
    @Expose
    private AttributesValues attributesValues;

    @SerializedName("user_story")
    @Expose
    private Integer userStory;

    @SerializedName("version")
    @Expose
    private Integer version;

    public AttributesValues getAttributesValues() {
    return attributesValues;
    }

    public void setAttributesValues(AttributesValues attributesValues) {
    this.attributesValues = attributesValues;
    }

    public Integer getUserStory() {
    return userStory;
    }

    public void setUserStory(Integer userStory) {
    this.userStory = userStory;
    }

    public Integer getVersion() {
    return version;
    }

    public void setVersion(Integer version) {
    this.version = version;
    }

}