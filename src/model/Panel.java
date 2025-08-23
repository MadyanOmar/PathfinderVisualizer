package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Panel {
	private StringProperty selectedAlgorithm = new SimpleStringProperty("A*");
    private StringProperty selectedBoardSize = new SimpleStringProperty("48x20");

    public StringProperty selectedAlgorithmProperty() { return selectedAlgorithm; }
    public StringProperty selectedBoardSizeProperty() { return selectedBoardSize; }
}
