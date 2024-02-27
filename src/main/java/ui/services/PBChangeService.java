package ui.services;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javafx.scene.chart.XYChart;
import taiga.api.UserStoryAPI;
import taiga.model.query.sprint.Sprint;
import taiga.model.query.sprint.UserStory;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.model.query.userstories.UserStoryInterface;
import taiga.util.timeAnalysis.LeadTimeStats;
import ui.metrics.pbchange.PBChangeCalculator;
import ui.metrics.pbchange.PBChangeItem;


public class PBChangeService extends Service<Object> {
    private Sprint sprint;
    private PBChangeCalculator pbChangeCalculator;

    private final ObservableList<UserStoryDetail> addedUserStories;
    private final ObservableList<UserStoryDetail> removedUserStories;

    public PBChangeService() {
        this.pbChangeCalculator = new PBChangeCalculator();
        this.addedUserStories = FXCollections.observableArrayList();
        this.removedUserStories = FXCollections.observableArrayList();
    }

    public ObservableList<UserStoryDetail> getAddedUserStories() {
        return addedUserStories;
    }

    public ObservableList<UserStoryDetail> getRemovedUserStories() {
        return removedUserStories;
    }

    public void recalculate(Sprint sprint) {
        this.sprint = sprint;
        this.restart();
    }

    @Override
    protected void failed() {
        super.failed();
        Throwable exception = getException();
        if (exception != null) {
            exception.printStackTrace();
        }
    }

    private void updatePBChanges(){
        List<PBChangeItem> changes = pbChangeCalculator.calculate(this.sprint.getProject(), this.sprint);
        addedUserStories.setAll(
            changes.stream()
                .filter(PBChangeItem::isAddedAfterSprint)
                .map(PBChangeItem::getStoryDetail)
                .toList()
        );

        removedUserStories.setAll(
            changes.stream()
                .filter(PBChangeItem::isRemovedAfterSprint)
                .map(PBChangeItem::getStoryDetail)
                .toList()
        );
    }


    @Override
    protected Task<Object> createTask() {
        return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                if (sprint == null) {
                    return null;
                }

                Platform.runLater(() -> {
                    updatePBChanges();

                });

                return null;
            }
        };
    }
}
