package ui.components;

import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class Multiselect<T> extends ListView<T> {
    public Multiselect() {
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setMaxHeight(100);
    }

}
