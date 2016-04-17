package grafika;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import enums.Kolor;

public class Pionek extends JComponent {

	private static final long serialVersionUID = 1L;
	private final Image image;
	private boolean pressed;
	private int xPos;
	private int yPos;
	private Kolor kolor;

	public Pionek(Kolor kolor) {
		super();
		setKolor(kolor);
		if (kolor == Kolor.Czerwony) {
			image = new ImageIcon("czerwony.png").getImage();
		} else {
			image = new ImageIcon("bialy.png").getImage();
		}
		xPos = 0;
		yPos = 0;
		pressed = false;
	}

	protected boolean checkMove(Coordinates point, Pionek pionek) {
		return false;
	}

	protected Coordinates getPoint(int x, int y) {
		return null;
	}

	public void paintComponent(Graphics g) {
		if (xPos >= 65 && xPos <= 72 && yPos >= 1 && yPos <= 8) {
			g.drawImage(image,
				Szachownica.getPlanszaPola()[xPos - 65][8 - yPos].getX(),
				Szachownica.getPlanszaPola()[xPos - 65][8 - yPos].getY(), null);
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

	public Kolor getKolor() {
		return kolor;
	}

	public void setKolor(Kolor kolor) {
		this.kolor = kolor;
	}

}
