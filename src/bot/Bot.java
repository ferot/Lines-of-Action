package bot;

import enums.PlayerColor;

public class Bot {

	private PlayerColor color;
	private final static int deep = 2;
	public Bot(PlayerColor color) {
		this.color = color;
	}

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}

}
