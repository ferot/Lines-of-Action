package bot;

import engine.Coordinates;

public class TreeNodeContent {

	private Coordinates from;
	private Coordinates to;
	private double value;
	
	public TreeNodeContent(){}
	
	public TreeNodeContent(Coordinates coordinates, Coordinates coordinates2,
			double countValue) {
		from = coordinates;
		to = coordinates2;
		value = countValue;
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
