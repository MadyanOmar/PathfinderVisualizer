package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Board;
import model.GraphNode;
import util.NodeState;
import util.PathfindingResult;
import util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardView extends Pane {
    private final Board board;
    private final Map<GraphNode, GraphNodeView> nodeViewMap = new HashMap<>();
    private GraphNodeView initialView;
    private GraphNodeView goalView;

    public BoardView(Board board) {
        this.board = board;
        setPrefSize(board.getSize().getX(), board.getSize().getY());
        drawBoard();
    }

    private void drawBoard() {
        getChildren().clear();
        nodeViewMap.clear();

        int columns = (int) board.getDimensions().getX();
        int rows = (int) board.getDimensions().getY();

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                GraphNode node = board.getNode(col, row);
                if (node.getState() == NodeState.INITIAL || node.getState() == NodeState.GOAL) continue;
                GraphNodeView view = new GraphNodeView(node);
                nodeViewMap.put(node, view);
                getChildren().add(view);
            }
        }

        GraphNode initial = board.getNode((int) board.getInitialDimension().getX(), (int) board.getInitialDimension().getY());
        initialView = new GraphNodeView(initial);
        initialView.setOnDragRelease((oldCol, oldRow) -> handleNodeMove(initialView.getNode(), oldCol, oldRow));
        getChildren().add(initialView);

        GraphNode goal = board.getNode((int) board.getGoalDimension().getX(), (int) board.getGoalDimension().getY());
        goalView = new GraphNodeView(goal);
        goalView.setOnDragRelease((oldCol, oldRow) -> handleNodeMove(goalView.getNode(), oldCol, oldRow));
        getChildren().add(goalView);
    }

    private void handleNodeMove(GraphNode draggedNode, int oldCol, int oldRow) {
        int cols = (int) board.getDimensions().getX();
        int rows = (int) board.getDimensions().getY();
        double width = board.getSize().getX() / cols;
        double height = board.getSize().getY() / rows;

        double x = draggedNode.getPosition().getX();
        double y = draggedNode.getPosition().getY();

        int newCol = Math.round((float)((x - board.getPosition().getX()) / width));
        int newRow = Math.round((float)((y - board.getPosition().getY()) / height));

        if (newCol < 0 || newCol >= cols || newRow < 0 || newRow >= rows) return;

        if (draggedNode.getState() == NodeState.INITIAL || draggedNode.getState() == NodeState.GOAL) {
            GraphNode targetNode = board.getBoard()[newCol][newRow];
            targetNode.setState(draggedNode.getState());
            targetNode.setDimension(new Vector(newCol, newRow));
            targetNode.setPosition(new Vector(newCol * width, newRow * height));
            board.updateNode(new Vector(newCol, newRow), targetNode);
        }

        GraphNode oldNode = board.getBoard()[oldCol][oldRow];
        oldNode.setDimension(new Vector(oldCol, oldRow));
        oldNode.setPosition(new Vector(oldCol * width, oldRow*height));
        oldNode.setState(NodeState.UNVISITED);
        board.updateNode(new Vector(oldCol, oldRow), oldNode);

        redraw();
    }

    private void updateNodeView(GraphNode node) {
        GraphNodeView view = nodeViewMap.get(node);
        if (view != null) {
            view.updateColorAnimated();
        }
    }

    public void animatePath(PathfindingResult result) {
        List<GraphNode> visited = result.getVisitedNodes();
        List<GraphNode> path = result.getShortestPath();

        Timeline timeline = new Timeline();
        Duration delay = Duration.millis(60);

        for (int i = 0; i < visited.size(); i++) {
            GraphNode node = visited.get(i);
            if (node.getState() == NodeState.INITIAL || node.getState() == NodeState.GOAL) continue;

            KeyFrame kf = new KeyFrame(delay.multiply(i), e -> {
                node.setState(NodeState.VISITED);
                updateNodeView(node);
            });
            timeline.getKeyFrames().add(kf);
        }

        int offset = visited.size();
        for (int i = 0; i < path.size(); i++) {
            GraphNode node = path.get(i);
            if (node.getState() == NodeState.INITIAL || node.getState() == NodeState.GOAL) continue;

            KeyFrame kf = new KeyFrame(delay.multiply(offset + i), e -> {
                node.setState(NodeState.PATH);
                updateNodeView(node);
            });
            timeline.getKeyFrames().add(kf);
        }

        timeline.play();
    }

    public void redraw() {
        drawBoard();
    }
}
