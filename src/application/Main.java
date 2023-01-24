package application;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Node;


public class Main extends Application {
	final static int WIDTH = 1200;
	final static int HEIGHT = 850;
	private Pane root = new Pane();
	private Board b = new Board(60, 36);
	private Pathfinder p = new Pathfinder();
	private Node[] panel = Panel.GetPanel(p, b, root).GetObjects();	
	private ToggleButton placeGoal = (ToggleButton) panel[panel.length-3];
	private ToggleButton placeInit = (ToggleButton) panel[panel.length-2];
	private Button createMaze = (Button) panel[panel.length-1];
	@Override
	public void start(Stage primaryStage) {
		try {
			b.Draw(root);
			b.SetGoal(23, 30, root);
			b.SetInitial(1, 10, root);
			primaryStage.getIcons().add(new Image("Img/icon.jpg"));
			Scene scene = new Scene(root,WIDTH,HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
						
			ToggleButton placeWalls = (ToggleButton) panel[panel.length-4];
			
			scene.addEventFilter(MouseEvent.MOUSE_PRESSED, e->{
				int x=Integer.MIN_VALUE;
				int y=Integer.MIN_VALUE;
				if(e.getSceneY() > Panel.HEIGHT)
				{
					x = (int)(e.getSceneX()/GraphNode.WIDTH); 
					y = (int)((e.getSceneY()-Panel.HEIGHT)/GraphNode.HEIGHT);
				}
				
				if(placeGoal.isSelected())
				{
					placeInit.setSelected(false);
					placeWalls.setSelected(false);
					if(x!=Integer.MIN_VALUE)
					{
						b.SetGoal(x, y, root);
						b.Draw(root);
					}
				}
				else if(placeInit.isSelected())
				{
					placeGoal.setSelected(false);
					placeWalls.setSelected(false);
					if(x!=Integer.MIN_VALUE)
					{
						b.SetInitial(x, y, root);
						b.Draw(root);
					}
				}
			});
			
			placeWalls.setOnMouseClicked(e->{
				if(placeWalls.isSelected())
				{
					placeInit.setSelected(false);
					placeGoal.setSelected(false);
					GraphNode.AddWalls();
				}
				else
				{
					GraphNode.DisableWalls();
				}
			});
			
			
			createMaze.setOnMouseClicked(e->{
				b.DeleteMaze(root);
				b.CreateMaze(root);
			});
			
			b.Draw(root);
			b.AddNeighbours();
			root.getChildren().addAll(panel);
			
			primaryStage.getIcons().add(new Image("file:Img/icon.ico"));
			primaryStage.setTitle("Pathfinder Visualizer");
			primaryStage.setResizable(false);
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
