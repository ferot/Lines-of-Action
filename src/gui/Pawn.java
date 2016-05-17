package gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import engine.Coordinates;
import enums.PlayerColor;

public class Pawn extends JComponent {

	private static final String WHITE_PATH = "images/bialy.png";
	private static final String RED_PATH = "images/czerwony.png";
	private static final int _H = 72;
	private static final int _A = 65;
	private static final long serialVersionUID = 1L;
	private final Image image;
	private boolean pressed;
	private int xPos;
	private int yPos;
	private int id;
	private int currentNumber;
	private PlayerColor color;

	public Pawn(PlayerColor kolor, int i) {
		super();
		setColor(kolor);
		if (kolor == PlayerColor.RED) {
			image = new ImageIcon(RED_PATH).getImage();
		} else {
			image = new ImageIcon(WHITE_PATH).getImage();
		}
		id = i;
		currentNumber = i;
		xPos = 0;
		yPos = 0;
		pressed = false;
	}

	protected boolean checkMove(Coordinates point, Pawn pionek) {
		return false;
	}

	protected Coordinates getPoint(int x, int y) {
		return null;
	}

	public void paintComponent(Graphics g) {
		if (xPos >= _A && xPos <= _H && yPos >= 1 && yPos <= 8) {
			g.drawImage(image,
					Chessboard.getChessboardFields()[xPos - _A][8 - yPos].getX(),
					Chessboard.getChessboardFields()[xPos - _A][8 - yPos].getY(),
					null);
		} else {
			System.out.println("Nieprawid³owe wspó³rzêdne: [" + xPos + ", "
					+ yPos + "]");
		}
	}

	public Image getImage() {
		return image;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public PlayerColor getColor() {
		return color;
	}

	public void setColor(PlayerColor color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

}
