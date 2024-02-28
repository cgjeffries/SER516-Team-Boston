package ui.metrics.pbchange;

import org.junit.jupiter.api.Test;
import taiga.model.query.sprint.UserStoryDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PBChangeItemTest {

    @Test
    void testPBChangeItem() {
        UserStoryDetail userStoryDetail = new UserStoryDetail();
        userStoryDetail.setId(1); 

        PBChangeItem pbChangeItemAdded = new PBChangeItem(userStoryDetail, true);

        assertEquals(userStoryDetail, pbChangeItemAdded.getStoryDetail());
        assertEquals(true, pbChangeItemAdded.isAddedAfterSprint());
        assertEquals(false, pbChangeItemAdded.isRemovedAfterSprint());

        PBChangeItem pbChangeItemRemoved = new PBChangeItem(userStoryDetail, false);

        assertEquals(userStoryDetail, pbChangeItemRemoved.getStoryDetail());
        assertEquals(false, pbChangeItemRemoved.isAddedAfterSprint());
        assertEquals(true, pbChangeItemRemoved.isRemovedAfterSprint());
    }
}
