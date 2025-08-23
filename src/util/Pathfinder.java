package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import model.GraphNode;

public class Pathfinder {
	public static PathfindingResult bfs(GraphNode[][] nodes, Vector start, Vector goal) {
        int cols = nodes.length;
        int rows = nodes[0].length;

        boolean[][] visited = new boolean[cols][rows];
        Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
        List<GraphNode> visitedOrder = new ArrayList<>();

        Queue<GraphNode> queue = new LinkedList<>();
        GraphNode startNode = nodes[(int) start.getX()][(int) start.getY()];
        GraphNode goalNode = nodes[(int) goal.getX()][(int) goal.getY()];
        queue.add(startNode);

        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();
            int x = (int) current.getDimension().getX();
            int y = (int) current.getDimension().getY();

            if (visited[x][y]) continue;
            visited[x][y] = true;
            visitedOrder.add(current);

            if (current == goalNode) {
                break;
            }

            for (Vector dir : new Vector[]{
                new Vector(-1, 0), new Vector(1, 0),
                new Vector(0, -1), new Vector(0, 1)
            }) {
                int nx = x + (int) dir.getX();
                int ny = y + (int) dir.getY();

                if (nx >= 0 && ny >= 0 && nx < cols && ny < rows) {
                    GraphNode neighbor = nodes[nx][ny];
                    if (neighbor.getState() != NodeState.WALL && !visited[nx][ny]) {
                        queue.add(neighbor);
                        if (!cameFrom.containsKey(neighbor)) {
                            cameFrom.put(neighbor, current);
                        }
                    }
                }
            }
        }

        // Backtrack path
        List<GraphNode> path = new ArrayList<>();
        GraphNode current = goalNode;
        while (cameFrom.containsKey(current) && current != startNode) {
            path.add(current);
            current = cameFrom.get(current);
        }

        Collections.reverse(path);
        return new PathfindingResult(visitedOrder, path);
    }
	
	
	
	public static PathfindingResult astar(GraphNode[][] nodes, Vector start, Vector goal) {
	    GraphNode startNode = nodes[(int) start.getX()][(int) start.getY()];
	    GraphNode goalNode = nodes[(int) goal.getX()][(int) goal.getY()];

	    Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
	    Map<GraphNode, Double> gScore = new HashMap<>();
	    List<GraphNode> visitedOrder = new ArrayList<>();

	    PriorityQueue<GraphNode> openSet = new PriorityQueue<>(Comparator.comparingDouble(n ->
	            gScore.getOrDefault(n, Double.MAX_VALUE) + heuristic(n, goalNode)));

	    gScore.put(startNode, 0.0);
	    openSet.add(startNode);

	    while (!openSet.isEmpty()) {
	        GraphNode current = openSet.poll();
	        visitedOrder.add(current);

	        if (current == goalNode) break;

	        for (GraphNode neighbor : getNeighbors(current, nodes)) {
	            if (neighbor.getState() == NodeState.WALL) continue;

	            double tentativeG = gScore.getOrDefault(current, Double.MAX_VALUE) + 1;
	            if (tentativeG < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
	                cameFrom.put(neighbor, current);
	                gScore.put(neighbor, tentativeG);
	                openSet.add(neighbor);
	            }
	        }
	    }

	    return constructResult(cameFrom, startNode, goalNode, visitedOrder);
	}
	
	public static PathfindingResult greedy(GraphNode[][] nodes, Vector start, Vector goal) {
	    GraphNode startNode = nodes[(int) start.getX()][(int) start.getY()];
	    GraphNode goalNode = nodes[(int) goal.getX()][(int) goal.getY()];

	    Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
	    Set<GraphNode> visited = new HashSet<>();
	    List<GraphNode> visitedOrder = new ArrayList<>();

	    PriorityQueue<GraphNode> openSet = new PriorityQueue<>(Comparator.comparingDouble(n ->
	            heuristic(n, goalNode)));

	    openSet.add(startNode);

	    while (!openSet.isEmpty()) {
	        GraphNode current = openSet.poll();
	        visitedOrder.add(current);
	        visited.add(current);

	        if (current == goalNode) break;

	        for (GraphNode neighbor : getNeighbors(current, nodes)) {
	            if (neighbor.getState() == NodeState.WALL || visited.contains(neighbor)) continue;

	            cameFrom.putIfAbsent(neighbor, current);
	            openSet.add(neighbor);
	        }
	    }

	    return constructResult(cameFrom, startNode, goalNode, visitedOrder);
	}
	
	public static PathfindingResult dfs(GraphNode[][] nodes, Vector start, Vector goal) {
	    GraphNode startNode = nodes[(int) start.getX()][(int) start.getY()];
	    GraphNode goalNode = nodes[(int) goal.getX()][(int) goal.getY()];

	    Stack<GraphNode> stack = new Stack<>();
	    Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
	    Set<GraphNode> visited = new HashSet<>();
	    List<GraphNode> visitedOrder = new ArrayList<>();

	    stack.push(startNode);

	    while (!stack.isEmpty()) {
	        GraphNode current = stack.pop();

	        if (!visited.add(current)) continue;
	        visitedOrder.add(current);

	        if (current == goalNode) break;

	        for (GraphNode neighbor : getNeighbors(current, nodes)) {
	            if (neighbor.getState() != NodeState.WALL && !visited.contains(neighbor)) {
	                cameFrom.putIfAbsent(neighbor, current);
	                stack.push(neighbor);
	            }
	        }
	    }

	    return constructResult(cameFrom, startNode, goalNode, visitedOrder);
	}
	
	private static PathfindingResult constructResult(Map<GraphNode, GraphNode> cameFrom,
            GraphNode start, GraphNode goal,
            List<GraphNode> visited) {
			List<GraphNode> path = new ArrayList<>();
			GraphNode current = goal;
			
			while (cameFrom.containsKey(current) && current != start) {
			path.add(current);
			current = cameFrom.get(current);
			}
			
			Collections.reverse(path);
			return new PathfindingResult(visited, path);
	}
	
	private static List<GraphNode> getNeighbors(GraphNode node, GraphNode[][] grid) {
	    List<GraphNode> neighbors = new ArrayList<>();
	    int x = (int) node.getDimension().getX();
	    int y = (int) node.getDimension().getY();
	    int cols = grid.length;
	    int rows = grid[0].length;

	    for (Vector dir : new Vector[]{
	        new Vector(-1, 0), new Vector(1, 0),
	        new Vector(0, -1), new Vector(0, 1)
	    }) {
	        int nx = x + (int) dir.getX();
	        int ny = y + (int) dir.getY();
	        if (nx >= 0 && ny >= 0 && nx < cols && ny < rows) {
	            neighbors.add(grid[nx][ny]);
	        }
	    }
	    return neighbors;
	}
	
	private static double heuristic(GraphNode a, GraphNode b) {
	    // Manhattan distance
	    return Math.abs(a.getDimension().getX() - b.getDimension().getX()) +
	           Math.abs(a.getDimension().getY() - b.getDimension().getY());
	}
}
