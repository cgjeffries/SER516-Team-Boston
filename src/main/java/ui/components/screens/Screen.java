package ui.components.screens;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.boxicons.BoxiconsSolid;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import settings.Settings;
import taiga.model.auth.LoginResponse;
import taiga.model.auth.Tokens;
import taiga.util.AuthTokenSingleton;
import ui.components.Icon;
import ui.dialogs.DialogManager;
import ui.util.DefaultLogoResolver;
import ui.util.FXMLManager;

/**
 * A screen is a managed container for UI content. To facilitate transitions
 * between screens, see {@link ScreenManager ScreenManager}
 *
 * @param <T> The type of root component this screen should have. This needs to
 *            be the same as the root element in the corresponding FXML.
 */
public abstract class Screen<T extends Parent> implements Initializable {
    private final String id;
    private final String fxmlPath;
    protected ScreenManager screenManager;

    protected boolean contentLoaded;
    protected boolean rootLoaded;

    @FXML
    private Button settings;

    @FXML
    private Button login_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private ModalPane modalPane;

    @FXML
    private ImageView user_logo;

    @FXML
    private Label login_info;

    /**
     * Create a screen instance
     *
     * @param screenManager a ScreenManager instance
     * @param id            A unique id for the scene.
     * @param fxmlFilename  The fxml file to load for this screen.
     */
    public Screen(ScreenManager screenManager, String id, String fxmlFilename) {
        this.id = id;
        this.fxmlPath = fxmlFilename;
        this.screenManager = screenManager;
        this.rootLoaded = false;
        this.contentLoaded = false;
    }

    /**
     * Gets the root element. This needs to be the same as the root element in the
     * corresponding FXML.
     *
     * @return the root element
     */
    public abstract T getRoot();

    /**
     * Get the controller for the screen. This should be the immediate superclass of
     * the screen (so return `this`)
     *
     * @return The controller for the screen
     */
    public abstract Object getController();

    /**
     * Loads the screen's FXML. Used for lazy loading.
     * If you are using {@link ScreenManager}, you should not call this manually.
     */
    public void load() {
        if (this.contentLoaded) {
            return;
        }
        FXMLLoader root = FXMLManager.load("/fxml/app_root.fxml", getRoot(), getController());
        FXMLManager.load("/fxml/" + fxmlPath + ".fxml", root.getNamespace().get("root"),
                getController());

        this.contentLoaded = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!this.rootLoaded) {
            initializeRoot();
            this.rootLoaded = true;
            return;
        }
        this.contentLoaded = true;
        setup();
    }

    private void initializeRoot() {
        login_btn.setGraphic(new Icon(BoxiconsRegular.LOG_IN, 16));
        login_btn.getStyleClass().addAll(Styles.ROUNDED);
        login_btn.setOnAction((e) -> DialogManager.show("Log In", modalPane));
        login_btn.visibleProperty().bind(Settings.get().getAppModel().getUser().isNull());
        login_btn.managedProperty().bind(login_btn.visibleProperty());

        logout_btn.setGraphic(new Icon(BoxiconsRegular.LOG_OUT, 16));
        logout_btn.getStyleClass().addAll(Styles.ROUNDED);
        logout_btn.setOnAction((e) -> {
            AuthTokenSingleton.getInstance().setTokens(new Tokens(null, null));
            Settings.get().getAppModel().setUser(null);
        });
        logout_btn.visibleProperty().bind(Settings.get().getAppModel().getUser().isNotNull());
        logout_btn.managedProperty().bind(logout_btn.visibleProperty());

        settings.setGraphic(new Icon(BoxiconsSolid.COG, 16));
        settings.getStyleClass().addAll(Styles.ROUNDED);
        settings.setOnAction((e) -> DialogManager.show("Settings", modalPane));

        login_info.textProperty().bind(Bindings.createStringBinding(() -> {
            LoginResponse user = Settings.get().getAppModel().getUser().get();
            if (user == null) {
                return "";
            }
            return "Logged in as " + user.getUsername();
        }, Settings.get().getAppModel().getUser()));

        user_logo.imageProperty().bind(Bindings.createObjectBinding(() -> {
            LoginResponse user = Settings.get().getAppModel().getUser().get();
            if (user == null) {
                return null;
            }
            return DefaultLogoResolver.getUserLogoImage(user);
        }, Settings.get().getAppModel().getUser()));
    }

    public String getId() {
        return this.id;
    }

    protected abstract void onFocused();

    protected abstract void setup();
}
