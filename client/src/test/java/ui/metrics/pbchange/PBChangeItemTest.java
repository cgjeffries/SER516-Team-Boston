package ui.metrics.pbchange;

import org.junit.jupiter.api.Test;
import taiga.models.sprint.UserStoryDetail;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PBChangeItemTest {

    @Test
    void testPBChangeItem() {
        UserStoryDetail userStoryDetail = new UserStoryDetail();
        userStoryDetail.setId(1);

        Date changeDate = new Date();

        PBChangeItem pbChangeItemAdded = new PBChangeItem(changeDate, userStoryDetail, true);

        assertEquals(userStoryDetail, pbChangeItemAdded.getStoryDetail());
        assertEquals(true, pbChangeItemAdded.isAddedAfterSprint());
        assertEquals(false, pbChangeItemAdded.isRemovedAfterSprint());
        assertEquals(changeDate, pbChangeItemAdded.getChangeDate());

        PBChangeItem pbChangeItemRemoved = new PBChangeItem(changeDate, userStoryDetail, false);

        assertEquals(userStoryDetail, pbChangeItemRemoved.getStoryDetail());
        assertEquals(false, pbChangeItemRemoved.isAddedAfterSprint());
        assertEquals(true, pbChangeItemRemoved.isRemovedAfterSprint());
        assertEquals(changeDate, pbChangeItemRemoved.getChangeDate());
    }
}
