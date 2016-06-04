package bot;

import engine.Coordinates;

public class TreeNodeContent {

	private char brd[][];
	private Coordinates from;
	private Coordinates to;
	
	public TreeNodeContent() {
		setBrd(new char[8][8]);
	}
	
	public TreeNodeContent(Coordinates coordinates, Coordinates coordinates2,
			char[][] board) {
		from = coordinates;
		to = coordinates2;
		setBrd(board);
	}

	public TreeNodeContent(Coordinates coordinates, Coordinates coordinates2,
			double countValue) {
		from = coordinates;
		to = coordinates2;
		setBrd(new char[8][8]);
	}

	public Coordinates getFrom() {
		return from;
	}

	public void setFrom(Coordinates from) {
		this.from = from;
	}

	public Coordinates getTo() {
		return to;
	}

	public void setTo(Coordinates to) {
		this.to = to;
	}

	public char[][] getBrd() {
		return brd;
	}

	public void setBrd(char brd[][]) {
		this.brd = brd;
	}
}
