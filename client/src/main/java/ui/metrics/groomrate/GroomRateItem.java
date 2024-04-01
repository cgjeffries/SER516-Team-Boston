package ui.metrics.groomrate;


import taiga.models.taskhistory.ItemHistory;
import taiga.models.userstories.UserStoryInterface;

import java.util.List;

public class GroomRateItem {

    private final UserStoryInterface storyDetail;
    private final boolean modified;
    private final List<ItemHistory> modifications;

    public GroomRateItem(List<ItemHistory> modifications, UserStoryInterface storyDetail) {
        this.storyDetail = storyDetail;
        this.modifications = modifications;
        modified = !modifications.isEmpty();
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
