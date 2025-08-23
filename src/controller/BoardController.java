package controller;

import model.Board;
import model.Panel;
import util.Pathfinder;
import util.PathfindingResult;
import util.Vector;
import view.BoardView;

public class BoardController {
	private Board board;
	private BoardView boardView;
	
	public BoardController(Vector boardSize, PanelController panelController) {
		Panel panelModel = panelController.getModel();

        Vector boardDimensions = parseSize(panelModel.selectedBoardSizeProperty().get());
        board = new Board(new Vector(0, 0), boardSize, boardDimensions);
        board.generateBoard();
        boardView = new BoardView(board);

        // Register listeners
        panelController.onStart(() -> {
            String algo = panelModel.selectedAlgorithmProperty().get();
            runPathfinding(algo);
        });

        panelController.onReset(() -> {
            board.generateBoard();
            boardView.redraw();
        });

        panelModel.selectedBoardSizeProperty().addListener((obs, oldVal, newVal) -> {
            Vector newDims = parseSize(newVal);
            board.setDimensions(newDims);
            board.generateBoard();
            boardView.redraw();
        });	
	}
	
	public BoardController(Board board, BoardView boardView) {
		this.board = board;
		this.boardView = boardView;
	}

    private Vector parseSize(String s) {
        CharSequence temp = s.subSequence(s.indexOf("(")+1, s.indexOf(")"));
        String[] parts = temp.toString().split("x");
        return new Vector(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    private void runPathfinding(String algorithm) {
    	PathfindingResult result = null;
    	Vector start = board.getInitialDimension();
    	Vector goal = board.getGoalDimension();
    	switch (algorithm) {
    	    case "A*": result = Pathfinder.astar(board.getBoard(), start, goal); break;
    	    case "Greedy Best-First Search": result = Pathfinder.greedy(board.getBoard(), start, goal); break;
    	    case "Depth-First Search": result = Pathfinder.dfs(board.getBoard(), start, goal); break;
    	    case "Breadth-First Search": result = Pathfinder.bfs(board.getBoard(), start, goal); break;
    	    default: throw new IllegalArgumentException("Unknown algorithm");
    	}
        boardView.animatePath(result);
    }
	
	public void redrawBoard(Vector boardPosition, Vector boardSize, Vector boardDimensions) {
		board.setDimensions(boardDimensions);
		board.setPosition(boardPosition);
		board.setSize(boardSize);
		boardView = new BoardView(board);
	}
	
	
	public BoardView getView() {
        return boardView;
    }

}
