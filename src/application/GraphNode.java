package application;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

enum Algorithm
{
	ASTAR, GBFS, DIJKSTRA;
}

class GraphNode implements Comparable<GraphNode> {
	static int WIDTH = 40;
	static int HEIGHT = 40;
	private int steps;
	static private SequentialTransition animation;
	static private SequentialTransition pathAnimation;
	static private ArrayList<GraphNode> shortestPath;
	private static Algorithm algo = Algorithm.ASTAR;
	private static boolean addWalls = false;
	ArrayList<GraphNode> neighbours;
	private boolean isVisited = false;
	private boolean isWall = false;
	private boolean isInPath = false;
	private int posX;
	private int posY;
	private GraphNode parent; // For path reconstruction
	
	public GraphNode(int posX, int posY)
	{
		
		this.posX = posX * WIDTH;
		this.posY = posY * HEIGHT + Panel.HEIGHT;
		this.steps = 0;
		this.parent = null;
		neighbours = new ArrayList<GraphNode>();
	}
	
	protected void changePos(int posX, int posY)
	{
		this.posX = posX/WIDTH;
		this.posY = posY/HEIGHT;
	}
	
	public static void AddWalls()
	{
		addWalls = true;
	}
	
	public static void DisableWalls()
	{
		addWalls = false;
	}
	
	public int GetX()
	{
		return posX/WIDTH;
	}
	
	public int GetY()
	{
		return (posY - Panel.HEIGHT)/HEIGHT;
	}
	
	public static void SetAlgo(Algorithm a)
	{
		algo = a;
	}
	
	public void SetStep(int steps)
	{
		this.steps = steps;
	}
	
	public int GetStep()
	{
		return steps;
	}
	
	public void SetParent(GraphNode parent)
	{
		this.parent = parent;
	}
	
	public GraphNode GetParent()
	{
		return parent;
	}
	
	public void SetInPath(boolean inPath)
	{
		this.isInPath = inPath;
	}
	
	public boolean IsInPath()
	{
		return isInPath;
	}
	
	public void Reset()
	{
		steps = 0;
		animation = null;
		pathAnimation = null;
		shortestPath = null;
		neighbours = null;
		isVisited = false;
		isInPath = false;
		parent = null;
	}
	
	public void DeleteNeighbours()
	{
		neighbours = null;
	}
	
	public boolean IsVisited()
	{
		return isVisited;
	}
	
	private int GetManhattanDistance(GraphNode goal)
	{
		return Math.abs(goal.posX-this.posX) +  Math.abs(goal.posY-this.posY);
	}
	
	private int GetPathCost(GraphNode goal)
	{
		return steps + GetManhattanDistance(goal);
	}
	
	public void AddNeigbour(GraphNode neighbour)
	{
		if(neighbours == null)
		{
			neighbours = new ArrayList<GraphNode>();
		}
		neighbours.add(neighbour);
	}
	
	public ArrayList<GraphNode> GetNeighbours()
	{
		return neighbours;
	}
	
	public Rectangle GetNode()
	{
		return new Rectangle(posX, posY, WIDTH, HEIGHT);
	}
	
	public static void PlayAnimation()
	{
		if(animation != null)
		{
			animation.play();
		}
	}
	
	public static void PlayPathAnimation()
	{
		if(pathAnimation != null)
		{
			pathAnimation.play();
		}
	}
	
	public static void SetShortestPath(ArrayList<GraphNode> path)
	{
		shortestPath = path;
	}
	
	public static ArrayList<GraphNode> GetShortestPath()
	{
		return shortestPath;
	}
	
	// Reconstruct path from goal to initial using parent pointers
	public static ArrayList<GraphNode> ReconstructPath(GraphNode goalNode)
	{
		ArrayList<GraphNode> path = new ArrayList<GraphNode>();
		GraphNode current = goalNode;
		
		while(current != null)
		{
			path.add(0, current); // Add to front to reverse the path
			current = current.GetParent();
		}
		
		return path;
	}
	
	// Create path animation
	public static void CreatePathAnimation(Pane root)
	{
		if(shortestPath == null || shortestPath.isEmpty())
		{
			Panel.GetPanel().EnablePanel();
			return;
		}
		
		animation = null;
		
		// Mark nodes as part of path and create animations
		for(int i = 1; i < shortestPath.size() - 1; i++) // Skip initial and goal
		{
			GraphNode node = shortestPath.get(i);
			node.SetInPath(true);
			node.Draw(root);			
		}
		
		animation.setOnFinished(e -> {
			Panel.GetPanel().EnablePanel();
		});
		
	}
	
	public boolean IsWall()
	{
		return isWall;
	}
	
	public void SetVisited()
	{
		this.isVisited = true;
	}
	
	public void MakeWall()
	{
		isWall = true;
	}
	
	public void RemoveWall()
	{
		isWall = false;
	}
	
	public void Draw(Pane root)
	{
		Rectangle rect = GetNode();
		rect.setOnMouseClicked(e->{
			if(isWall && addWalls)
			{
				rect.setFill(Color.WHITE);
				rect.setStroke(Color.BLACK);
				rect.setStrokeWidth(2);
				isWall = false;
			}
			else if(addWalls)
			{
				rect.setFill(Color.BLACK);
				isWall = true;
			}
		});
		
		if(isInPath)
		{
			rect.setFill(Color.MEDIUMVIOLETRED);
			rect.setStroke(Color.BLACK);
			rect.setStrokeWidth(2);
			FadeTransition a = new FadeTransition(Duration.seconds(0.05), rect);
			FillTransition anim = new FillTransition(Duration.seconds(0.3), rect);
			anim.setFromValue(Color.MEDIUMVIOLETRED);
			anim.setToValue(Color.GOLD);
			anim.setCycleCount(1);
			a.setOnFinished(e->{
				anim.play();
			});
			if(animation == null)
			{
				animation = new SequentialTransition();
			}
			animation.getChildren().add(a);
		}
		else if(isVisited)
		{
			rect.setFill(Color.WHITE);
			rect.setStroke(Color.BLACK);
			rect.setStrokeWidth(2);
			FadeTransition a = new FadeTransition(Duration.seconds(0.05), rect);
			FillTransition anim = new FillTransition(Duration.seconds(0.3), rect);
			anim.setFromValue(Color.PINK);
			anim.setToValue(Color.MEDIUMVIOLETRED);
			anim.setCycleCount(1);
			a.setOnFinished(e->{
				anim.play();
			});
			
			if(animation == null)
			{
				animation = new SequentialTransition();
				animation.setOnFinished(e->{
					// After search animation, create and play path animation
					CreatePathAnimation(root);
					animation.play();
				});
			}
			animation.getChildren().add(a);
		}
		else if(isWall)
		{
			rect.setFill(Color.BLACK);
		}
		else
		{
			rect.setFill(Color.WHITE);
			rect.setStroke(Color.BLACK);
			rect.setStrokeWidth(2);
		}
		
		root.getChildren().add(rect);		
	}

	@Override
	public int compareTo(GraphNode other) {
		if(algo == Algorithm.GBFS)
		{
			if(this.GetManhattanDistance(Board.goal) > other.GetManhattanDistance(Board.goal))
			{
				return 1;
			}
			else if(this.GetManhattanDistance(Board.goal) < other.GetManhattanDistance(Board.goal))
			{
				return -1;
			}
			return 0;
		}
		else if(algo == Algorithm.DIJKSTRA)
		{
			if(this.GetStep() > other.GetStep())
			{
				return 1;
			}
			else if(this.GetStep() < other.GetStep())
			{
				return -1;
			}
			return 0;
		}
		else
		{
			if(this.GetPathCost(Board.goal) > other.GetPathCost(Board.goal))
			{
				return 1;
			}
			else if(this.GetPathCost(Board.goal) < other.GetPathCost(Board.goal))
			{
				return -1;
			}
			return 0;
		}
	}
}

class Goal extends GraphNode
{
	public Goal(int posx, int posy) {
		super(posx, posy);
	}
	
	public void Draw(Pane root)
	{
		Rectangle rect = GetNode();
		rect.setFill(Color.RED);
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(2);
		FadeTransition anim = new FadeTransition(Duration.seconds(0.8), rect);
		anim.setFromValue(1);
		anim.setToValue(0);
		anim.setCycleCount(Animation.INDEFINITE);
		anim.play();
		root.getChildren().add(rect);
	}
}

class Initial extends GraphNode
{
	public Initial(int posx, int posy) {
		super(posx, posy);
	}
	
	public void Draw(Pane root)
	{
		Rectangle rect = GetNode();
		rect.setFill(Color.GREENYELLOW);
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(2);
		root.getChildren().add(rect);
	}
}