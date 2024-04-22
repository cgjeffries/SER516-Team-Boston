package calculators;

import bostonmodel.burndown.BurnDownEntry;
import org.apache.http.HttpStatus;
import spark.Response;
import taiga.TaigaClient;
import taiga.models.sprint.Days;
import taiga.models.sprint.Sprint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class TaskBurndown implements BurndownCalculator {

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

    public List<BurnDownEntry> calculate(Response response, int sprintId) {
        AtomicReference<Sprint> sprint = new AtomicReference<>();
        TaigaClient.getSprintAPI().getSprint(sprintId, result -> {
            if (result.getStatus() == HttpStatus.SC_OK) {
                sprint.set(result.getContent());
            }
        }).join();

        if (sprint.get() == null) {
            response.status(HttpStatus.SC_BAD_REQUEST);
            return new ArrayList<>();
        }

        return calculate(sprint.get());
    }
}
