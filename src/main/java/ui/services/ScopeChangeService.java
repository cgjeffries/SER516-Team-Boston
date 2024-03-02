package ui.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import taiga.model.query.sprint.Sprint;
import ui.metrics.scopechange.ScopeChangeCalculator;
import ui.metrics.scopechange.ScopeChangeItem;

import java.util.List;

public class ScopeChangeService extends Service<Object> {

    private Sprint sprint;

    private final ObservableList<ScopeChangeItem> scopeChangeStories;
    private final ScopeChangeCalculator scopeChangeCalculator;

    public ScopeChangeService() {
        this.scopeChangeStories = FXCollections.observableArrayList();
        this.scopeChangeCalculator = new ScopeChangeCalculator();
    }

    public ObservableList<ScopeChangeItem> getScopeChangeStories() {
        return scopeChangeStories;
    }

    public void recalculate(Sprint sprint) {
        this.sprint = sprint;
        this.restart();
    }

    @Override
    protected Task<Object> createTask() {
        return new Task<>() {
            @Override
            protected Object call() {
                if (sprint == null) {
                    return null;
                }

                List<ScopeChangeItem> stories = new ArrayList<>(scopeChangeCalculator.calculate(sprint));
                stories.sort(Comparator.comparing(ScopeChangeItem::getChangeDate));

                Platform.runLater(() -> {
                    scopeChangeStories.setAll(stories);
                });
                return null;
            }
        };
    }
}
