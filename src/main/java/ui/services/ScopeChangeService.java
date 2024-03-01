package ui.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import taiga.model.query.sprint.UserStoryDetail;
import java.util.ArrayList;
import java.util.List;

public class ScopeChangeService extends Service<List<UserStoryDetail>> {

    public ScopeChangeService() {
        
    }

    @Override
    protected Task<List<UserStoryDetail>> createTask() {
        return new Task<>() {
            @Override
            protected List<UserStoryDetail> call() {
                // Hard-coded list of UserStoryDetail so it runs
                List<UserStoryDetail> dummyList = new ArrayList<>();
                dummyList.add(new UserStoryDetail());
                dummyList.add(new UserStoryDetail());
                return dummyList;
            }
        };
    }
}
