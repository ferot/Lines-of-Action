package bot;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
	private Node<T> root;
	private int maxDeep;

	public Tree(T rootData, int deep) {
		root = new Node<T>();
		maxDeep = deep;
		root.data = rootData;
		root.deep = 0;
		root.parent = null;
		root.children = new ArrayList<Node<T>>();
	}

	public void add(Node<T> parent, T data) {
		Node<T> child = new Node<T>();
		child.data = data;
		child.deep = parent.deep + 1;
		child.parent = parent;
		child.children = new ArrayList<Node<T>>();
		parent.children.add(child);
	}

	public void remove(Node<T> node) {
		node.children = null;
		if (node.parent != null) {
			node.parent.children.remove(node);
		}
	}

	public Node<T> getMaximumValue(int deep) {
		if (deep > maxDeep || deep < 0) {
			return null;
		}
		List<Node<T>> nodesAtDeep = new ArrayList<Node<T>>();
		if (deep == 0) {
			return root;
		}
		if (deep == 1) {
			nodesAtDeep.addAll(root.children);
			return findMax(nodesAtDeep);
		}
		if (deep == 2) {
			for (Node<T> parent : root.children) {
				nodesAtDeep.addAll(parent.children);
			}
			return findMax(nodesAtDeep);
		}
		if (deep == 3) {
			for (Node<T> parent : root.children) {
				for (Node<T> nodes : parent.children) {
					nodesAtDeep.addAll(nodes.children);
				}
			}
			return findMax(nodesAtDeep);
		}
		return null;
	}

	public Node<T> getMinimumValue(int deep) {
		if (deep > maxDeep || deep < 0) {
			return null;
		}
		List<Node<T>> nodesAtDeep = new ArrayList<Node<T>>();
		if (deep == 0) {
			return root;
		}
		if (deep == 1) {
			nodesAtDeep.addAll(root.children);
			return findMin(nodesAtDeep);
		}
		if (deep == 2) {
			for (Node<T> parent : root.children) {
				nodesAtDeep.addAll(parent.children);
			}
			return findMin(nodesAtDeep);
		}
		if (deep == 3) {
			for (Node<T> parent : root.children) {
				for (Node<T> nodes : parent.children) {
					nodesAtDeep.addAll(nodes.children);
				}
			}
			return findMin(nodesAtDeep);
		}
		return null;
	}

	private Node<T> findMax(List<Node<T>> nodesAtDeep) {
		double max = -1;
		Node<T> maxNode = new Node<T>();
		for (Node<T> node : nodesAtDeep) {
			if (((TreeNodeContent) node.data).getValue() > max) {
				max = ((TreeNodeContent) node.data).getValue();
				maxNode = node;
			}
		}
		return maxNode;
	}

	private Node<T> findMin(List<Node<T>> nodesAtDeep) {
		double min = Integer.MAX_VALUE;
		Node<T> minNode = new Node<T>();
		for (Node<T> node : nodesAtDeep) {
			if (((TreeNodeContent) node.data).getValue() < min) {
				min = ((TreeNodeContent) node.data).getValue();
				minNode = node;
			}
		}
		return minNode;
	}

	public static class Node<T> {
		private T data;
		private int deep;
		private Node<T> parent;
		private List<Node<T>> children;

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}
	}

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}
}