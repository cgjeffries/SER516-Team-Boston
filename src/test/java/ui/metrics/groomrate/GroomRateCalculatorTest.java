package ui.metrics.groomrate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.controlsfx.control.PropertySheet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import taiga.model.query.sprint.UserStoryDetail;
import taiga.model.query.taskhistory.ItemHistory;
import taiga.model.query.taskhistory.ItemHistoryValuesDiff;
import taiga.util.timeAnalysis.LeadTimeEntry;
import taiga.util.timeAnalysis.LeadTimeHelper;


public class GroomRateCalculatorTest {

    @Test
    public void testGroomRateCalculator(){
        LeadTimeHelper mockedLeadTimeHelper = Mockito.mock(LeadTimeHelper.class);

        ItemHistory us1History1 = new ItemHistory();
        us1History1.setCreatedAt(new Date(123, Calendar.DECEMBER, 25));
        us1History1.setValuesDiff(new ItemHistoryValuesDiff());
        ItemHistory us1History2 = new ItemHistory();
        us1History2.setCreatedAt(new Date(124, Calendar.JANUARY, 10));
        us1History2.setValuesDiff(new ItemHistoryValuesDiff());
        UserStoryDetail us1 = new UserStoryDetail();
        us1.setCreatedDate(new Date(123, Calendar.DECEMBER, 24));


        UserStoryDetail us2 = new UserStoryDetail();
        us2.setCreatedDate(new Date(123, Calendar.DECEMBER, 24));

        when(mockedLeadTimeHelper.getLeadTimeEntryList()).thenReturn(
            new ArrayList<>(Arrays.asList(
                new LeadTimeEntry(new ArrayList<>(Arrays.asList(
                    us1History1,
                    us1History2
                )), us1),
                new LeadTimeEntry(new ArrayList<>(), us2)
            ))
        );

        GroomRateCalculator groomRateCalculator = new GroomRateCalculator(mockedLeadTimeHelper);

        List<GroomRateItem> result = groomRateCalculator.calculate(new Date(124, Calendar.JANUARY, 1), new Date(124, Calendar.JANUARY, 14));

        List<GroomRateItem> validationList = new ArrayList<>(Arrays.asList(
            new GroomRateItem(new ArrayList<>(Arrays.asList(
                us1History2
            )), us1),
            new GroomRateItem(new ArrayList<>(), us2)
        ));
        assertEquals(validationList.size(), result.size());
        for(int i = 0; i < validationList.size(); i++){
            assertEquals(result.get(i).toString(), validationList.get(i).toString());
        }
    }
}
