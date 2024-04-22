package burndown.calculators;

import bostonmodel.burndown.BurnDownEntry;
import org.apache.http.HttpStatus;
import serviceutil.DateUtil;
import spark.Response;
import taiga.TaigaClient;
import taiga.models.sprint.Sprint;
import taiga.models.sprint.UserStoryDetail;
import taiga.models.userstories.UserStoryInterface;
import burndown.util.BVUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BusinessValueBurndown implements BurndownCalculator {
    private final HashMap<Integer, Double> businessValues;

    public BusinessValueBurndown() {
        this.businessValues = new HashMap<>();
    }

    private double calculateTotalBusinessValue(List<UserStoryDetail> userStories) {
        double tempTotal = 0;
        userStories
                .parallelStream()
                .forEach(story -> {
                    Double bv = BVUtil.getBusinessValueForUserStory(story);
                    businessValues.put(story.getId(), bv);
                });
        for (UserStoryInterface userStory : userStories) {
            tempTotal += businessValues.get(userStory.getId());
        }
        return tempTotal;
    }

    @Override
    public List<BurnDownEntry> calculate(Sprint sprint) {
        AtomicReference<List<UserStoryDetail>> userStories = new AtomicReference<>();

        TaigaClient.getUserStoryAPI().listMilestoneUserStories(sprint.getId(), result -> {
            userStories.set(List.of(result.getContent()));
        }).join();

        double businessValueTotal = calculateTotalBusinessValue(userStories.get());


        List<BurnDownEntry> burndown = new ArrayList<>();

        LocalDate start = DateUtil.toLocal(sprint.getEstimatedStart());
        LocalDate end = DateUtil.toLocal(sprint.getEstimatedFinish());

        if (userStories.get() == null) {
            return start.datesUntil(end.plusDays(1)).map(d -> new BurnDownEntry(0, 0, DateUtil.toDate(d)))
                    .collect(Collectors.toList());
        }

        List<LocalDate> sprintDates = start.datesUntil(end.plusDays(1)).toList();

        for (int i = 0; i < sprintDates.size(); i++) {
            double value = businessValueTotal;
            if (i != 0) {
                value = burndown.get(i - 1).getCurrent();
            }
            for (UserStoryDetail userStoryDetail : userStories.get()) {
                if (userStoryDetail.getFinishDate() != null
                        && DateUtil.toLocal(userStoryDetail.getFinishDate()).equals(sprintDates.get(i))) {
                    value = value - businessValues.get(userStoryDetail.getId());
                }
            }

            double ideal = 0.0;
            if (sprintDates.size() - 1 > 0) {
                ideal = businessValueTotal - ((businessValueTotal / (sprintDates.size() - 1)) * (i));
            }
            burndown.add(new BurnDownEntry(Math.max(0, ideal), value, DateUtil.toDate(sprintDates.get(i))));
        }

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
