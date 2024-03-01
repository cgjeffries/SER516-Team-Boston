package ui.metrics.scopechange;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import ui.services.ScopeChangeService;

public class ScopeChange extends StackPane {
    private final ScopeChangeService service;

    public ScopeChange() {
        // Initialize the test service
        this.service = new ScopeChangeService();
        initUI();
    }

    private void initUI() {
        // Test component
        Label placeholderLabel = new Label("Scope Change UI testing");
        this.getChildren().add(placeholderLabel);
    }
}
