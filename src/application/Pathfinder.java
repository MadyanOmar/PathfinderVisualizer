package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import javafx.scene.layout.Pane;

class Pathfinder {
	
	public void DFS(GraphNode[][] board, Pane root)
	{
		Stack<GraphNode> stack = new Stack<GraphNode>();
		GraphNode startNode = board[Board.initial.GetX()][Board.initial.GetY()];
		stack.push(startNode);
		
		while(!stack.isEmpty())
		{
			GraphNode currentNode = stack.pop();
			if(currentNode.getClass() == Board.goal.getClass())
			{
				// Reconstruct and set the shortest path
				ArrayList<GraphNode> path = GraphNode.ReconstructPath(currentNode);
				GraphNode.SetShortestPath(path);
				GraphNode.PlayAnimation();
				return;
			}
			if(!currentNode.IsWall() && !currentNode.IsVisited())
			{
				currentNode.SetVisited();
				currentNode.Draw(root);
				
				for(int i=0; i<currentNode.GetNeighbours().size(); i++)
				{
					GraphNode neighbor = currentNode.GetNeighbours().get(i);
					if(!(neighbor.IsVisited()) && !(neighbor.IsWall()) 
						&& (neighbor.getClass() != Board.initial.getClass()))
					{
						// Set parent for path reconstruction
						if(neighbor.GetParent() == null)
						{
							neighbor.SetParent(currentNode);
						}
						stack.push(neighbor);
					}
				}
			}
		}
	}
	
	public void BFS(GraphNode[][] board, Pane root)
	{
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		GraphNode startNode = board[Board.initial.GetX()][Board.initial.GetY()];
		queue.add(startNode);
		
		while(!queue.isEmpty())
		{
			GraphNode currentNode = queue.remove();
			if(currentNode.getClass() == Board.goal.getClass())
			{
				// Reconstruct and set the shortest path
				ArrayList<GraphNode> path = GraphNode.ReconstructPath(currentNode);
				GraphNode.SetShortestPath(path);
				GraphNode.PlayAnimation();
				return;
			}
			if(!currentNode.IsWall() && !currentNode.IsVisited())
			{
				currentNode.SetVisited();
				currentNode.Draw(root);
				
				for(int i=0; i<currentNode.GetNeighbours().size(); i++)
				{
					GraphNode neighbor = currentNode.GetNeighbours().get(i);
					if(!(neighbor.IsVisited()) && !(neighbor.IsWall()) 
						&& (neighbor.getClass() != Board.initial.getClass()))
					{
						// Set parent for path reconstruction (only if not already set)
						if(neighbor.GetParent() == null)
						{
							neighbor.SetParent(currentNode);
							queue.add(neighbor);
						}
					}
				}
			}
		}
	}
	
	public void GBFS(GraphNode[][] board, Pane root)
	{
		GraphNode.SetAlgo(Algorithm.GBFS);
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
		GraphNode startNode = board[Board.initial.GetX()][Board.initial.GetY()];
		queue.add(startNode);
		
		while(!queue.isEmpty())
		{
			GraphNode currentNode = queue.remove();
			if(currentNode.getClass() == Board.goal.getClass())
			{
				// Reconstruct and set the shortest path
				ArrayList<GraphNode> path = GraphNode.ReconstructPath(currentNode);
				GraphNode.SetShortestPath(path);
				GraphNode.PlayAnimation();
				return;
			}
			if(!currentNode.IsWall() && !currentNode.IsVisited())
			{
				currentNode.SetVisited();
				currentNode.Draw(root);
				
				for(int i=0; i<currentNode.GetNeighbours().size(); i++)
				{
					GraphNode neighbor = currentNode.GetNeighbours().get(i);
					if(!(neighbor.IsVisited()) && !(neighbor.IsWall()) 
						&& (neighbor.getClass() != Board.initial.getClass()))
					{
						// Set parent for path reconstruction
						if(neighbor.GetParent() == null)
						{
							neighbor.SetParent(currentNode);
						}
						queue.add(neighbor);
					}
				}
			}
		}
	}
	
	public void DijkstraSearch(GraphNode[][] board, Pane root)
	{
		GraphNode.SetAlgo(Algorithm.DIJKSTRA);
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
		GraphNode startNode = board[Board.initial.GetX()][Board.initial.GetY()];
		startNode.SetStep(0);
		queue.add(startNode);
		
		while(!queue.isEmpty())
		{
			GraphNode currentNode = queue.remove();
			
			if(currentNode.getClass() == Board.goal.getClass())
			{
				// Reconstruct and set the shortest path
				ArrayList<GraphNode> path = GraphNode.ReconstructPath(currentNode);
				GraphNode.SetShortestPath(path);
				GraphNode.PlayAnimation();
				return;
			}
			
			if(!currentNode.IsWall() && !currentNode.IsVisited())
			{
				currentNode.SetVisited();
				currentNode.Draw(root);
				
				for(int i=0; i<currentNode.GetNeighbours().size(); i++)
				{
					GraphNode neighbor = currentNode.GetNeighbours().get(i);
					if(!(neighbor.IsVisited()) && !(neighbor.IsWall()) 
						&& (neighbor.getClass() != Board.initial.getClass()))
					{	
						int newDistance = currentNode.GetStep() + 1;
						
						// Update if we found a shorter path
						if(neighbor.GetStep() == 0 || newDistance < neighbor.GetStep())
						{
							neighbor.SetStep(newDistance);
							neighbor.SetParent(currentNode);
							queue.add(neighbor);
						}
					}
				}
			}
		}
	}
	
	public void AStarSearch(GraphNode[][] board, Pane root)
	{
		GraphNode.SetAlgo(Algorithm.ASTAR);
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
		GraphNode startNode = board[Board.initial.GetX()][Board.initial.GetY()];
		startNode.SetStep(0);
		queue.add(startNode);
		
		while(!queue.isEmpty())
		{
			GraphNode currentNode = queue.remove();
			
			if(currentNode.getClass() == Board.goal.getClass())
			{
				// Reconstruct and set the shortest path
				ArrayList<GraphNode> path = GraphNode.ReconstructPath(currentNode);
				GraphNode.SetShortestPath(path);
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
					GraphNode neighbor = currentNode.GetNeighbours().get(i);
					if(!(neighbor.IsVisited()) && !(neighbor.IsWall()) 
						&& (neighbor.getClass() != Board.initial.getClass()))
					{	
						int newDistance = currentNode.GetStep() + 1;
						
						// Update if we found a shorter path
						if(neighbor.GetStep() == 0 || newDistance < neighbor.GetStep())
						{
							neighbor.SetStep(newDistance);
							neighbor.SetParent(currentNode);
							queue.add(neighbor);
						}
					}
				}
			}
		}
	}
}