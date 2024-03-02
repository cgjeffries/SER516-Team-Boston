package ui.services;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import ui.metrics.groomrate.GroomRateCalculator;
import ui.metrics.groomrate.GroomRateItem;

import java.util.Date;
import java.util.List;

public class GroomRateService extends Service<Object> {
    private int projectId;
    private Date start;
    private Date end;

    private final SimpleIntegerProperty modifiedStoryCount;
    private final SimpleIntegerProperty totalStoryCount;
    private final DoubleProperty groomRateRatio;

    public GroomRateService(int projectId) {
        this.start = new Date();
        this.end = new Date();

        this.modifiedStoryCount = new SimpleIntegerProperty();
        this.totalStoryCount = new SimpleIntegerProperty();
        this.groomRateRatio = new SimpleDoubleProperty();
    }

    public DoubleProperty getGroomRateRatio() {
        return groomRateRatio;
    }


    public IntegerProperty getModifiedStoryCount() {
        return modifiedStoryCount;
    }

    public IntegerProperty getTotalStoryCount() {
        return totalStoryCount;
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
        return new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                GroomRateCalculator groomRateCalculator = new GroomRateCalculator(projectId);

                List<GroomRateItem> groomRateItems = groomRateCalculator.calculate(start, end);
                int modified = groomRateItems.stream()
                        .filter(GroomRateItem::isModified)
                        .toList()
                        .size();
                int total = groomRateItems.size();

                double ratio;
                if (total > 0) {
                    ratio = (double) modified / total;
                } else {
                    ratio = 0;
                }

                Platform.runLater(() -> {
                    modifiedStoryCount.set(modified);
                    totalStoryCount.set(total);
                    groomRateRatio.set(ratio);
                });

                return null;
            }
        };
    }

    public void recalculate(int projectId, Date start, Date end) {
        this.projectId = projectId;
        this.start = start;
        this.end = end;
        this.restart();
    }
}
