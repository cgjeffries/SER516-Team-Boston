package ui.tooltips;

public class TooltipPoint<T> {
    protected TooltipTextCallback<T> tooltipTextCallback;

    public void setTooltipCallback(TooltipTextCallback<T> tooltipTextCallback) {
        this.tooltipTextCallback = tooltipTextCallback;
    }

    public interface TooltipTextCallback<T> {
        String getText(T item);
    }

    public String applyTooltipCallback(T item) {
        if (this.tooltipTextCallback == null) {
            return "";
        }
        return tooltipTextCallback.getText(item);
    }
}
