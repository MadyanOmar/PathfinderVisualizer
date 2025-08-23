package util;

import java.util.List;

import model.GraphNode;

public class PathfindingResult {
    private final List<GraphNode> visitedNodes;
    private final List<GraphNode> shortestPath;

    public PathfindingResult(List<GraphNode> visitedNodes, List<GraphNode> shortestPath) {
        this.visitedNodes = visitedNodes;
        this.shortestPath = shortestPath;
    }

    public List<GraphNode> getVisitedNodes() {
        return visitedNodes;
    }

    public List<GraphNode> getShortestPath() {
        return shortestPath;
    }
}
