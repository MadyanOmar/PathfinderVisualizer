package application;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import javafx.scene.layout.Pane;

class Pathfinder {
	
	public void DFS(GraphNode[][] board, Pane root)
	{
		Stack<GraphNode> stack = new Stack<GraphNode>();
		stack.push(board[Board.initial.GetX()][Board.initial.GetY()]);
		while(!stack.isEmpty())
		{
			GraphNode currentNode = stack.pop();
			if(currentNode.getClass() == Board.goal.getClass())
			{
				GraphNode.PlayAnimation();
				return;
			}
			if(!currentNode.IsWall() && !currentNode.IsVisited())
			{
				currentNode.SetVisited();
				currentNode.Draw(root);
				
				for(int i=0; i<currentNode.GetNeighbours().size(); i++)
				{
					if(!(currentNode.GetNeighbours().get(i).IsVisited()) && !(currentNode.GetNeighbours().get(i).IsWall()) 
						&& (currentNode.GetNeighbours().get(i).getClass() != Board.initial.getClass()))
					{
						stack.push(currentNode.GetNeighbours().get(i));
					}
				}
			}
		}
	}
	
	public void BFS(GraphNode[][] board, Pane root)
	{
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		queue.add(board[Board.initial.GetX()][Board.initial.GetY()]);
		while(!queue.isEmpty())
		{
			GraphNode currentNode = queue.remove();
			if(currentNode.getClass() == Board.goal.getClass())
			{
				GraphNode.PlayAnimation();
				return;
			}
			if(!currentNode.IsWall() && !currentNode.IsVisited())
			{
				currentNode.SetVisited();
				currentNode.Draw(root);
				
				for(int i=0; i<currentNode.GetNeighbours().size(); i++)
				{
					if(!(currentNode.GetNeighbours().get(i).IsVisited()) && !(currentNode.GetNeighbours().get(i).IsWall()) 
						&& (currentNode.GetNeighbours().get(i).getClass() != Board.initial.getClass()))
					{
						queue.add(currentNode.GetNeighbours().get(i));
					}
				}
			}
		}
	}
	
	public void GBFS(GraphNode[][] board, Pane root)
	{
		GraphNode.SetAlgo(Algorithm.GBFS);
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
		queue.add(board[Board.initial.GetX()][Board.initial.GetY()]);
		while(!queue.isEmpty())
		{
			GraphNode currentNode = queue.remove();
			if(currentNode.getClass() == Board.goal.getClass())
			{
				GraphNode.PlayAnimation();
				return;
			}
			if(!currentNode.IsWall() && !currentNode.IsVisited())
			{
				currentNode.SetVisited();
				currentNode.Draw(root);
				
				for(int i=0; i<currentNode.GetNeighbours().size(); i++)
				{
					if(!(currentNode.GetNeighbours().get(i).IsVisited()) && !(currentNode.GetNeighbours().get(i).IsWall()) 
						&& (currentNode.GetNeighbours().get(i).getClass() != Board.initial.getClass()))
					{
						queue.add(currentNode.GetNeighbours().get(i));
					}
				}
			}
		}
	}
	
	public void DijkstraSearch(GraphNode[][] board, Pane root)
	{
		GraphNode.SetAlgo(Algorithm.DIJKSTRA);
		int steps = 0;
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
		queue.add(board[Board.initial.GetX()][Board.initial.GetY()]);
		while(!queue.isEmpty())
		{
			GraphNode currentNode = queue.remove();
			if(currentNode.getClass()!=Initial.class && currentNode.GetStep()==0 )
			{
				currentNode.SetStep(steps);
			}
			steps++;
			if(currentNode.getClass() == Board.goal.getClass())
			{
				GraphNode.PlayAnimation();
				return;
			}
			if(!currentNode.IsWall() && !currentNode.IsVisited())
			{
				currentNode.SetVisited();
				currentNode.Draw(root);
				
				for(int i=0; i<currentNode.GetNeighbours().size(); i++)
				{
					if(!(currentNode.GetNeighbours().get(i).IsVisited()) && !(currentNode.GetNeighbours().get(i).IsWall()) 
						&& (currentNode.GetNeighbours().get(i).getClass() != Board.initial.getClass()))
					{	
						if(currentNode.GetNeighbours().get(i).GetStep() == 0)
						{
							currentNode.GetNeighbours().get(i).SetStep(
									currentNode.GetStep() + 1	
									);	
						}
						queue.add(currentNode.GetNeighbours().get(i));
					}
				}
			}
		}
	}
	
	public void AStarSearch(GraphNode[][] board, Pane root)
	{
		//Find a way to calculate steps
		GraphNode.SetAlgo(Algorithm.ASTAR);
		int steps = 0;
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
		queue.add(board[Board.initial.GetX()][Board.initial.GetY()]);
		while(!queue.isEmpty())
		{
			GraphNode currentNode = queue.remove();
			if(currentNode.getClass()!=Initial.class && currentNode.GetStep()==0 )
			{
				currentNode.SetStep(steps);
			}
			steps++;
			if(currentNode.getClass() == Board.goal.getClass())
			{
				GraphNode.PlayAnimation();
				return;
			}
			if(!currentNode.IsWall() && !currentNode.IsVisited())
			{
				if(currentNode.getClass()!=Initial.class)
				{
					currentNode.SetVisited();
					currentNode.Draw(root);
				}
				
				for(int i=0; i<currentNode.GetNeighbours().size(); i++)
				{
					if(!(currentNode.GetNeighbours().get(i).IsVisited()) && !(currentNode.GetNeighbours().get(i).IsWall()) 
						&& (currentNode.GetNeighbours().get(i).getClass() != Board.initial.getClass()))
					{	
						if(currentNode.GetNeighbours().get(i).GetStep() == 0)
						{
							currentNode.GetNeighbours().get(i).SetStep(
									currentNode.GetStep() + 1	
									);	
						}
						queue.add(currentNode.GetNeighbours().get(i));
					}
				}
			}
		}
	}
}
