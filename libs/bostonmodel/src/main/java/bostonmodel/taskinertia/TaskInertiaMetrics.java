package bostonmodel.taskinertia;

import java.time.LocalDate;
import java.util.TreeMap;

public class TaskInertiaMetrics {
    private TreeMap<LocalDate, Double> inertia;

    public TaskInertiaMetrics(TreeMap<LocalDate, Double> inertia) {
        this.inertia = inertia;
    }

    public TreeMap<LocalDate, Double> getInertia() {
        return inertia;
    }
}
