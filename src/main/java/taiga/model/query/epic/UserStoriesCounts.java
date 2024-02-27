package taiga.model.query.epic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserStoriesCounts {

    @SerializedName("progress")
    @Expose
    private Double progress;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}