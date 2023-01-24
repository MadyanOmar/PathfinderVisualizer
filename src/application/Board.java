package application;

import javafx.scene.layout.Pane;

class Board {
	public static Goal goal;
	public static Initial initial;
	GraphNode[][] nodes;
	
	public Board(int boardWidth, int boardHeight)
	{
		nodes = new GraphNode[boardWidth][boardHeight];
	}
	
	public void SetGoal(int x, int y, Pane root)
	{
		int i = Integer.MIN_VALUE;
		int j = Integer.MIN_VALUE;
		if(goal!=null)
		{
			//Get old goal node position
			i = goal.GetX();
			j = goal.GetY();
			
			//Turn old goal node into regular node
			nodes[i][j] = new GraphNode(i,j);
		}
		goal = new Goal(x, y);
		nodes[x][y] = goal;
		AddNeighbours(nodes[x][y]);
		
		//Add neighbors for new goal node and new goal node's neighbors
		for(int k=0; k<nodes[x][y].GetNeighbours().size(); k++)
		{
			nodes[x][y].GetNeighbours().get(k).DeleteNeighbours();
			AddNeighbours(nodes[x][y].GetNeighbours().get(k));
		}
		
		if(i!=Integer.MIN_VALUE)
		{
			//Add neighbors for old goal node and old goal node's neighbors
			AddNeighbours(nodes[i][j]);
			for(int k=0; k<nodes[i][j].GetNeighbours().size(); k++)
			{
				nodes[i][j].GetNeighbours().get(k).DeleteNeighbours();
				AddNeighbours(nodes[i][j].GetNeighbours().get(k));
			}
		}
	}
	
	public void DeleteMaze(Pane root)
	{
		for(int i=0; i<nodes.length; i++)
		{
			for(int j=0; j<nodes[0].length; j++)
			{
				nodes[i][j].DeleteNeighbours();
				if(nodes[i][j].IsWall())
				{
					nodes[i][j].RemoveWall();
					nodes[i][j].Draw(root);
				}
			}
		}
		AddNeighbours();
	}
	
	public void CreateMaze(Pane root)
	{
		for(int i=0; i<nodes.length; i++)
		{
			for(int j=0; j<nodes[0].length; j++)
			{
				int randnum = (int) (Math.random() * 100);
				if(randnum<=40)
				{
					if(nodes[i][j] == null)
					{
						nodes[i][j] = new GraphNode(i, j);
					}
					if(nodes[i][j].getClass()!=Initial.class && nodes[i][j].getClass()!=Goal.class)
					{
						nodes[i][j].MakeWall();
						nodes[i][j].Draw(root);
					}
				}
			}
		}
	}
	
	public void SetInitial(int x, int y, Pane root)
	{
		int i = Integer.MIN_VALUE;
		int j = Integer.MIN_VALUE;
		if(initial!=null)
		{
			//Get old initial node position
			i = initial.GetX();
			j = initial.GetY();
			
			//Turn old initial node into regular node
			nodes[i][j] = new GraphNode(i,j);
		}
		initial = new Initial(x, y);
		nodes[x][y] = initial;
		AddNeighbours(nodes[x][y]);
		
		//Add neighbors for new initial node and new initial's neighbors
		for(int k=0; k<nodes[x][y].GetNeighbours().size(); k++)
		{
			nodes[x][y].GetNeighbours().get(k).DeleteNeighbours();
			AddNeighbours(nodes[x][y].GetNeighbours().get(k));
		}
		
		if(i!=Integer.MIN_VALUE)
		{
			//Add neighbors for old goal node and old goal node's neighbors
			AddNeighbours(nodes[i][j]);
			for(int k=0; k<nodes[i][j].GetNeighbours().size(); k++)
			{
				nodes[i][j].GetNeighbours().get(k).DeleteNeighbours();
				AddNeighbours(nodes[i][j].GetNeighbours().get(k));
			}
		}
	}
	
	public GraphNode[][] getBoard()
	{
		return nodes;
	}
	
	public void Reset()
	{
		for(int i=0; i<nodes.length; i++)
		{
			for(int j=0; j<nodes[0].length; j++)
			{
				nodes[i][j].Reset();
			}
		}
	}
	
	private void AddNeighbours(GraphNode node)
	{
		node.DeleteNeighbours();
		int i = node.GetX();
		int j = node.GetY();
		
		if(j<nodes[0].length-1)
		{
			node.AddNeigbour(nodes[i][j+1]);
		}
		if(j>0)
		{
			node.AddNeigbour(nodes[i][j-1]);
		}
		if(i>0)
		{
			node.AddNeigbour(nodes[i-1][j]);
		}
		if(i < nodes.length-1)
		{
			node.AddNeigbour(nodes[i+1][j]);
		}
	}
	
	public void AddNeighbours()
	{
		for(int i=0; i<nodes.length; i++)
		{
			for(int j=0; j<nodes[0].length; j++)
			{				
				if(j<nodes[0].length-1)
				{
					nodes[i][j].AddNeigbour(nodes[i][j+1]);
				}
				if(j>0)
				{
					nodes[i][j].AddNeigbour(nodes[i][j-1]);
				}
				if(i>0)
				{
					nodes[i][j].AddNeigbour(nodes[i-1][j]);
				}
				if(i < nodes.length-1)
				{
					nodes[i][j].AddNeigbour(nodes[i+1][j]);
				}
			}
		}
	}
	
	public void Draw(Pane root)
	{
		for(int i=0; i<nodes.length; i++)
		{
			for(int j=0; j<nodes[0].length; j++)
			{
				if(nodes[i][j] == null)
				{
					nodes[i][j] = new GraphNode(i, j);
				}
				nodes[i][j].Draw(root);
			}
		}
	}
}
