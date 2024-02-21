package ui.components.screens;

import java.net.URL;
import java.util.ResourceBundle;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.boxicons.BoxiconsSolid;

import atlantafx.base.controls.ModalPane;
import atlantafx.base.theme.Styles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import ui.components.Icon;
import ui.dialogs.DialogManager;
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
    private Button home;

    @FXML
    private Button settings;

    @FXML
    private Button account;

    @FXML
    private ModalPane modalPane;

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
        FXMLLoader root = FXMLManager.load("/fxml/sidebar.fxml", getRoot(), getController());
        FXMLManager.load("/fxml/" + fxmlPath + ".fxml", root.getNamespace().get("root"),
                getController());

        this.contentLoaded = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!this.rootLoaded) {
            this.rootLoaded = true;
            initializeRoot();
            return;
        }
        this.contentLoaded = true;
        setup();
    }

    private void initializeRoot() {
        home.setGraphic(new Icon(BoxiconsSolid.HOME, 28));
        home.getStyleClass().addAll(Styles.FLAT);
        home.setOnAction((e) -> screenManager.switchScreen("Metric Selection"));

        account.setGraphic(new Icon(BoxiconsSolid.USER, 28));
        account.getStyleClass().addAll(Styles.FLAT);

        settings.setGraphic(new Icon(BoxiconsRegular.SLIDER, 28));
        settings.getStyleClass().addAll(Styles.FLAT);
        settings.setOnAction((e) -> DialogManager.show("Settings", modalPane));
    }

    public String getId() {
        return this.id;
    }

    protected abstract void onFocused();

    protected abstract void setup();
}
