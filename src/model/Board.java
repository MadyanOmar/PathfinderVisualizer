package model;

import java.util.Random;

import util.NodeState;
import util.PathfindingUtils;
import util.Vector;

public class Board {
	private Vector position;
	private Vector size;
	private Vector dimensions;
	private Vector initialDimension;
	private Vector goalDimension;
	GraphNode[][] nodes;
	
	public Board(Vector position, Vector size, Vector dimensions) {
		this.dimensions = dimensions;
		this.position = position;
		this.size = size;
		nodes = new GraphNode[(int) dimensions.getX()][(int) dimensions.getY()];
		Random random = new Random();
		goalDimension = new Vector(random.nextInt((int) dimensions.getX()), random.nextInt((int) dimensions.getY()));
		initialDimension = new Vector(random.nextInt((int) dimensions.getX()), random.nextInt((int) dimensions.getY()));
	}
	
	
	public void generateBoard() {
		Vector basePosition = position;
		int columns = (int) dimensions.getX();
        int rows = (int) dimensions.getY();
        float width = (float) (size.getX() / columns);
        float height = (float) (size.getY() / rows);

	    Random random = new Random();

	    Vector initialDimension, goalDimension;

	    // 1. Ensure start != goal
	    do {
	        initialDimension = new Vector(random.nextInt(columns), random.nextInt(rows));
	        goalDimension = new Vector(random.nextInt(columns), random.nextInt(rows));
	    } while (initialDimension.equals(goalDimension));

	    // 2. Generate nodes
	    for (int r = 0; r < columns; r++) {
	        for (int c = 0; c < rows; c++) {
	            Vector pos = new Vector(basePosition.getX() + (r * width), basePosition.getY() + (c * height));
	            Vector sz = new Vector(width, height);
	            nodes[r][c] = new GraphNode(pos, sz, new Vector(r,c));
	        }
	    }

	    // 3. Mark start and goal
	    nodes[(int) initialDimension.getX()][(int) initialDimension.getY()].setState(NodeState.INITIAL);
	    nodes[(int) goalDimension.getX()][(int) goalDimension.getY()].setState(NodeState.GOAL);
	    this.initialDimension = new Vector((int) initialDimension.getX(), (int) initialDimension.getY());
	    this.goalDimension = new Vector((int) goalDimension.getX(), (int) goalDimension.getY());

	    // 4. Try placing random walls until a valid path exists
	    boolean pathFound = false;
	    int maxAttempts = 100;
	    int attempt = 0;

	    while (!pathFound && attempt < maxAttempts) {
	        attempt++;

	        // 4a. Clear all walls
	        for (int r = 0; r < columns; r++) {
	            for (int c = 0; c < rows; c++) {
	                if (nodes[r][c].getState() == NodeState.WALL) {
	                    nodes[r][c].setState(NodeState.UNVISITED);
	                }
	            }
	        }

	        // 4b. Randomly assign some walls
	        double wallProbability = 0.4; // Adjust for difficulty
	        for (int r = 0; r < columns; r++) {
	            for (int c = 0; c < rows; c++) {
	                if ((r == initialDimension.getX() && c == initialDimension.getY()) ||
	                    (r == goalDimension.getX() && c == goalDimension.getY())) {
	                    continue; // Don't overwrite start/goal
	                }

	                if (random.nextDouble() < wallProbability) {
	                    nodes[r][c].setState(NodeState.WALL);
	                } else {
	                    nodes[r][c].setState(NodeState.UNVISITED);
	                }
	            }
	        }

	        // 4c. Check if a path exists (e.g., using BFS)
	        pathFound = PathfindingUtils.isPathAvailable(nodes, initialDimension, goalDimension);
	    }

	    if (!pathFound) {
	        System.out.println("Failed to find a valid board with a path after " + maxAttempts + " attempts.");
	    }

	   
	}
	
	public void setPosition(Vector position) {
		this.position = position;
	}

	public void setSize(Vector size) {
		this.size = size;
	}

	public void setDimensions(Vector dimensions) {
		this.dimensions = dimensions;
		nodes = new GraphNode[(int) dimensions.getX()][(int) dimensions.getY()];
	}

	public Vector getDimensions() {
		return dimensions;
	}

	public Vector getSize() {
		return size;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public Vector getInitialDimension() {
		return initialDimension;
	}
	
	public Vector getGoalDimension() {
		return goalDimension;
	}
	
	public GraphNode[][] getBoard() {
		return nodes;
	}
	
	public GraphNode getNode(int x, int y) {
		return nodes[x][y];
	}

	public void updateNode(Vector dimension, GraphNode node) {
		if(node.getState() == NodeState.INITIAL) {
			this.initialDimension = node.getDimension();
		} else if(node.getState() == NodeState.GOAL) {
			this.goalDimension = node.getDimension();
		}
		nodes[(int) dimension.getX()][(int) dimension.getY()] = node;
	}
}
