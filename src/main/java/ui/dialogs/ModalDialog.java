package ui.dialogs;

import atlantafx.base.controls.Card;
import atlantafx.base.layout.ModalBox;
import atlantafx.base.theme.Styles;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ui.util.FXMLManager;

public abstract class ModalDialog<T extends Pane> extends ModalBox {
    private final Label header;
    private final Card content;

    public ModalDialog(String title, String bodyFXML) {
        super("#modalPane");
        this.header = new Label(title);
        this.content = new Card();

        FXMLManager.load(bodyFXML, getRoot(), getController());

        setMinWidth(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMaxHeight(USE_PREF_SIZE);
        setPrefSize(800, 600);

        header.getStyleClass().add(Styles.TITLE_2);
        content.setHeader(header);
        content.setBody(getRoot());

        AnchorPane.setBottomAnchor(content, 0d);
        AnchorPane.setTopAnchor(content, 0d);
        AnchorPane.setLeftAnchor(content, 0d);
        AnchorPane.setRightAnchor(content, 0d);

        addContent(content);
    }

    protected abstract Object getController();

    protected abstract T getRoot();

    protected abstract void onFocus();

    protected abstract String getName();
}
