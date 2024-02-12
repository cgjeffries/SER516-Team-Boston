package taiga.model.query.customattributes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

public class AttributesValues {

    @SerializedName("attributes_values")
    @Expose
    private HashMap<String, String> attributesValues;

    @SerializedName("version")
    @Expose
    private int version;

    @SerializedName("user_story")
    @Expose
    private int userStory;

    public HashMap<String, String> getAttributesValues() {
        return attributesValues;
    }

    public void setAttributesValues(HashMap<String, String> attributesValues) {
        this.attributesValues = attributesValues;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int getUserStory() {
        return userStory;
    }

    public void setUserStory(Integer userStory) {
        this.userStory = userStory;
    }
}
