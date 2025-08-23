package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PanelView extends Pane {

    private ComboBox<String> boardSizeComboBox;
    private ComboBox<String> algorithmComboBox;
    private final Button startButton;
    private final Button resetButton;

    public PanelView() {
        getStyleClass().add("panel-view");
        setPrefSize(1280, 200);

        boardSizeComboBox = new ComboBox<>();
        boardSizeComboBox.getItems().addAll("Small (12x5)", "Medium (24x10)", "Large (48x20)", "Extra Large (60x25)");
        boardSizeComboBox.setValue("Medium (24x10)");

        algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll("A*", "Greedy Best-First Search", "Breadth-First Search", "Depth-First Search");
        algorithmComboBox.setValue("A*");

        
        startButton = new Button("Start");
        resetButton = new Button("Reset");

        startButton.setPrefWidth(100);
        resetButton.setPrefWidth(100);

        VBox boardOptions = new VBox(new Label("Board Size:"), boardSizeComboBox);
        VBox algoOptions = new VBox(new Label("Algorithm:"), algorithmComboBox);
        VBox actions = new VBox(startButton, resetButton);

        for (VBox vbox : new VBox[]{boardOptions, algoOptions, actions}) {
            vbox.setSpacing(5);
        }

        HBox layout = new HBox(60, boardOptions, algoOptions, actions);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(20, 40, 20, 40));
        layout.setPrefSize(1280, 200);
        layout.getStyleClass().add("panel-content");

        getChildren().add(layout);
    }

    public ComboBox<String> getBoardSizeComboBox() { return boardSizeComboBox; }
    public ComboBox<String> getAlgorithmComboBox() { return algorithmComboBox; }
    public Button getStartButton() { return startButton; }
    public Button getResetButton() { return resetButton; }
}