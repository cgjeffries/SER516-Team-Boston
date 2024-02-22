package ui.dialogs;

import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import settings.Settings;

public class SettingsDialog extends ModalDialog {
    private VBox root;

    @FXML
    private TextField server_url;

    public SettingsDialog() {
        super("/fxml/settings.fxml");
    }

    @Override
    protected Object getController() {
        return this;
    }

    @Override
    protected Node getHeader() {
        Label header = new Label("Settings");
        header.getStyleClass().add(Styles.TITLE_2);
        return header;
    }

    @Override
    protected VBox getBody() {
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
