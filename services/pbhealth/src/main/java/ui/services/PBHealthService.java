package ui.services;

import taiga.models.sprint.UserStoryDetail;
import ui.metrics.pbHealth.PBHealthHelper;

import java.util.List;

public class PBHealthService {
    private int projectId;

    public PBHealthService() {

    }

    // calculate PB health
    public PBHealthMetrics calculatePBHealth(int projectId) {
        this.projectId = projectId;
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

    public static class PBHealthMetrics {
        private final double pbHealthRatio;
        private final int groomedStoryCount;
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

}