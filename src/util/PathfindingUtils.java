package util;

import java.util.LinkedList;
import java.util.Queue;

import model.GraphNode;

public class PathfindingUtils {
	    public static boolean isPathAvailable(GraphNode[][] nodes, Vector start, Vector goal) {
	        int cols = nodes.length;
	        int rows = nodes[0].length;
	        boolean[][] visited = new boolean[cols][rows];
	        Queue<Vector> queue = new LinkedList<>();
	        queue.add(start);

	        while (!queue.isEmpty()) {
	            Vector current = queue.poll();
	            int x = (int) current.getX();
	            int y = (int) current.getY();

	            if (visited[x][y]) continue;
	            visited[x][y] = true;

	            if (x == (int) goal.getX() && y == (int) goal.getY()) {
	                return true;
	            }

	            for (Vector dir : new Vector[]{
	                new Vector(-1, 0), new Vector(1, 0),
	                new Vector(0, -1), new Vector(0, 1)
	            }) {
	                int nx = x + (int) dir.getX();
	                int ny = y + (int) dir.getY();

	                if (nx >= 0 && ny >= 0 && nx < cols && ny < rows &&
	                    !visited[nx][ny] &&
	                    nodes[nx][ny].getState() != NodeState.WALL) {
	                    queue.add(new Vector(nx, ny));
	                }
	            }
	        }

	        return false;
	    }
}
