package ui.metrics.pbchange;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import taiga.model.query.sprint.Sprint;


public class PBChangeService extends Service<Object> {
    private Sprint sprint;

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


    @Override
    protected Task<Object> createTask() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTask'");
    }
}
