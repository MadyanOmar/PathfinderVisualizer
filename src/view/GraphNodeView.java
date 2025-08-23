package view;

import javafx.animation.FillTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.GraphNode;
import util.NodeState;
import util.OnDragReleaseHandler;
import util.Vector;

public class GraphNodeView extends StackPane {
    private final GraphNode node;
    private final Rectangle cell;
    private OnDragReleaseHandler onDragRelease;

    private static final Duration ANIM_DURATION = Duration.millis(200);

    public GraphNodeView(GraphNode node) {
        this.node = node;
        
        setLayoutX(node.getPosition().getX());
        setLayoutY(node.getPosition().getY());
        setPrefSize(node.getSize().getX(), node.getSize().getY());

        cell = new Rectangle(node.getSize().getX(), node.getSize().getY());
        cell.setArcWidth(4);
        cell.setArcHeight(4);
        cell.setStroke(Color.BLACK);
        cell.setFill(getColorForState(node.getState()));  // initial color
        cell.setOnMouseClicked(e->{
        	if(node.getState() == NodeState.WALL) {
            	node.setState(NodeState.UNVISITED);
            	cell.setFill(getColorForState(node.getState()));
        	}else if(node.getState() == NodeState.UNVISITED) {
        		node.setState(NodeState.WALL);
        		cell.setFill(getColorForState(node.getState()));
        		
        	}
        });
        
        // Add initial and goal movement
        getChildren().add(cell);
        enableDragEventForwarding();
    }
    
    private static class Delta {
        double x, y;
    }
    
    private void enableDragEventForwarding() {
        final Delta dragDelta = new Delta();

        setOnMousePressed(e -> {
            if (node.getState() == NodeState.INITIAL || node.getState() == NodeState.GOAL) {
                dragDelta.x = e.getX();
                dragDelta.y = e.getY();
            }
        });

        setOnMouseDragged(e -> {
            if (node.getState() == NodeState.INITIAL || node.getState() == NodeState.GOAL) {
                setLayoutX(e.getSceneX() - dragDelta.x);
                double layoutY = localToScene(0, 0).getY(); 
            	double offsetY = layoutY - getLayoutY();  
                setLayoutY(e.getSceneY() - offsetY - dragDelta.y);
            }
        });

        setOnMouseReleased(e -> {
            if (onDragRelease != null && 
               (node.getState() == NodeState.INITIAL || node.getState() == NodeState.GOAL)) {
            	int x = (int) (e.getSceneX() / node.getSize().getX());
            	double layoutY = localToScene(0, 0).getY(); 
            	double offsetY = layoutY - getLayoutY();    
            	int y = (int) ((e.getSceneY() - offsetY) / node.getSize().getY());
            	
            	int old_x = (int) node.getDimension().getX();
            	int old_y = (int) node.getDimension().getY();

            	Vector dimension = new Vector(x,y);
                node.setDimension(dimension);
            	Vector position = new Vector(node.getDimension().getX() * node.getSize().getX(), node.getDimension().getY() * node.getSize().getY());
            	node.setPosition(position);
                onDragRelease.onRelease(old_x, old_y);
            }
        });
    }

    
    public void setOnDragRelease(OnDragReleaseHandler handler) {
        this.onDragRelease = handler;
    }
    
    public void updateColorAnimated() {
        Color toColor = getColorForState(node.getState());

        FillTransition transition = new FillTransition(Duration.millis(200), cell);
        transition.setToValue(toColor);
        transition.play();
    }
    
    public void updateState(NodeState newState, boolean animate) {
        node.setState(newState);
        Color targetColor = getColorForState(newState);
        if (animate) {
            FillTransition ft = new FillTransition(ANIM_DURATION, cell);
            ft.setToValue(targetColor);
            ft.play();
        } else {
            cell.setFill(targetColor);
        }
    }

    private Color getColorForState(NodeState state) {
    	switch (state) {
        case WALL:
            return Color.BLACK;
        case INITIAL:
            return Color.LIMEGREEN;
        case GOAL:
            return Color.CRIMSON;
        case VISITED:
            return Color.MEDIUMVIOLETRED;
        case PATH:
            return Color.GOLD;
        case UNVISITED:
        default:
            return Color.WHITESMOKE;
    }
    }

    public GraphNode getNode() {
        return node;
    }
}