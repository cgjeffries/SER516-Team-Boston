package ui.metrics.groomrate;

import atlantafx.base.controls.Spacer;
import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import settings.Settings;
import ui.services.GroomRateService;
import ui.util.DateUtil;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class GroomRate extends StackPane {

    private final ReadOnlyObjectProperty<LocalDate> endDate;
    private final ReadOnlyObjectProperty<LocalDate> startDate;
    private final GroomRateService service;

    public GroomRate(ReadOnlyObjectProperty<LocalDate> startDate, ReadOnlyObjectProperty<LocalDate> endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.service = new GroomRateService(Settings.get().getAppModel().getCurrentProject().get().getId());
        this.init();
    }

    private void init() {
        VBox.setVgrow(this, Priority.ALWAYS);

        StackPane root = new StackPane();
        VBox.setVgrow(root, Priority.ALWAYS);

        Text groomRate = new Text();
        groomRate.textProperty().bind(Bindings.createStringBinding(() -> {
            NumberFormat percentageFormat = NumberFormat.getPercentInstance();
            percentageFormat.setMinimumFractionDigits(2);
            return percentageFormat.format(this.service.getGroomRateRatio().get());
        }, this.service.getGroomRateRatio()));
        groomRate.getStyleClass().add(Styles.TITLE_1);

        Text groomRateDesc = new Text();
        groomRateDesc.textProperty().bind(Bindings.createStringBinding(() -> {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            String start = format.format(DateUtil.toDate(this.startDate.getValue()));
            String end = format.format(DateUtil.toDate(this.endDate.getValue()));
            return "Ratio of stories groomed between " + start + " and " + end;
        }, this.startDate, this.endDate));
        groomRateDesc.getStyleClass().add(Styles.TEXT_CAPTION);

        Spacer spacer = new Spacer(10);

        Text modifiedLabel = new Text();
        modifiedLabel.getStyleClass().add(Styles.TITLE_4);
        modifiedLabel.textProperty().bind(Bindings.createStringBinding(() -> this.service.getModifiedStoryCount().get() + " modified stories", this.service.getModifiedStoryCount()));

        Text totalLabel = new Text();
        totalLabel.getStyleClass().add(Styles.TITLE_4);
        totalLabel.textProperty().bind(Bindings.createStringBinding(() -> this.service.getTotalStoryCount().get() + " total stories", this.service.getTotalStoryCount()));

        VBox container = new VBox(
                groomRate,
                groomRateDesc,
                spacer,
                modifiedLabel,
                totalLabel
        );
        container.setSpacing(5);
        container.setPrefHeight(VBox.USE_PREF_SIZE);
        container.setAlignment(Pos.CENTER);
        container.visibleProperty().bind(this.service.runningProperty().not());
        VBox.setVgrow(container, Priority.ALWAYS);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        root.getChildren().add(progress);
        root.getChildren().add(container);

        getChildren().add(root);
    }

    public void recalculate(Date start, Date end) {
        this.service.recalculate(Settings.get().getAppModel().getCurrentProject().get().getId(), start, end);
    }
}
