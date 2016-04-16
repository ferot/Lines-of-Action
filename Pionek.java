package grafika;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import enums.Kolor;

public class Pionek extends JComponent {

	private static final int _A = 65;
	private static final long serialVersionUID = 1L;
	private final Image image;
	private boolean pressed;
	private int xPos;
	private int yPos;
	private MouseListener listener;

	public Pionek(Kolor kolor) {
		super();
		if (kolor == Kolor.Czerwony) {
			image = new ImageIcon("czerwony.png").getImage();
		} else {
			image = new ImageIcon("bialy.png").getImage();
		}
		xPos = 0;
		yPos = 0;
		pressed = false;
		listener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Coordinates[][] temp = Szachownica.getPlanszaPola();
				Coordinates check = temp[xPos - _A][8 - yPos];
				if (e.getX() >= check.getX()
						&& e.getX() <= check.getX()
								+ image.getWidth(null)
						&& e.getY() >= check.getY()
						&& e.getY() <= check.getY() + image.getHeight(null)) {
					pressed = true;
				} else {
					pressed = false;
				}
			}
		};

	}

	public MouseListener getListener() {
		return listener;
	}

	public void setListener(MouseListener listener) {
		this.listener = listener;
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

}
