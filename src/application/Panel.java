package application;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.geometry.Insets;
import javafx.scene.Node;

public class Panel {
	private static Panel panel;
	public static int HEIGHT = 130;
	private Node[] objects;
	public Panel(Pathfinder p, Board b, Pane root)
	{
		objects = new Node[9];
		Rectangle rect = new Rectangle(0,0, Main.WIDTH, HEIGHT);
		Button button = new Button("Start");
		Button reset = new Button("Reset");
		ToggleButton placeWalls = new ToggleButton("Add/Remove Walls");
		ToggleButton placeGoal = new ToggleButton( "      Set Goal      ");
		ToggleButton placeInit = new ToggleButton( "Set Initial Node");
		Button createMaze = new Button("   Generate Walls   ");
		ComboBox<String> input = new ComboBox<String>();
		input.getItems().addAll("Depth First Search",
							 "Breadth First Search",
							 "Greedy Best First Search",
							 "A* Search");
		ComboBox<String> size = new ComboBox<String>();
		size.getItems().addAll("15x9",
							 "25x15",
							 "30x18",
							 "60x36");
		
		size.setLayoutX(40);
		size.setLayoutY(60);
		size.getSelectionModel().select(0);
		
		input.setLayoutX(40);
		input.setLayoutY(30);
		input.getSelectionModel().select(0);
		
		rect.setFill(Color.BISQUE);
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(2);

		placeWalls.setLayoutX(Main.WIDTH/2 + 40);
		placeWalls.setLayoutY(10);
		
		createMaze.setLayoutX(Main.WIDTH/2 + 40);
		createMaze.setLayoutY(70);
		
		placeGoal.setPadding(new Insets(0,45,0,45));
		placeGoal.setLayoutX(Main.WIDTH/2 - 300);
		placeGoal.setLayoutY(70);
		
		placeInit.setLayoutX(Main.WIDTH/2 - 300);
		placeInit.setLayoutY(10);
		
		button.setMinWidth(100);
		button.setMinHeight(40);
		button.setFont(new Font(20));
		button.setLayoutX(Main.WIDTH/2 - 100);
		button.setLayoutY(50);
		button.setOnMouseClicked(e->{
			String choice1 = input.getValue().toString();
			String choice2 = size.getValue().toString();
			if(choice2.equals("15x9")) {
				Main.numberNodesX = 15;
				Main.numberNodesY = 9;
			}
			if(choice2.equals("25x15")) {
				Main.numberNodesX = 25;
				Main.numberNodesY = 15;
			}
			if(choice2.equals("30x18")) {
				Main.numberNodesX = 30;
				Main.numberNodesY = 18;
			}
			if(choice2.equals("60x36")) {
				Main.numberNodesX = 60;
				Main.numberNodesY = 36;
			}
			if(choice1.equals("Depth First Search"))
			{
				p.DFS(b.getBoard(), root);
			}
			if(choice1.equals("Breadth First Search"))
			{
				p.BFS(b.getBoard(), root);
			}
			if(choice1.equals("Greedy Best First Search"))
			{
				p.GBFS(b.getBoard(), root);
			}
			if(choice1.equals("A* Search"))
			{
				p.AStarSearch(b.getBoard(), root);
			}
			DisablePanel();
		});
		
		input.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
		     @Override public ListCell<String> call(ListView<String> p) {
		         return new ListCell<String>() {
		             private final Text t;
		             { 
		                 setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
		                 t = new Text();
		             }
		             
		             @Override protected void updateItem(String item, boolean empty) {
		                 super.updateItem(item, empty);
		                 
		                 if (item == null || empty) {
		                     setGraphic(null);
		                 } else {
		                     t.setText(item);
		                     setGraphic(t);
		                 }
		            }
		       };
		   }
		});
		
		reset.setTextFill(Color.WHITE);
		reset.setStyle("-fx-background-color: red; -fx-border-radius: 2px;");
		reset.setMinWidth(100);
		reset.setMinHeight(40);
		reset.setFont(new Font(20));
		reset.setLayoutX(Main.WIDTH - 150);
		reset.setLayoutY(50);
		reset.setOnMouseClicked(e->{
			b.Reset();
			b.Draw(root);
			b.AddNeighbours();
		});
		
		objects[0] = rect;
		objects[1] = button;
		objects[2] = input;
		objects[3] = reset;
		objects[4] = placeWalls;
		objects[5] = placeGoal;
		objects[6] = placeInit;
		objects[7] = createMaze;
		objects[8] = size;
	}
	
	public static Panel GetPanel(Pathfinder p, Board b, Pane root)
	{
		if(panel == null)
		{
			panel = new Panel(p, b, root);
		}
		return panel;
	}
	
	public static Panel GetPanel()
	{
		if(panel!=null)
		{
			return panel;
		}
		return null;
	}
	
	public void DisablePanel()
	{
		for(int i=0; i<objects.length; i++)
		{
			objects[i].setDisable(true);
		}
	}
	
	public void EnablePanel()
	{
		for(int i=0; i<objects.length; i++)
		{
			objects[i].setDisable(false);
		}
	}
	
	public Node[] GetObjects()
	{
		return objects;
	}
}
