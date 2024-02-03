package taiga.model.query.sprint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Days {

    @SerializedName("day")
    @Expose
    private Date day;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("open_points")
    @Expose
    private Double openPoints;

    @SerializedName("optimal_points")
    @Expose
    private Double optimalPoints;

    public Date getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public Double getOpenPoints() {
        return openPoints;
    }

    public Double getOptimalPoints() {
        return optimalPoints;
    }
}
