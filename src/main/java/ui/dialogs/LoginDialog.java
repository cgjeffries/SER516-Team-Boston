package ui.dialogs;

import atlantafx.base.controls.PasswordTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ui.Launcher;

public class LoginDialog extends ModalDialog {
    private HBox body;

    @FXML
    private TextField username;

    @FXML
    private PasswordTextField password;

    @FXML
    private Button login;

    @FXML
    private Hyperlink taiga_url;

    public LoginDialog() {
        super("/fxml/login.fxml");
    }

    @Override
    protected Object getController() {
        return this;
    }

    @Override
    protected Node getHeader() {
        return null;
    }

    @Override
    protected Node getBody() {
        if (body == null) {
            body = new HBox();
        }
        return body;
    }

    @Override
    protected void onFocus() {

    }

    @Override
    protected String getName() {
        return "Log In";
    }

    @FXML
    private void doLogin() {

    }

    @FXML
    private void openTaiga() {
        Launcher.openUrl("https://taiga.io");
    }

    @Override
    protected void onLoaded() {
        login.setDefaultButton(true);
    }
}
