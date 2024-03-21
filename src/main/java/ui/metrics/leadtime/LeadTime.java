package ui.metrics.leadtime;

import java.util.Date;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import taiga.model.query.sprint.Sprint;
import ui.components.Icon;
import ui.services.LeadTimeService;

public class LeadTime extends StackPane {
    private final LeadTimeService service;

    private final TabPane tabPane;

    public LeadTime(){
        this.service = new LeadTimeService();
        this.tabPane = new TabPane();
        this.init();
    }

    private void init(){
        Tab usLeadTimeTab = createLeadTimeTab(
            "User Story",
            new Icon(BoxiconsRegular.USER),
            this.service.getInBacklogStories(),
            this.service.getInSprintStories(),
            this.service.getInProgressStories(),
            this.service.getReadyForTestStories(),
            this.service.getDoneStories());

        tabPane.getTabs().setAll(usLeadTimeTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        getChildren().add(tabPane);
        this.service.start();
    }

    private Tab createLeadTimeTab(
        String name, Icon icon,
        ObservableList<XYChart.Data<String, Number>> inBacklog,
        ObservableList<XYChart.Data<String, Number>> inSprint,
        ObservableList<XYChart.Data<String, Number>> inProgress,
        ObservableList<XYChart.Data<String, Number>> readyForTest,
        ObservableList<XYChart.Data<String, Number>> done){
        StackPane root = new StackPane();

        Tab tab = new Tab(name);
        tab.setGraphic(icon);

        ProgressIndicator progress = new ProgressIndicator(-1d);
        progress.visibleProperty().bind(this.service.runningProperty());

        CategoryAxis date = new CategoryAxis();
        date.setLabel("Date");
        NumberAxis value = new NumberAxis();
        value.setLabel("User stories");

        XYChart.Series<String, Number> inBacklogSeries = new XYChart.Series<>(inBacklog);
        inBacklogSeries.setName("In Backlog");
        XYChart.Series<String, Number> inSprintSeries = new XYChart.Series<>(inSprint);
        inSprintSeries.setName("In Sprint");
        XYChart.Series<String, Number> inProgressSeries = new XYChart.Series<>(inProgress);
        inProgressSeries.setName("In Progress");
        XYChart.Series<String, Number> readyForTestSeries = new XYChart.Series<>(readyForTest);
        readyForTestSeries.setName("Ready for Test");
        XYChart.Series<String, Number> doneSeries = new XYChart.Series<>(done);
        doneSeries.setName("DONE"); //"Done" MEANS "DONE!!!!!"

        StackedAreaChart<String, Number> chart = new StackedAreaChart<>(date, value);

        chart.getData().addAll(doneSeries, readyForTestSeries, inProgressSeries, inSprintSeries, inBacklogSeries);


        chart.setAnimated(false);
        chart.visibleProperty().bind(this.service.runningProperty().not());

        root.getChildren().add(chart);
        root.getChildren().add(progress);

        tab.setContent(root);

        return tab;
    }

    public void switchSprint(Sprint sprint) {
        this.service.recalculate(sprint);
    }

    public void switchDates(Integer projectId, Date startDate, Date endDate){
        this.service.recalculate(projectId, startDate, endDate);
    }

    public void focusFirstTab() {
        tabPane.getSelectionModel().selectFirst();
    }
}
