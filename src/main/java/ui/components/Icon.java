package ui.components;

import org.kordamp.ikonli.boxicons.BoxiconsLogos;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.boxicons.BoxiconsSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class Icon extends FontIcon {
    public Icon(BoxiconsRegular icon) {
        super(icon);
    }
    public Icon(BoxiconsSolid icon) {
        super(icon);
    }
    public Icon(BoxiconsLogos icon) {
        super(icon);
    }

    public Icon(BoxiconsRegular icon, int size) {
        this(icon);
        this.setStyle(this.getStyle() + "-fx-icon-size: " + size + "px;");
    }

    public Icon(BoxiconsSolid icon, int size) {
        this(icon);
        this.setStyle(this.getStyle() + "-fx-icon-size: " + size + "px;");
    }

    public Icon(BoxiconsLogos icon, int size) {
        this(icon);
        this.setStyle(this.getStyle() + "-fx-icon-size: " + size + "px;");
    }
}
