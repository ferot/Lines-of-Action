package bot;

import java.util.ArrayList;
import java.util.List;

import bot.Tree.Node;
import engine.Coordinates;
import engine.Engine;
import enums.PlayerColor;
import gui.Launcher;
import gui.Pawn;

public class Bot {

	private PlayerColor color;
	private Tree<TreeNodeContent> gameTree;
	private final static int DEEP = 3;
	public Bot(PlayerColor color) {
		this.color = color;
		TreeNodeContent root = new TreeNodeContent();
		gameTree = new Tree<TreeNodeContent>(root, DEEP);
	}

	public void move(char [][] board) {
		TreeNodeContent root = new TreeNodeContent();
		gameTree = new Tree<TreeNodeContent>(root, DEEP);
		if (color == Engine.getTurn()) {
			createTree(gameTree, board, DEEP);
			// Node<TreeNodeContent> node = Engine.findBestOption(gameTree,
			// color);
			Node<TreeNodeContent> node = gameTree.getMinimumValue(1);
			Pawn temp = Engine.getPawn(node.getData().getFrom());
			Engine.move(temp, node.getData().getTo());
			Engine.checkGameFinished();
			Launcher.getBoard().repaint();
			Engine.changeTurn();
		}
	}

	private void createTree(Tree<TreeNodeContent> tree,
			char [][] board, int deep) {
		PlayerColor turn = color;
		for (int current = 0; current < deep; current++) {
			for (Node<TreeNodeContent> node : nodesAtLevel(tree.getRoot(), current)) {
				createChildren(tree, node, Engine.cloneBoard(board), turn);
			}
			turn = changeTurn(turn);
		}
	}

	private void createChildren(Tree<TreeNodeContent> tree,
			Node<TreeNodeContent> parent,
			char[][] board, PlayerColor turn) {
		List<TreeNodeContent> contents = new ArrayList<TreeNodeContent>();
		if (turn == PlayerColor.WHITE) {
			for (Coordinates point : Engine.getPointsFromBoard(board, 'w')) {
				contents.addAll(Engine.getPossibleMoves(board, point));
			}
			for (TreeNodeContent content : contents) {
				tree.add(parent, content);
			}

		} else {
			for (Coordinates point : Engine.getPointsFromBoard(board, 'r')) {
				contents.addAll(Engine.getPossibleMoves(board, point));
			}
			for (TreeNodeContent content : contents) {
				tree.add(parent, content);
			}
		}
	}

	private List<Node<TreeNodeContent>> nodesAtLevel(
			Node<TreeNodeContent> parent, int current) {
		List<Node<TreeNodeContent>> nodes = new ArrayList<Node<TreeNodeContent>>();
		if (current == 0) {
			nodes.add(parent);
		} else if (current == 1) {
			for (Node<TreeNodeContent> node : parent.getChildren()) {
				nodes.add(node);
			}
		} else {
			for (Node<TreeNodeContent> node : parent.getChildren()) {
				nodes.addAll(nodesAtLevel(node, current - 1));
			}
		}

		return nodes;
	}

	private PlayerColor changeTurn(PlayerColor turn) {
		return turn == PlayerColor.RED ? PlayerColor.WHITE : PlayerColor.RED;

	}

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}

}
