package ui.metrics.burndown;

import taiga.TaigaClient;
import taiga.models.sprint.Days;
import taiga.models.sprint.Sprint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class TaskBurndown implements BurndownCalculator {

    public TaskBurndown() {
    }

    @Override
    public List<BurnDownEntry> calculate(Sprint sprint) {
        AtomicReference<List<Days>> days = new AtomicReference<>();

        CompletableFuture<Void> future = TaigaClient.getSprintStatsAPI().getSprintStats(sprint.getId(), result -> {
            days.set(List.of(result.getContent().getDays()));
        });

        future.join(); // wait for the request to complete

        List<BurnDownEntry> burndown = new ArrayList<>();

        days.get().forEach(
                d -> burndown.add(new BurnDownEntry(Math.max(0, d.getOptimalPoints()), d.getOpenPoints(), d.getDay())));

        return burndown;
    }
}
