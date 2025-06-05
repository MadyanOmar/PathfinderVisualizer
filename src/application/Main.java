package application;


import java.util.Random;

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
	static int numberNodesX = 60;
	static int numberNodesY = 36;
	private Pane root = new Pane();
	private Board b = new Board(numberNodesX, numberNodesY);
	private Pathfinder p = new Pathfinder();
	private Node[] panel = Panel.GetPanel(p, b, root).GetObjects();	
	private ToggleButton placeGoal = (ToggleButton) panel[panel.length-4];
	private ToggleButton placeInit = (ToggleButton) panel[panel.length-3];
	private Button createMaze = (Button) panel[panel.length-2];
	@Override
	public void start(Stage primaryStage) {
		Random random = new Random();
		int goalPosX = 0;
		int goalPosY = 0;
		int initialPosX = 0;
		int initialPosY = 0;
		GraphNode.WIDTH = WIDTH/numberNodesX; 
		GraphNode.HEIGHT = (HEIGHT-Panel.HEIGHT)/numberNodesY;
		while(goalPosX == initialPosX && goalPosY == initialPosY) {
			goalPosX = random.nextInt(numberNodesX);
			goalPosY = random.nextInt(numberNodesY);
			initialPosX = random.nextInt(numberNodesX);
			initialPosY = random.nextInt(numberNodesY);
		}
		try {
			b.Draw(root);
			b.SetGoal(goalPosX, goalPosY, root);
			b.SetInitial(initialPosX, initialPosY, root);
			primaryStage.getIcons().add(new Image("Img/icon.jpg"));
			Scene scene = new Scene(root,WIDTH,HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
						
			ToggleButton placeWalls = (ToggleButton) panel[panel.length-5];
			
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
