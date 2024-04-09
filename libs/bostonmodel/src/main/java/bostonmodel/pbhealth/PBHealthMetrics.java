package bostonmodel.pbhealth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PBHealthMetrics {
    @SerializedName("pb_health_ratio")
    @Expose
    private final double pbHealthRatio;

    @SerializedName("groomed_story_count")
    @Expose
    private final int groomedStoryCount;

    @SerializedName("total_story_count")
    @Expose
    private final int totalStoryCount;

    public PBHealthMetrics(double pbHealthRatio, int groomedStoryCount, int totalStoryCount) {
        this.pbHealthRatio = pbHealthRatio;
        this.groomedStoryCount = groomedStoryCount;
        this.totalStoryCount = totalStoryCount;
    }

    // Getters
    public double getPbHealthRatio() {
        return pbHealthRatio;
    }

    public int getGroomedStoryCount() {
        return groomedStoryCount;
    }

    public int getTotalStoryCount() {
        return totalStoryCount;
    }
}
