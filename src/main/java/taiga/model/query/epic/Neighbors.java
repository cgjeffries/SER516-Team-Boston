package taiga.model.query.epic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Neighbors {

    @SerializedName("next")
    @Expose
    private AdjacentEpic next;
    @SerializedName("previous")
    @Expose
    private AdjacentEpic previous;

    public AdjacentEpic getNext() {
        return next;
    }

    public void setNext(AdjacentEpic next) {
        this.next = next;
    }

    public AdjacentEpic getPrevious() {
        return previous;
    }

    public void setPrevious(AdjacentEpic previous) {
        this.previous = previous;
    }

}
