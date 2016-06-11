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

	// public void move(char[][] board) {
	// TreeNodeContent root = new TreeNodeContent();
	// gameTree = new Tree<TreeNodeContent>(root, DEEP);
	// if (color == Engine.getTurn()) {
	// System.out.println("CREATE TREE:");
	// long start = System.currentTimeMillis();
	// createTree(gameTree, board, DEEP);
	// System.out.println(System.currentTimeMillis() - start);
	// System.out.println("MINIMAX: ");
	// start = System.currentTimeMillis();
	// // MIN_MAX
	// TreeNodeContent content = minMax(gameTree.getRoot(), DEEP);
	// System.out.println(System.currentTimeMillis() - start);
	// // Node<TreeNodeContent> node = Engine.findBestOption(gameTree,
	// // color);
	// Pawn temp = Engine.getPawn(content.getFrom());
	// Engine.move(temp, content.getTo());
	// Engine.checkGameFinished();
	// Launcher.getBoard().repaint();
	// Engine.changeTurn();
	// }
	// }

	public void move(char[][] board) {
		TreeNodeContent root = new TreeNodeContent();
		root.setBrd(board);
		gameTree = new Tree<TreeNodeContent>(root, DEEP);
		if (color == Engine.getTurn()) {
			createTree(gameTree, DEEP);
			// MIN_MAX
			Node<TreeNodeContent> alpha = initNode(gameTree.getRoot(), -100000); // TODO
																					// WARTOSCI
															// DOBRANE Z
														// HEURYSTYK
			Node<TreeNodeContent> beta = initNode(gameTree.getRoot(), 100000);
			Node<TreeNodeContent> node = alphaBeta(gameTree.getRoot(), DEEP,
					alpha, beta);
			while (node.getParent() != null && node.getParent().getParent() != null) {
				node = node.getParent();
			}
			// Node<TreeNodeContent> node = Engine.findBestOption(gameTree,
			// color);
			// Node<TreeNodeContent> node = gameTree.getMinimumValue(1);
			Pawn temp = Engine.getPawn(node.getData().getBrd(),node.getData().getFrom());
			Engine.move(temp, node.getData().getTo());
			Engine.checkGameFinished();
			Launcher.getBoard().repaint();
			Engine.changeTurn();
		}
	}

	private Node<TreeNodeContent> initNode(Node<TreeNodeContent> node, int i) {
		Node<TreeNodeContent> alpha = new Node<TreeNodeContent>();
		alpha.setData(cloneData(node.getData()));
		alpha.setChildren(node.getChildren());
		alpha.setParent(node.getParent());
		alpha.getData().setValue(i);
		return alpha;
	}

	private Node<TreeNodeContent> initNode(Node<TreeNodeContent> node) {
		Node<TreeNodeContent> alpha = new Node<TreeNodeContent>();
		alpha.setData(cloneData(node.getData()));
		alpha.setChildren(node.getChildren());
		alpha.setParent(node.getParent());
		return alpha;
	}

	private TreeNodeContent cloneData(TreeNodeContent data) {
		TreeNodeContent content = new TreeNodeContent();
		content.setBrd(data.getBrd());
		content.setFrom(data.getFrom());
		content.setTo(data.getTo());
		content.setValue(data.getValue());
		return content;
	}

	private Node<TreeNodeContent> alphaBeta(Node<TreeNodeContent> node,
			int deep, Node<TreeNodeContent> alpha, Node<TreeNodeContent> beta) {
		if (deep == 0) {
			return node;
		} else if (deep % 2 == DEEP % 2) { // MAKSYMALIZACJA
			Node<TreeNodeContent> x = initNode(node);
			x.getData().setValue(Double.MIN_VALUE);
			for (Node<TreeNodeContent> child : node.getChildren()) {
				alpha = x.getData().getValue() > alpha.getData().getValue() ? x
						: alpha;
				Node<TreeNodeContent> tmp = initNode(node);
				tmp.getData().setValue(alpha.getData().getValue() + 1);
				x = alphaBeta(child, deep - 1, alpha, tmp);
				if (x.getData().getValue() > alpha.getData().getValue()) {
					x = alphaBeta(child, deep - 1, x, beta);
				}
				if (alpha.getData().getValue() >= beta.getData().getValue()) {
					return beta;
				}
			}
			return alpha;
		} else { // MINIMALIZACJA
			Node<TreeNodeContent> x = initNode(node);
			x.getData().setValue(Double.MAX_VALUE);
			for (Node<TreeNodeContent> child : node.getChildren()) {
				beta = x.getData().getValue() < beta.getData().getValue() ? x
						: beta;
				Node<TreeNodeContent> tmp = initNode(node);
				tmp.getData().setValue(beta.getData().getValue() - 1);
				x = alphaBeta(child, deep - 1, tmp, beta);
				if (x.getData().getValue() > alpha.getData().getValue()) {
					x = alphaBeta(child, deep - 1, alpha, x);
				}
				if (alpha.getData().getValue() >= beta.getData().getValue()) {
					return alpha;
				}
			}
			return beta;
		}
	}

	// private TreeNodeContent minMax(Node<TreeNodeContent> node, int deep) {
	// if (deep == 0) {
	// return node.getData();
	// } else if (deep % 2 != 0) { // MAKSYMALIZACJA
	// TreeNodeContent max = new TreeNodeContent();
	// max.setValue(Double.MIN_VALUE);
	// for (Node<TreeNodeContent> child : node.getChildren()) {
	// TreeNodeContent tmp = minMax(child, deep - 1);
	// if (tmp.getValue() > max.getValue()) {
	// max = tmp;
	// }
	// }
	// return max;
	// } else { // MINIMALIZACJA
	// TreeNodeContent min = new TreeNodeContent();
	// min.setValue(Double.MAX_VALUE);
	// for (Node<TreeNodeContent> child : node.getChildren()) {
	// TreeNodeContent tmp = minMax(child, deep - 1);
	// if (tmp.getValue() < min.getValue()) {
	// min = tmp;
	// }
	// }
	// return min;
	// }
	// }

	private void createTree(Tree<TreeNodeContent> tree, int deep) {
		PlayerColor turn;
		turn = color;
		for (int currentDeep = 0; currentDeep < deep; currentDeep++) {
			for (Node<TreeNodeContent> node : nodesAtLevel(tree.getRoot(), currentDeep)) {
				createChildren(tree, node, turn);
			}
			turn = changeTurn(turn);
		}
	}

	private void createChildren(Tree<TreeNodeContent> tree,
			Node<TreeNodeContent> parent, PlayerColor turn) {
		List<TreeNodeContent> contents = new ArrayList<TreeNodeContent>();
		if (turn == PlayerColor.WHITE) {
			for (Coordinates point : Engine.getPointsFromBoard(parent.getData()
					.getBrd(), 'w')) {
				contents.addAll(Engine.getPossibleMoves(parent.getData()
						.getBrd(), point));
			}
			for (TreeNodeContent content : contents) {
				tree.add(parent, content);
			}

		} else {
			for (Coordinates point : Engine.getPointsFromBoard(parent.getData()
					.getBrd(), 'r')) {
				contents.addAll(Engine.getPossibleMoves(parent.getData()
						.getBrd(), point));
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
