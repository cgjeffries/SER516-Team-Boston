package ui.metrics.groomrate;

import java.util.Date;

import java.util.List;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.model.query.userstories.UserStoryInterface;

public class GroomRateItem {

    private final UserStoryInterface storyDetail;
    private final boolean modified;
    private final List<ItemHistory> modifications;

    public GroomRateItem (List<ItemHistory> modifications, UserStoryInterface storyDetail) {
        this.storyDetail = storyDetail;
        this.modifications = modifications;
        if(!modifications.isEmpty()) {
            modified = true;
        }
        else {
            modified = false;
        }
    }

    public UserStoryInterface getStoryDetail() {
        return storyDetail;
    }

    public boolean isModified() {
        return modified;
    }

    public List<ItemHistory> getModifications() {
        return modifications;
    }

    @Override
    public String toString() {
        return "GroomRateItem{" +
                "storyDetail=" + storyDetail +
                ", modified=" + modified +
                '}';
    }
    
}
