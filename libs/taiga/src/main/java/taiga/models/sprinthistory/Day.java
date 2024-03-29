package taiga.models.sprinthistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("name")
    @Expose
    private Integer name;

    @SerializedName("open_points")
    @Expose
    private Double openPoints;

    @SerializedName("optimal_points")
    @Expose
    private Double optimalPoints;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Double getOpenPoints() {
        return openPoints;
    }

    public void setOpenPoints(Double openPoints) {
        this.openPoints = openPoints;
    }

    public Double getOptimalPoints() {
        return optimalPoints;
    }

    public void setOptimalPoints(Double optimalPoints) {
        this.optimalPoints = optimalPoints;
    }

}