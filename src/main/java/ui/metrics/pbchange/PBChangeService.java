package ui.metrics.pbchange;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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


public class PBChangeService extends Service<Object> {
    private Sprint sprint;

    private UserStoryAPI userStoryAPI;

    public PBChangeService() {
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

    private void updatePBChanges(ObservableList<XYChart.Data<String, Number>> data){
        AtomicReference<List<UserStoryDetail>> userStoryListReference = new AtomicReference<>();
        userStoryAPI.listProjectUserStories(sprint.getProject(), result -> {
            userStoryListReference.set(new ArrayList<>(List.of(result.getContent())));
        }).join();//TODO: why can't this be UserStoryInterface?

        for (UserStoryDetail userStoryDetail : userStoryListReference.get()){
            if(true) { //TODO: link to util code
                //data.add(userStoryDetail.getId(), userStoryDetail.getCreatedDate().getTime());
            }
        }


    }


    @Override
    protected Task<Object> createTask() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTask'");
    }
}
