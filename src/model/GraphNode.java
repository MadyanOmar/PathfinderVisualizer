package model;

import util.Vector;
import util.NodeState;

public class GraphNode {
	Vector position;
	Vector size;
	Vector dimension;
	NodeState state;
	
	public GraphNode(Vector position, Vector size, Vector dimension) {
		this.position = position;
		this.size = size;
		this.dimension = dimension;
		this.state = NodeState.UNVISITED;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public Vector getSize() {
		return size;
	}

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState newState) {
		this.state = newState;
	}
	
	public void setPosition(Vector position) {
		this.position = position;
	}
	
	public void setDimension(Vector dimension) {
		this.dimension = dimension;
	}
	
	public Vector getDimension() {
		// TODO Auto-generated method stub
		return dimension;
	}
}
