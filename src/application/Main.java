package application;
	
import controller.BoardController;
import controller.PanelController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Panel;
import util.Vector;
import view.PanelView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	final int WIDTH = 1280;
	final int HEIGHT = 720;
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,WIDTH,HEIGHT);
			
			Vector boardSize = new Vector(1280, 520);
			
			Panel panelModel = new Panel();
			PanelView panelView = new PanelView();
			PanelController panelController = new PanelController(panelModel, panelView);

			BoardController boardController = new BoardController(boardSize, panelController);

			root.setTop(panelView);
			root.setBottom(boardController.getView());

			root.setTop(panelView);
			root.setBottom(boardController.getView());
			primaryStage.setResizable(false);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Pathfinder Visualizer");
			primaryStage.getIcons().add(new Image("resources/icon.jpg"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
