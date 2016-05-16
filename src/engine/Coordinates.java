package engine;

public class Coordinates {

	@Override
	public boolean equals(Object obj) {
		Coordinates cords = (Coordinates) obj;
		if (this.x == cords.getX() && this.y == cords.getY())
			return true;
		return false;
	}

	private int x;
	private int y;

	public Coordinates() {
		setX(0);
		setY(0);
	}

	public Coordinates(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
