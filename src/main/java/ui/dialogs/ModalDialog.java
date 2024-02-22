package ui.dialogs;

import atlantafx.base.controls.Card;
import atlantafx.base.layout.ModalBox;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import ui.util.FXMLManager;

public abstract class ModalDialog extends ModalBox {
    private final Card content;

    public ModalDialog(String bodyFXML) {
        super("#modalPane");
        this.content = new Card();

        FXMLManager.load(bodyFXML, getBody(), getController());

        setMinWidth(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMaxHeight(USE_PREF_SIZE);
        setPrefSize(800, 600);

        content.setHeader(getHeader());
        content.setBody(getBody());

        AnchorPane.setBottomAnchor(content, 0d);
        AnchorPane.setTopAnchor(content, 0d);
        AnchorPane.setLeftAnchor(content, 0d);
        AnchorPane.setRightAnchor(content, 0d);

        addContent(content);
        onLoaded();
    }

    protected abstract Object getController();

    protected abstract Node getHeader();

    protected abstract Node getBody();

    protected abstract String getName();
    
    protected void onFocus() {

    }

    protected void onLoaded() {

    }
}
