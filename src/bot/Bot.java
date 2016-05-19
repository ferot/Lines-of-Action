package bot;

import java.util.ArrayList;
import java.util.List;

import bot.Tree.Node;
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

	public void move(List<Pawn> whites, List<Pawn> reds) {
		TreeNodeContent root = new TreeNodeContent();
		gameTree = new Tree<TreeNodeContent>(root, DEEP);
		if (color == Engine.getTurn()) {
			createTree(gameTree, whites, reds, DEEP);
			Node<TreeNodeContent> node = gameTree.getMinimumValue(1);
			Pawn temp = Engine.getPawn(node.getData().getFrom());
			Engine.move(temp, node.getData().getTo());
			Engine.checkGameFinished();
			Launcher.getBoard().repaint();
			Engine.changeTurn();
		}
	}

	private Tree<TreeNodeContent> createTree(Tree<TreeNodeContent> tree,
			List<Pawn> whites, List<Pawn> reds, int deep) {
		for (int current = 0; current < 1; current++) {
			List<TreeNodeContent> contents = new ArrayList<TreeNodeContent>();
			if (color == PlayerColor.WHITE) {
				for (Pawn pawn : whites) {
					contents.addAll(Engine.getPossibleMoves(pawn, false));
				}
				for (TreeNodeContent content : contents) {
					tree.add(tree.getRoot(), content);
				}

			} else {
				for (Pawn pawn : reds) {
					contents.addAll(Engine.getPossibleMoves(pawn, false));
				}
				for (TreeNodeContent content : contents) {
					tree.add(tree.getRoot(), content);
				}
			}
		}
		return null;
	}

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}

}
