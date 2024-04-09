package pbhealth;

import bostonmodel.pbhealth.PBHealthMetrics;
import taiga.models.sprint.UserStoryDetail;

import java.util.List;

public class PBHealthService {
    // calculate PB health
    public static PBHealthMetrics calculatePBHealth(int projectId) {
        PBHealthHelper pbHealthHelper = new PBHealthHelper(projectId);
        List<UserStoryDetail> groomedUserStories = pbHealthHelper.getGroomedPB();
        List<UserStoryDetail> notGroomedUserStories = pbHealthHelper.getNotGroomedPB();

        int totalUserStoryCount = groomedUserStories.size() + notGroomedUserStories.size();
        double ratio = 0;
        if (totalUserStoryCount > 0) {
            ratio = (double) groomedUserStories.size() / totalUserStoryCount;
        }

        return new PBHealthMetrics(ratio, groomedUserStories.size(), totalUserStoryCount);
    }
}