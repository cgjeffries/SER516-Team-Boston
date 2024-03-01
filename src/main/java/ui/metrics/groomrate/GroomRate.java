package ui.metrics.groomrate;

import atlantafx.base.controls.Spacer;
import atlantafx.base.theme.Styles;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.Date;

public class GroomRate extends StackPane {

    private final ReadOnlyObjectProperty<LocalDate> endDate;
    private final ReadOnlyObjectProperty<LocalDate> startDate;

    public GroomRate(ReadOnlyObjectProperty<LocalDate> startDate, ReadOnlyObjectProperty<LocalDate> endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.init();
    }
    private void init() {
        VBox.setVgrow(this, Priority.ALWAYS);

        StackPane root = new StackPane();
        VBox.setVgrow(root, Priority.ALWAYS);

        Text groomRate = new Text("0.500");
        groomRate.getStyleClass().add(Styles.TITLE_1);


        Text groomRateDesc = new Text("Ratio of Stories groomed between 02/20/2024 and 02/29/2024");
        groomRateDesc.getStyleClass().add(Styles.TEXT_CAPTION);

        Spacer spacer = new Spacer(10);

        Text modifiedLabel = new Text("6 modified");
        modifiedLabel.getStyleClass().add(Styles.TITLE_4);

        Text totalLabel = new Text("12 total stories");
        totalLabel.getStyleClass().add(Styles.TITLE_4);

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
        VBox.setVgrow(container, Priority.ALWAYS);

        ProgressIndicator progress = new ProgressIndicator(-1d);

//        root.getChildren().add(progress);
        root.getChildren().add(container);

        getChildren().add(root);
    }

    public void recalculate(Date start, Date end) {

    }
}
