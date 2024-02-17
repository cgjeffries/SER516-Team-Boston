package taiga.util.timeAnalysis;

import java.util.Date;

public class LeadTimeEntry {
    private final Date createdInBacklog;
    private final Date addedToSprint;

    private final Date inProgress;

    private final Date done;

    public LeadTimeEntry(Date createdInBacklog, Date addedToSprint, Date inProgress, Date done){
        this.createdInBacklog = createdInBacklog;
        this.addedToSprint = addedToSprint;
        this.inProgress = inProgress;
        this.done = done;
    }

    public Date getCreatedInBacklog(){
        return createdInBacklog;
    }

    public Date getAddedToSprint(){
        return addedToSprint;
    }

    public Date getInProgress(){
        return inProgress;
    }

    public Date getDone(){
        return done;
    }

    public Long getLeadTime(){
        if(createdInBacklog == null || done == null){
            return 0L;
        }
        return done.getTime() - createdInBacklog.getTime();
    }
}
