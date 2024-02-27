package ui.metrics.pbchange;

public class PBChangeItem {
    private final boolean addedAfterSprint;
    private final boolean removedAfterSprint;

    public PBChangeItem(boolean added) {
        this.addedAfterSprint = added;
        this.removedAfterSprint = !added;
    }

    public boolean isAddedAfterSprint() {
        return addedAfterSprint;
    }

    public boolean isRemovedAfterSprint() {
        return removedAfterSprint;
    }
}
