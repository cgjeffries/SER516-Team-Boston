package ui.dialogs;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;

import atlantafx.base.controls.Message;
import atlantafx.base.controls.PasswordTextField;
import atlantafx.base.theme.Styles;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import taiga.util.services.LoginService;
import ui.Launcher;
import ui.components.Icon;

public class LoginDialog extends ModalDialog {
    private HBox body;
    private LoginService authenticationService;

    @FXML
    private TextField username;

    @FXML
    private PasswordTextField password;

    @FXML
    private Button login;

    @FXML
    private Hyperlink taiga_url;

    @FXML
    private Message login_error;

    public LoginDialog() {
        super("/fxml/login.fxml");
        this.authenticationService = new LoginService();
        login_error.setGraphic(new Icon(BoxiconsRegular.ERROR));
        login_error.getStyleClass().add(Styles.DANGER);
        login_error.managedProperty().bind(login_error.visibleProperty());
        login_error.visibleProperty().bind(Bindings.createBooleanBinding(() -> {
            return this.authenticationService.getValue() != null && !this.authenticationService.getValue();
        }, this.authenticationService.valueProperty()));
        this.authenticationService.setOnSucceeded((e) -> {
            if (this.authenticationService.getValue().equals(true)) {
                username.clear();
                password.clear();
                close();
            }
        });
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
        this.authenticationService.login(username.getText(), password.getPassword());
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
