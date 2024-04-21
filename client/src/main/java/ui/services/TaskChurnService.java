package ui.services;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import taiga.models.sprint.Sprint;

public class TaskChurnService extends Service<Object> {
    private Sprint sprint;
    public void recalculate(Sprint sprint) {
        if (sprint != null && sprint.getId() > 0) {
            this.sprint = sprint;
            restart();
        }
    }
    @Override
    protected Task<Object> createTask() {
        return null;
    }
}
