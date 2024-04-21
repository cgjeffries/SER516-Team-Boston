package ui.metrics.taskchurn;

import javafx.scene.layout.StackPane;
import taiga.models.sprint.Sprint;
import ui.services.TaskChurnService;
import ui.services.TaskExcessService;

public class TaskChurn extends StackPane {
    private final TaskChurnService service;

    public TaskChurn() {
        this.service = new TaskChurnService();
        this.init();
    }

    private void init() {
        //TODO
    }

    public TaskChurnService getService() {
        return service;
    }

    public void recalculate(Sprint sprint) {
        this.service.recalculate(sprint);
    }
}
