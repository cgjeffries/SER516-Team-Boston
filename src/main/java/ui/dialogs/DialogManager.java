package ui.dialogs;

import java.util.HashMap;

import atlantafx.base.controls.ModalPane;
import javafx.scene.layout.Pane;

public class DialogManager {
    private final static HashMap<String, ModalDialog<? extends Pane>> dialogs = new HashMap<>();

    public static void add(ModalDialog<? extends Pane> dialog) {
        if (dialogs.containsKey(dialog.getName())) {
            return;
        }
        dialogs.put(dialog.getName(), dialog);
    }

    public static void show(String name, ModalPane pane) {
        ModalDialog<? extends Pane> target = dialogs.get(name);
        if (target == null) {
            System.err.println("No dialog '" + name + "' was found.");
            return;
        }
        pane.show(target);
        target.onFocus();
    }
}
