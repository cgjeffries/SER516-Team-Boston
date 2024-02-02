package ui.components;

import org.kordamp.ikonli.javafx.FontIcon;

public class Icon extends FontIcon {
    public Icon(String code) {
        super(code);
    }

    public Icon(String code, int size) {
        this(code);

        this.setStyle(this.getStyle() + "-fx-icon-size: " + size + "px;");
    }
}
