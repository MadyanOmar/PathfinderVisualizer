package controller;

import model.Panel;
import view.PanelView;

public class PanelController {
    private final Panel model;
    private final PanelView view;

    public PanelController(Panel model, PanelView view) {
        this.model = model;
        this.view = view;

        // Bind UI to model
        model.selectedAlgorithmProperty().bind(view.getAlgorithmComboBox().valueProperty());
        model.selectedBoardSizeProperty().bind(view.getBoardSizeComboBox().valueProperty());
    }

    public Panel getModel() {
        return model;
    }

    public PanelView getView() {
        return view;
    }

    public void onStart(Runnable callback) {
        view.getStartButton().setOnAction(e -> callback.run());
    }

    public void onReset(Runnable callback) {
        view.getResetButton().setOnAction(e -> callback.run());
    }
}
