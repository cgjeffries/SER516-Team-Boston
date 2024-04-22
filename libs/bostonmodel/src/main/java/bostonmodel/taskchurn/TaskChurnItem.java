package bostonmodel.taskchurn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;

public class TaskChurnItem {

    @SerializedName("churnDate")
    @Expose
    private final LocalDate churnDate;

    @SerializedName("churnCount")
    @Expose
    private Integer churnCount;

    public TaskChurnItem(LocalDate churnDate, Integer churnCount){
        this.churnDate = churnDate;
        this.churnCount = churnCount;
    }

    public LocalDate getChurnDate() {
        return churnDate;
    }

    public Integer getChurnCount() {
        return churnCount;
    }

    public void setChurnCount(Integer churnCount) {
        this.churnCount = churnCount;
    }

    @Override
    public String toString() {
        return "taskchurn.TaskChurnItem{" +
            "Date=" + churnDate.toString() +
            ", churnCount=" + churnCount +
            '}';
    }
}
