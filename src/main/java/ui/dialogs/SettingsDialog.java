package ui.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import settings.Settings;

public class SettingsDialog extends ModalDialog<VBox> {
    private VBox root;

    @FXML
    private TextField server_url;

    public SettingsDialog() {
        super("Settings", "/fxml/settings.fxml");
    }

    @Override
    protected Object getController() {
        return this;
    }

    @Override
    protected VBox getRoot() {
        if (root == null) {
            root = new VBox();
        }
        return root;
    }

    @Override
    protected void onFocus() {
        server_url.setText(Settings.get().getAppModel().getApiURL());
    }

    @Override
    protected String getName() {
        return "Settings";
    }

    @FXML
    private void saveSettings() {
        Settings.get().getAppModel().setApiURL(server_url.getText());
        close();
    }
}
