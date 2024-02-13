package taiga.util.burndown;

import taiga.api.SprintStatsAPI;
import taiga.model.query.sprint.Days;
import taiga.model.query.sprint.Sprint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class TaskBurndown implements BurndownCalculator {
    private final SprintStatsAPI sprintStatsAPI;

    public TaskBurndown() {
        this.sprintStatsAPI = new SprintStatsAPI();
    }
    @Override
    public List<BurnDownEntry> calculate(Sprint sprint) {
        AtomicReference<List<Days>> days = new AtomicReference<>();

        CompletableFuture<Void> future = this.sprintStatsAPI.getSprintStats(sprint.getId(), result -> {
            days.set(List.of(result.getContent().getDays()));
        });

        future.join(); //wait for the request to complete

        List<BurnDownEntry> burndown = new ArrayList<>();

        days.get().forEach(d -> burndown.add(new BurnDownEntry(Math.max(0, d.getOptimalPoints()), d.getOpenPoints(), d.getDay())));

        burndown.forEach(System.out::println);
        return burndown;
    }
}
