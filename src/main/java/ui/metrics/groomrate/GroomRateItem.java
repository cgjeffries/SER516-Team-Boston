package ui.metrics.groomrate;

import java.util.Date;

import taiga.model.query.sprint.UserStoryDetail;

public class GroomRateItem {

    private final UserStoryDetail storyDetail;
    private final boolean modified;
    private final Date modifiedDate;

    public GroomRateItem (Date modifiedDate, UserStoryDetail storyDetail) {
        this.storyDetail = storyDetail;
        this.modifiedDate = modifiedDate;
        if(modifiedDate != null) {
            modified = true;
        }
        else {
            modified = false;
        }
    }

    public GroomRateItem (Date modifiedDate, UserStoryDetail storyDetail, boolean modified) {
        this.storyDetail = storyDetail;
        this.modifiedDate = modifiedDate;
        this.modified = modified;
    }

    public UserStoryDetail getStoryDetail() {
        return storyDetail;
    }

    public boolean isModified() {
        return modified;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public String toString() {
        return "GroomRateItem{" +
                "storyDetail=" + storyDetail +
                ", modified=" + modified +
                '}';
    }
    
}
